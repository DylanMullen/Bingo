package me.dylanmullen.bingo.game.user;

import java.util.UUID;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.net.Client;

public class BUser
{

	private Client c;
	private UUID uuid;

	private BUserInformation info;
	private BingoCard card;

	public BUser(Client c, UUID uuid)
	{
		this.c = c;
		this.uuid = uuid;
		init();
	}

	private void init()
	{
		this.info = new BUserInformation(uuid);
	}
	
	public void setCard(BingoCard card)
	{
		this.card = card;
	}

	public Client getClient()
	{
		return c;
	}
	
	public UUID getUuid()
	{
		return uuid;
	}
	
	public BUserInformation getInfo()
	{
		return info;
	}

	public BingoCard getCard()
	{
		return card;
	}
	
}
