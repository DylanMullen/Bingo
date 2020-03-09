package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_011_SendCards extends Packet
{

	public Packet_011_SendCards(Client c, String message)
	{
		super(11, c, message, true);
	}

	@Override
	public void handle()
	{

	}

}
