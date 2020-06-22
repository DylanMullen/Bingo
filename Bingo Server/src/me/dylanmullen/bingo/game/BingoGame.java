package me.dylanmullen.bingo.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.chat.BingoChat;
import me.dylanmullen.bingo.game.currency.CurrencyController;
import me.dylanmullen.bingo.game.runnables.GameRunnable;
import me.dylanmullen.bingo.game.runnables.LobbyRunnable;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class BingoGame
{

	private UUID gameUUID;
	private GameSettings settings;
	private BingoChat chat;

	private HashSet<User> usersConnected;
	private HashMap<User, CardGroup> cardsInPlay;
	private HashSet<CardGroup> potentialCards;

	private ArrayList<Integer> numbers;
	private ArrayList<Integer> numbersCalled;

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

	public BingoGame(GameSettings settings)
	{
		this.settings = settings;

		setup();
	}

	public void setup()
	{
		this.gameUUID = UUID.randomUUID();
		this.usersConnected = new HashSet<User>();
		this.potentialCards = new HashSet<CardGroup>();
		this.cardsInPlay = new HashMap<User, CardGroup>();

		this.numbers = new ArrayList<Integer>();
		this.numbersCalled = new ArrayList<Integer>();

		if (getSettings().hasChat())
			chat = new BingoChat();

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
			PacketHandler.sendPacket(getUsercards(11, u.getClient(), cg));
		}

		if (settings.isDebugMode())
			rig();
	}

	@SuppressWarnings("unchecked")
	private Packet getUsercards(int id, Client client, CardGroup cards)
	{
		Packet packet = PacketHandler.createPacket(client, id, null);
		JSONObject message = new JSONObject();
		message.put("cards", cards.toString());
		packet.setMessageSection(message);

		return packet;
	}

	public void sendPotentialCards()
	{
		for (User u : usersConnected)
		{
			CardGroup cg = generateCardGroup(u);
			PacketHandler.sendPacket(getUsercards(14, u.getClient(), cg));
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

	@SuppressWarnings("unchecked")
	public void handleWinning()
	{
		List<User> winners = getWinners();

		double prize = settings.getWinningPrize(lineState, winners.size());
		JSONArray winnerData = new JSONArray();
		for (User user : winners)
		{
			winnerData.add(user.getUserInformation().getDisplayName());
			CurrencyController.getController().increment(user, prize);
		}

		sendPacket(createWinningPacket(winnerData));
		if (lineState != LineState.FULLHOUSE)
		{
			sendPacket(createLinestatePacket());
			updateLineState();
		} else
		{
			shouldRestart = true;
		}
	}

	@SuppressWarnings("unchecked")
	private Packet createWinningPacket(JSONArray winnerData)
	{
		Packet packet = PacketHandler.createPacket(null, 13, null);
		JSONObject message = new JSONObject();
		message.put("winners", winnerData);
		packet.setMessageSection(message);
		return packet;
	}

	private Packet createLinestatePacket()
	{
		Packet packet = PacketHandler.createPacket(null, 12, null);
		JSONObject message = new JSONObject();
		message.put("linestate", lineState.state);
		packet.setMessageSection(message);
		return packet;
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

	public void submitChatMessage(User user, String message)
	{
		if (chat != null)
			chat.sendMessage(usersConnected, chat.submitMessage(user, message));
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

	@SuppressWarnings("unchecked")
	public Packet createGamestatePacket()
	{
		Packet packet = PacketHandler.createPacket(null, 10, null);
		JSONObject message = new JSONObject();
		message.put("gameState", getGameState());
		packet.setMessageSection(message);
		return packet;
	}

	// TODO send previous numbers.
	public Packet createNextNumberPacket(int num)
	{
		Packet packet = PacketHandler.createPacket(null, 9, null);
		JSONObject message = new JSONObject();
		message.put("number", num);
		message.put("previousNumbers", new JSONArray());
		packet.setMessageSection(message);
		return packet;
	}

	public void sendPacket(Packet packet)
	{
		for (User user : usersConnected)
		{
			packet.setClient(user.getClient());
			PacketHandler.sendPacket(packet);
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

	public int getGameState()
	{
		switch (state)
		{
			case LOBBY:
				return 0;
			case PLAYING:
				return 1;
			case ENDING:
				return 2;
			default:
				return -1;
		}
	}

	public boolean shouldRestart()
	{
		return shouldRestart;
	}

	public GameSettings getSettings()
	{
		return settings;
	}
}
