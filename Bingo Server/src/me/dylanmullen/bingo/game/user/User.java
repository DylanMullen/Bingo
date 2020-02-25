package me.dylanmullen.bingo.game.user;

import java.util.UUID;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.net.Client;

public class User
{

	private Client client;
	private UUID uuid;
	
	private BingoGame currentGame;

	public User(Client c, UUID uuid)
	{
		this.client = c;
		this.uuid = uuid;
	}

	public void setCurrentGame(BingoGame currentGame)
	{
		this.currentGame = currentGame;
	}
	
	public BingoGame getCurrentGame()
	{
		return currentGame;
	}
	
	public UUID getUUID()
	{
		return uuid;
	}
	
	public Client getClient()
	{
		return client;
	}
	
}
