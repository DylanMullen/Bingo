package me.dylanmullen.bingo.net.packet;

import java.util.UUID;

import me.dylanmullen.bingo.net.EncryptionHandler;

public class PacketTicket
{

	private Packet packet;
	private PacketCallback callback;
	private UUID uuid;

	private boolean rsa;

	public PacketTicket(Packet packet, boolean rsa, PacketCallback callback)
	{
		this.packet = packet;
		this.callback = callback;
		this.uuid = UUID.randomUUID();
		this.rsa = rsa;

		packet.setPacketUUID(getUUID());
		setup();
	}

	private void setup()
	{
		if (rsa)
			packet.setEncrypted(EncryptionHandler.encryptRSA(packet.getSender(), packet.toString()));
		else
			packet.setEncrypted(EncryptionHandler.encryptAES(packet.getSender(), packet.toString()));
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
