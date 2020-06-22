package me.dylanmullen.bingo.net.packet.packets;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_007_RequestCard extends Packet
{

	public Packet_007_RequestCard(Client c, JSONObject message)
	{
		super(7, c, message);
	}

	@Override
	public void handle()
	{
		User user = UserManager.getInstance().getUser(getSenderUUID());
		BingoServer.getInstance().getGame().handleCardRequest(user, getPacketUUID());
	}

}
