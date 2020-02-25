package me.dylanmullen.bingo.game;

import java.util.HashSet;
import java.util.UUID;

import me.dylanmullen.bingo.game.user.User;

public class BingoGame
{

	private UUID gameUUID;
	private HashSet<User> users;
	private HashSet<BingoCard> cardsInPlay;
	private HashSet<CardGroup> potentialCards;
	private long seed;

	private int maxPlayers;

	public BingoGame(int playerCap)
	{
		this.gameUUID = UUID.randomUUID();
		this.users = new HashSet<User>();

		this.maxPlayers = playerCap;
		this.seed = System.currentTimeMillis();
	}

	public void start()
	{

	}

	public void end()
	{
	}

	public void addPlayer(User u)
	{
		if (users.contains(u))
			return;
		users.add(u);
		u.setCurrentGame(this);
	}

	public BingoCard generateCard(User user)
	{
		BingoCard card = new BingoCard(user, seed);
		card.generate();
		return card;
	}
	
	public CardGroup generateCardGroup(User u)
	{
		CardGroup cg = new CardGroup(seed, u);
		cg.generate();
		return cg;
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

	public UUID getGameUUID()
	{
		return gameUUID;
	}

	public HashSet<User> getPlayers()
	{
		return users;
	}

}
