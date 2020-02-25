package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.game.GameController;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_007_RequestCard extends Packet
{

	public Packet_007_RequestCard(Client c, String message)
	{
		super(007, c, message, false);
	}

	@Override
	public void handle()
	{
		User user = UserManager.getInstance().getUser(getClient());
		GameController.getInstance().handleCardRequest(user, getUUID());
	}

}
