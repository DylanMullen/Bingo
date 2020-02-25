package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.game.GameController;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_004_Request extends Packet
{

	public Packet_004_Request(int id, Client c, String message, boolean format)
	{
		super(id, c, message, format);
	}

	@Override
	public void handle()
	{
		String[] message = getMessage().split("/nl/");
		
		switch(message[0])
		{
			case "CARD":
				GameController.getInstance().handleCardRequest(UserManager.getInstance().getUser(getClient()), getUUID());
				return;
		}
		
	}

}
