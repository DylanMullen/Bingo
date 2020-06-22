package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_Generic extends Packet
{

	public Packet_Generic(int id, Client c)
	{
		super(id, c, null);
	}

	@Override
	public void handle()
	{
	}

}
