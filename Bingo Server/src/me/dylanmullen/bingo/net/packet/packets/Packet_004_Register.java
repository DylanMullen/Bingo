package me.dylanmullen.bingo.net.packet.packets;

import java.net.InetAddress;

import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class Packet_004_Register extends Packet
{

	// /id/004/m/username/nl/password/t/time
	public Packet_004_Register(PacketHandler handler, InetAddress address, int port, int id, String data)
	{
		super(handler, address, port, id, data);
	}

	@Override
	public void handle()
	{
		
	}

}
