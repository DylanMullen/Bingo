package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_Generic extends Packet
{

	/*
	 * 12 = LineStateChange
	 * 13 = Send Winners
	 */
	public Packet_Generic(int id, Client c, String message)
	{
		super(id, c, message, true);
	}

	@Override
	public void handle()
	{
	}

}
