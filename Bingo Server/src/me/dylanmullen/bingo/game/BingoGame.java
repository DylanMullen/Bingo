package me.dylanmullen.bingo.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import me.dylanmullen.bingo.game.runnables.GameRunnable;
import me.dylanmullen.bingo.game.runnables.LobbyRunnable;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class BingoGame
{

	private UUID gameUUID;
	private HashSet<User> usersConnected;
	private HashMap<User, CardGroup> cardsInPlay;
	private HashSet<CardGroup> potentialCards;

	private ArrayList<Integer> numbers;
	private ArrayList<Integer> numbersCalled;

	private int maxPlayers;
	private static GameState state = GameState.LOBBY;
	private static LineState lineState = LineState.ONE;

	private Thread thread;
	private boolean playing;
	private boolean shouldRestart = false;

	public enum GameState
	{
		LOBBY, PLAYING, ENDING;
	}

	public enum LineState
	{
		ONE(0), TWO(1), FULLHOUSE(2);

		private int state;

		private LineState(int state)
		{
			this.state = state;
		}

		public int getState()
		{
			return state;
		}

		public static LineState getStateByID(int id)
		{
			for (LineState ls : values())
				if (ls.state == id)
					return ls;
			return null;
		}
	}

	public BingoGame(int playerCap)
	{
		this.gameUUID = UUID.randomUUID();
		this.usersConnected = new HashSet<User>();
		this.potentialCards = new HashSet<CardGroup>();
		this.cardsInPlay = new HashMap<User, CardGroup>();

		this.maxPlayers = playerCap;

		this.numbers = new ArrayList<Integer>();
		this.numbersCalled = new ArrayList<Integer>();

		setupNumbers();
		start();
	}

	public void setupNumbers()
	{
		numbers.clear();
		numbersCalled.clear();
		for (int i = 0; i < 90; i++)
		{
			numbers.add(i + 1);
		}
		Collections.shuffle(numbers);
	}

	public void start()
	{
		thread = new Thread(() ->
		{
			do
			{
				state = GameState.LOBBY;
				new LobbyRunnable(this).run();
				state = GameState.PLAYING;
				new GameRunnable(this).run();
				restart();
			} while (playing);
			dispose();
		});
		playing = true;
		thread.start();
	}

	public void restart()
	{
		if (!shouldRestart)
			return;

		state = GameState.LOBBY;
		lineState = LineState.ONE;
		cardsInPlay.clear();
		potentialCards.clear();
		setupNumbers();
		sendPotentialCards();
		shouldRestart = false;
	}

	public synchronized void dispose()
	{
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void sendUserCards()
	{
		for (User u : cardsInPlay.keySet())
		{
			CardGroup cg = cardsInPlay.get(u);
			PacketHandler.sendPacket(PacketHandler.createPacket(u.getClient(), 11, cg.getCardUUIDs()), null);
		}
	}

	public void sendPotentialCards()
	{
		for (User u : usersConnected)
		{
			CardGroup cg = generateCardGroup(u);
			PacketHandler.sendPacket(PacketHandler.createPacket(u.getClient(), 14, cg.toString()), null);
		}
	}

	public int getPlayersPlayingSize()
	{
		return cardsInPlay.keySet().size();
	}

	// DEBUG TOOL
	public void rig()
	{
		int[] x = cardsInPlay.values().iterator().next().firstNumbers();

		numbers.clear();
		for (int j = 0; j < x.length; j++)
		{
			numbers.add(x[j]);
		}
	}

	public int pickNumber()
	{
		if (allNumbersUsed())
			return -1;

		int number = numbers.get(0);
		numbers.remove(0);
		numbersCalled.add(number);
		return number;
	}

	public boolean checkWinners()
	{
		boolean status = false;

		for (User u : cardsInPlay.keySet())
		{
			CardGroup cg = cardsInPlay.get(u);
			status = cg.checkWinner(lineState, numbersCalled);
			if (status)
				return status;
		}
		return status;
	}

	public ArrayList<User> getWinners()
	{
		ArrayList<User> winners = new ArrayList<User>();
		for (User u : cardsInPlay.keySet())
		{
			if (cardsInPlay.get(u).checkWinner(lineState, numbersCalled))
				winners.add(u);
		}
		return winners;
	}

	public ArrayList<BingoCard> getWinningCards(User u)
	{
		return cardsInPlay.get(u).getWinningCards(lineState, numbersCalled);
	}

	public void handleWinning()
	{
		ArrayList<User> winners = getWinners();

		StringBuilder winnerNames = new StringBuilder();
		for (int i = 0; i < winners.size(); i++)
		{
			User u = winners.get(i);
			winnerNames.append(u.getUUID().toString() + (winners.size() - 1 == i ? "" : "/nl/"));
		}
		sendPacket(13, winnerNames.toString());
		if (lineState != LineState.FULLHOUSE)
		{
			sendPacket(12, lineState.state + "");
			updateLineState();
		} else
		{
			shouldRestart = true;
		}
	}

	private void updateLineState()
	{
		int index = lineState.state + 1;
		lineState = LineState.getStateByID(index);
	}

	private boolean allNumbersUsed()
	{
		return numbers.isEmpty();
	}

	public void addPlayer(User u)
	{
		if (usersConnected.contains(u))
			return;
		usersConnected.add(u);
		u.setCurrentGame(this);
	}

	public CardGroup generateCardGroup(User u)
	{
		CardGroup cg = new CardGroup(u);
		cg.generate();
		potentialCards.add(cg);
		return cg;
	}

	public void removeCardGroup(CardGroup group)
	{
		potentialCards.remove(group);
	}

	public void addCard(User u, BingoCard card)
	{
		synchronized (cardsInPlay)
		{
			if (!cardsInPlay.containsKey(u))
				cardsInPlay.put(u, new CardGroup(u));

			CardGroup cg = cardsInPlay.get(u);
			cg.addCard(card);
			cardsInPlay.put(u, cg);
		}
	}

	public CardGroup getCardGroup(User u)
	{
		for (CardGroup cg : potentialCards)
			if (u.equals(cg.getOwner()))
				return cg;
		return null;
	}

	public CardGroup getCards(User u)
	{
		return cardsInPlay.get(u);
	}

	public boolean hasUser(User u)
	{
		return usersConnected.contains(u);
	}

	public boolean hasCards(User u)
	{
		return cardsInPlay.containsKey(u);
	}

	public void sendPacket(int id, String mes)
	{
		for (User user : usersConnected)
		{
			PacketHandler.sendPacket(PacketHandler.createPacket(user.getClient(), id, mes), null);
		}
	}

	public UUID getGameUUID()
	{
		return gameUUID;
	}

	public HashSet<User> getPlayers()
	{
		return usersConnected;
	}

	public String getGameState()
	{
		switch (state)
		{
			case ENDING:
				return "2";
			case LOBBY:
				return "0";
			case PLAYING:
				return "1";
			default:
				return "-1";
		}
	}

	public boolean shouldRestart()
	{
		return shouldRestart;
	}

}
