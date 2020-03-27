package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response.ResponseType;

public class Packet_004_Ping extends Packet
{

	public Packet_004_Ping(int id, Client c, String message)
	{
		super(id, c, message, false);
	}

	@Override
	public void handle()
	{
		Packet_005_Response res = (Packet_005_Response) PacketHandler.createPacket(getClient(), 5, "PONG");
		res.constructMessage(ResponseType.SUCCESS, "Pong", getUUID());
		PacketHandler.sendPacket(res, null);
	}

}
