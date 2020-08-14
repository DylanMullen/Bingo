package me.dylanmullen.bingo.net.packet.packets;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class Packet_004_Ping extends Packet
{

	public Packet_004_Ping(Client c, JSONObject message)
	{
		super(4, c, message);
	}

	@Override
	public void handle()
	{
		Packet packet = PacketHandler.createPacket(getSender(), 5, null);
		packet.setMessageSection(constructMessage());
		packet.setPacketUUID(getPacketUUID());
		PacketHandler.sendPacket(packet);
	}

	@SuppressWarnings("unchecked")
	private JSONObject constructMessage()
	{
		JSONObject message = new JSONObject();
		message.put("responseType", 200);
		return message;
	}

}
