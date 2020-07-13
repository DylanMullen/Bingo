package me.dylanmullen.bingo.net.packet.packets;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_RetrieveClouds extends Packet
{

	public Packet_RetrieveClouds(Client client, JSONObject object)
	{
		super(17, client, object);
	}

	@Override
	public void handle()
	{
		BingoServer.getInstance().getGame().handleCloudRetrieval(getSender(), getPacketUUID());
	}

}
