package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_006_JoinGame extends Packet
{

	public Packet_006_JoinGame(Client c, JSONObject message)
	{
		super(6, c, message);
	}

	@Override
	public void handle()
	{
		User user = UserManager.getInstance().getUser(getSenderUUID());
		UUID cloudUUID = UUID.fromString((String) getMessageSection().get("cloudUUID"));
		UUID dropletUUID = UUID.fromString((String) getMessageSection().get("dropletUUID"));

		BingoServer.getInstance().getGame().connectUser(user, cloudUUID, dropletUUID, getPacketUUID());
	}
}
