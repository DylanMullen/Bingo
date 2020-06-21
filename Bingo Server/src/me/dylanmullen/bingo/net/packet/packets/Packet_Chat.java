package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_Chat extends Packet
{

	public Packet_Chat(int id, Client c, String message)
	{
		super(id, c, message, false);
	}

	@Override
	public void handle()
	{
		User user = UserManager.getInstance().getUser(getClient());
		String message = getMessage();
		if (user.getCurrentGame() != null)
			user.getCurrentGame().submitChatMessage(user, message);
	}

}
