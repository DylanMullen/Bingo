package me.dylanmullen.bingo.net.packet;

import java.util.UUID;

public class PacketTicket
{

	private Packet packet;
	private PacketCallback callback;
	private UUID uuid;

	public PacketTicket(Packet packet, PacketCallback callback)
	{
		this.packet = packet;
		this.callback = callback;
		this.uuid = UUID.randomUUID();
		
		packet.setPacketUUID(getUUID());
	}
	
	public Packet getPacket()
	{
		return packet;
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
