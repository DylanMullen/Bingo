package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_Chat extends Packet
{

	public Packet_Chat(Client c, JSONObject message)
	{
		super(16, c, message);
	}

	@Override
	public void handle()
	{
		User user = UserManager.getInstance().getUser(getSenderUUID());
		
		UUID dropletUUID = UUID.fromString((String) getMessageSection().get("dropletUUID"));
		String message = (String) getMessageSection().get("chatMessage");
		BingoServer.getInstance().getGame().handleChatMessage(user, dropletUUID, message);
	}

}
