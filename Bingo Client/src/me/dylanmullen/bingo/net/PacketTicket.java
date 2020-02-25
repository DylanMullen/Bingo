package me.dylanmullen.bingo.net;

import java.util.UUID;

import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.util.Callback;

public class PacketTicket
{

	private Packet packetToSend;
	private PacketCallback callback;

	private UUID uuid;

	public PacketTicket(Packet packet, PacketCallback callback)
	{
		this.packetToSend = packet;
		this.callback = callback;
		this.uuid = UUID.randomUUID();
		
		packetToSend.setPacketUUID(getUUID());
	}

	public Packet getPacketToSend()
	{
		return packetToSend;
	}

	public PacketCallback getCallback()
	{
		return callback;
	}
	
	public UUID getUUID()
	{
		return uuid;
	}

}
