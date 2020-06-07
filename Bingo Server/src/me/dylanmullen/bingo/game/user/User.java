package me.dylanmullen.bingo.game.user;

import java.util.UUID;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class User
{

	private Client client;
	private UUID uuid;
	private UserInformation userInformation;

	private BingoGame currentGame;

	public User(Client c, UUID uuid)
	{
		this.client = c;
		this.uuid = uuid;
	}

	public void loadInformation(UUID packetUUID)
	{
		userInformation = new UserInformation(uuid);
		userInformation.load(client, packetUUID);
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
