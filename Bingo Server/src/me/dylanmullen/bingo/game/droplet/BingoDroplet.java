package me.dylanmullen.bingo.game.droplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.GameSettings;
import me.dylanmullen.bingo.game.cards.BingoCard;
import me.dylanmullen.bingo.game.cards.BingoCardGroup;
import me.dylanmullen.bingo.game.chat.BingoChat;
import me.dylanmullen.bingo.game.chat.ChatMessage;
import me.dylanmullen.bingo.game.currency.CurrencyController;
import me.dylanmullen.bingo.game.runnables.GameRunnable;
import me.dylanmullen.bingo.game.runnables.LobbyRunnable;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class BingoDroplet
{

	public enum GameState
	{
		LOBBY(0), PLAYING(1);

		private int stateCode;

		private GameState(int code)
		{
			this.stateCode = code;
		}

		public int getStateCode()
		{
			return stateCode;
		}
	}

	public enum LineState
	{
		ONE(1), TWO(2), FULLHOUSE(3);

		private int linesRequired;

		private LineState(int lines)
		{
			this.linesRequired = lines;
		}

		public int getLinesRequired()
		{
			return linesRequired;
		}

		public static LineState getByLines(int lines)
		{
			for (LineState state : values())
				if (state.getLinesRequired() == lines)
					return state;
			return null;
		}
	}

	private UUID uuid;
	private GameSettings settings;

	private BingoChat chat;

	private Set<User> usersConnected;
	private Set<BingoCardGroup> cards;

	private GameState dropletState;
	private LineState lineState;

	private List<Integer> numbers;

	private Thread thread;
	private boolean playing, restart;

	public BingoDroplet(GameSettings settings)
	{
		this.uuid = UUID.randomUUID();
		this.settings = settings.clone();
		setupDroplet();
	}

	private void setupDroplet()
	{
		this.chat = new BingoChat(uuid);
		this.usersConnected = new HashSet<User>();
		this.cards = new HashSet<BingoCardGroup>();
		this.numbers = new ArrayList<Integer>();
		this.dropletState = GameState.LOBBY;
		this.lineState = LineState.ONE;
		setupNumbers();
		start();
	}

	public synchronized void start()
	{
		if (isPlaying())
			return;

		Thread thread = new Thread(() ->
		{
			playing = true;
			do
			{
				updateGameState(GameState.LOBBY);
				new LobbyRunnable(this).run();
				updateGameState(GameState.PLAYING);
				rig();
				new GameRunnable(this).run();
				restart();
			} while (isPlaying());
			dispose();
		});
		thread.start();
	}

	public void addUser(User user)
	{
		synchronized (usersConnected)
		{
			usersConnected.add(user);
		}
	}

	public synchronized void dispose()
	{
		try
		{
			if (!isPlaying())
				return;
			this.playing = false;
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	private void setupNumbers()
	{
		for (int i = 1; i < settings.getMaxNumbers(); i++)
			numbers.add(i);

		Collections.shuffle(numbers);

	}

	private void rig()
	{
	}

	private void restart()
	{
		if (!shouldRestart())
			return;

		lineState = LineState.ONE;
		cards.clear();
		setupNumbers();
		sendAllNewCards();
		this.restart = false;
	}

	public int pickNumber()
	{
		if (allNumbersUsed())
			return -1;

		int number = numbers.get(0);
		numbers.remove(0);
		return number;
	}

	private void markNumber(int number)
	{
		for (BingoCardGroup card : getCardsInPlay())
		{
			card.markNumber(number);
		}
	}

	public void handleWinning()
	{
		Set<BingoCard> winners = getWinningCards();

		double prize = settings.getWinningPrize(lineState, winners.size());
		sendWinnerInformation(winners, prize);

		if (lineState != LineState.FULLHOUSE)
		{
			updateLineState();
		} else
			restart = true;
	}

	private void updateLineState()
	{
		int index = lineState.getLinesRequired() + 1;
		lineState = LineState.getByLines(index);
		sendLineState();
	}

	private void updateGameState(GameState state)
	{
		this.dropletState = state;
		sendGameState();
	}

	public boolean checkWinners(int number)
	{
		boolean status = false;
		markNumber(number);

		Set<BingoCardGroup> cardsInPlay = getCardsInPlay();
		for (BingoCardGroup card : cardsInPlay)
		{
			if (card.hasWinners(lineState))
				status = true;
		}
		return status;
	}

	public Set<BingoCard> getWinningCards()
	{
		Set<BingoCard> winners = new HashSet<>();
		for (BingoCardGroup group : getCardsInPlay())
			for (BingoCard card : group.getWinningCards())
				winners.add(card);
		return winners;
	}

	public Set<BingoCardGroup> getCardsInPlay()
	{
		Set<BingoCardGroup> cardsInPlay = new HashSet<>();
		for (BingoCardGroup card : cards)
			if (card.hasPurchasedCards())
				cardsInPlay.add(card);
		return cardsInPlay;
	}

	public void sendPurchasedCards()
	{
		for (User user : getPlayingUsers())
			sendPurchasedCards(user);
	}

	/**
	 * Sends all the currently connected Users new cards to play with.
	 */
	private void sendAllNewCards()
	{
		synchronized (usersConnected)
		{
			for (User user : usersConnected)
			{
				sendPotentialCards(14, user);
			}
		}
	}

	/**
	 * Sends the UUIDs of their purchased cards.
	 * 
	 * @param user The {@link User} to send to.
	 */
	private void sendPurchasedCards(User user)
	{
		BingoCardGroup cards = getCards(user);
		if (!cards.hasPurchasedCards())
			return;

		PacketHandler.sendPacket(getPuchasedCardsPacket(user.getClient(), cards));
	}

	/**
	 * Sends the User a new set of cards.
	 * 
	 * @param id   The id of the packet.
	 * @param user The {@link User} to send to.
	 */
	private void sendPotentialCards(int id, User user)
	{
		PacketHandler.sendPacket(getUsercards(id, user.getClient(), generateCards(user)));
	}

	@SuppressWarnings("unchecked")
	private void sendWinnerInformation(Set<BingoCard> cards, double prize)
	{
		JSONArray array = new JSONArray();
		for (BingoCard card : cards)
		{
			array.add(card.getOwner().getUserInformation().getDisplayName());
			CurrencyController.getController().increment(card.getOwner(), prize);
		}

		for (User user : getPlayingUsers())
		{
			PacketHandler.sendPacket(getWinnerInformationPacket(user.getClient(), array));
		}
	}

	private void sendLineState()
	{
		for (User user : usersConnected)
		{
			PacketHandler.sendPacket(getLineStatePacket(user.getClient()));
		}
	}

	private void sendGameState()
	{
		for (User user : usersConnected)
		{
			PacketHandler.sendPacket(getGameStatePacket(user.getClient()));
		}
	}

	public void sendNextNumber(int number)
	{
		for (User user : usersConnected)
		{
			PacketHandler.sendPacket(getNextNumberPacket(user.getClient(), number));
		}
	}

	public void sendMessage(ChatMessage message)
	{
		getChat().sendMessage(usersConnected, message);
	}

	/**
	 * Generates the User a new set of cards.
	 * 
	 * @param user The {@link User} to generate for.
	 * @return {@link BingoCardGroup}
	 */
	public BingoCardGroup generateCards(User user)
	{
		BingoCardGroup group = new BingoCardGroup(user, 3, settings.getMaxNumbers());
		group.generateCards();
		cards.add(group);
		return group;
	}

	// PACKET STUFF

	@SuppressWarnings("unchecked")
	private Packet getUsercards(int id, Client client, BingoCardGroup cards)
	{
		Packet packet = PacketHandler.createPacket(client, id, null);
		JSONObject message = new JSONObject();
		message.put("dropletUUID", uuid.toString());
		message.put("cards", cards.getCardsJSON());
		packet.setMessageSection(message);
		return packet;
	}

	@SuppressWarnings("unchecked")
	private Packet getPuchasedCardsPacket(Client client, BingoCardGroup cards)
	{
		Packet packet = PacketHandler.createPacket(client, 11, null);
		JSONObject message = new JSONObject();
		message.put("dropletUUID", uuid.toString());
		message.put("purchasedCards", cards.getPurchasedCardsArray());
		packet.setMessageSection(message);
		return packet;
	}

	@SuppressWarnings("unchecked")
	private Packet getWinnerInformationPacket(Client client, JSONArray winners)
	{
		Packet packet = PacketHandler.createPacket(client, 13, null);
		JSONObject message = new JSONObject();
		message.put("dropletUUID", uuid.toString());
		message.put("winners", winners);
		packet.setMessageSection(message);
		return packet;
	}

	private Packet getLineStatePacket(Client client)
	{
		Packet packet = PacketHandler.createPacket(client, 12, null);
		JSONObject message = new JSONObject();
		message.put("dropletUUID", uuid.toString());
		message.put("lineState", lineState.getLinesRequired());
		packet.setMessageSection(message);
		return packet;
	}

	private Packet getGameStatePacket(Client client)
	{
		Packet packet = PacketHandler.createPacket(client, 10, null);
		JSONObject message = new JSONObject();
		message.put("dropletUUID", uuid.toString());
		message.put("gameState", dropletState.getStateCode());
		packet.setMessageSection(message);
		return packet;
	}

	// TODO send previous numbers.
	public Packet getNextNumberPacket(Client client, int num)
	{
		Packet packet = PacketHandler.createPacket(client, 9, null);
		JSONObject message = new JSONObject();
		message.put("dropletUUID", uuid.toString());
		message.put("number", num);
		message.put("previousNumbers", new JSONArray());
		packet.setMessageSection(message);
		return packet;
	}

	public Set<User> getPlayingUsers()
	{
		Set<User> users = new HashSet<User>();
		for (BingoCardGroup group : getCardsInPlay())
			users.add(group.getOwner());
		return users;
	}

	public BingoCardGroup getCards(User user)
	{
		synchronized (cards)
		{
			for (BingoCardGroup cards : cards)
				if (cards.getOwner() == user)
					return cards;
			return null;
		}
	}

	public BingoChat getChat()
	{
		return chat;
	}

	public JSONObject toJSON()
	{
		JSONObject object = new JSONObject();
		object.put("players", getConnectedPlayersSize());
		return object;
	}

	public boolean hasPlayer(User user)
	{
		return usersConnected.contains(user);
	}

	private boolean allNumbersUsed()
	{
		return numbers.isEmpty();
	}

	public boolean isPlaying()
	{
		return playing;
	}

	public boolean shouldRestart()
	{
		return restart;
	}

	public boolean hasSpace()
	{
		return usersConnected.size() < getSettings().getMaxPlayers();
	}

	public int getConnectedPlayersSize()
	{
		return usersConnected.size();
	}

	public GameSettings getSettings()
	{
		return settings;
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public GameState getGameState()
	{
		return dropletState;
	}

}
