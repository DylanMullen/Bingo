package me.dylanmullen.bingo.net.packet.packets;

import org.json.simple.JSONObject;

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
		User user = UserManager.getInstance().getUser(getSender());
		String message = (String) getMessageSection().get("chatMessage");
		
		if (user.getCurrentGame() != null)
			user.getCurrentGame().submitChatMessage(user, message);
	}

}
