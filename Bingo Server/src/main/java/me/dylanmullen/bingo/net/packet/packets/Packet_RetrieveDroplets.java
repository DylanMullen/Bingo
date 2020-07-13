package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_RetrieveDroplets extends Packet
{

	public Packet_RetrieveDroplets(Client client, JSONObject object)
	{
		super(18, client, object);
	}

	@Override
	public void handle()
	{
		UUID cloudUUID = UUID.fromString((String) getMessageSection().get("cloudUUID"));
		BingoServer.getInstance().getGame().handleDropletRetrieval(getSender(), cloudUUID, getPacketUUID());
	}

}
