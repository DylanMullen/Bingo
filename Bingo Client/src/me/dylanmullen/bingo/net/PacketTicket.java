package me.dylanmullen.bingo.net;

import java.util.UUID;

import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class PacketTicket
{

	private Packet packetToSend;
	private PacketCallback callback;

	private UUID uuid;

	/**
	 * A packet ticket of a Packet. This contains the packet and the callback that
	 * might be called if a response is received from the server.
	 * 
	 * @param packet   The packet to send.
	 * @param callback The callback to call if a response is received.
	 */
	public PacketTicket(Packet packet, PacketCallback callback)
	{
		this.packetToSend = packet;
		this.callback = callback;
		this.uuid = UUID.randomUUID();
		packet.setPacketUUID(getUUID());
	}

	/**
	 * @return Returns the packet to send.
	 */
	public Packet getPacketToSend()
	{
		return this.packetToSend;
	}

	/**
	 * @return Returns the callback to call.
	 */
	public PacketCallback getCallback()
	{
		return this.callback;
	}

	/**
	 * @return Returns the UUID of the Packet.
	 */
	public UUID getUUID()
	{
		return this.uuid;
	}

}
