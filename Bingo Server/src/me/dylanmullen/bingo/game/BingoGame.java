package me.dylanmullen.bingo.game;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import me.dylanmullen.bingo.game.runnables.GameRunnable;
import me.dylanmullen.bingo.game.runnables.LobbyRunnable;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class BingoGame
{

	private UUID gameUUID;
	private HashSet<User> users;
	private HashSet<BingoCard> cardsInPlay;
	private HashSet<CardGroup> potentialCards;

	private Random random;
	private long seed;

	private int[] numbers;

	private int maxPlayers;
	private static GameState state = GameState.LOBBY;
	private static LineState lineState;

	private Thread thread;

	public enum GameState
	{
		LOBBY, PLAYING, ENDING;
	}

	public enum LineState
	{
		ONE, TWO, FULLHOUSE;
	}

	public BingoGame(int playerCap)
	{
		this.gameUUID = UUID.randomUUID();
		this.users = new HashSet<User>();
		this.potentialCards = new HashSet<CardGroup>();
		this.cardsInPlay = new HashSet<BingoCard>();

		this.maxPlayers = playerCap;

		this.random = new Random();
		this.seed = System.currentTimeMillis();

		this.numbers = new int[90];
		start();
	}

	public void start()
	{
		thread = new Thread(() ->
		{
			state = GameState.LOBBY;
			new LobbyRunnable(this).run();
			state = GameState.PLAYING;
			new GameRunnable(this).run();
		});
		thread.start();
	}

	public void end()
	{
	}

	public void sendUserCards()
	{
		for (User u : users)
		{
			StringBuilder sb = new StringBuilder();
			for (BingoCard card : cardsInPlay)
			{
				if (u.equals(card.getOwner()))
					sb.append(card.getUuid().toString() + "/nl/");
			}
			String mes = sb.toString();
			if (mes.length() != 0)
				mes = mes.substring(0, (mes.length() - ("/nl/").length()));
			PacketHandler.sendPacket(PacketHandler.createPacket(u.getClient(), 11, mes), null);
		}

	}

	public int pickNumber()
	{
		if (allNumbersUsed())
			return -1;
		int number = random.nextInt(90 - 1) + 1;

		if (numbers[number - 1] == 1)
		{
			return pickNumber();
		}

		numbers[number - 1] = 1;
		return number;
	}

	private boolean allNumbersUsed()
	{
		for (int i = 0; i < numbers.length; i++)
		{
			if (numbers[i] == 0)
				return false;
		}
		return true;
	}

	public boolean checkWinners(int num)
	{
		boolean status = false;

		for (BingoCard card : cardsInPlay)
		{
			status = card.isWinner(lineState, numbers);
		}

		return status;
	}

	public HashSet<BingoCard> getWinners()
	{
		HashSet<BingoCard> winners = new HashSet<BingoCard>();
		for (BingoCard card : cardsInPlay)
		{
			if (card.isWinner(lineState, numbers))
				winners.add(card);
		}
		return winners;
	}

	public void addPlayer(User u)
	{
		if (users.contains(u))
			return;
		users.add(u);
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

	public void addCard(BingoCard card)
	{
		cardsInPlay.add(card);
	}

	public CardGroup getCardGroup(User u)
	{
		for (CardGroup cg : potentialCards)
			if (u.equals(cg.getOwner()))
				return cg;
		return null;
	}

	public BingoCard getCard(User u)
	{
		for (BingoCard card : cardsInPlay)
			if (card.getOwner().equals(u))
				return card;
		return null;
	}

	public boolean hasUser(User u)
	{
		return users.contains(u);
	}

	public boolean hasCard(User u)
	{
		for (BingoCard card : cardsInPlay)
			if (card.getOwner().equals(u))
				return true;
		return false;
	}

	public void sendPacket(int id, String mes)
	{
		for (User user : users)
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
		return users;
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

}
