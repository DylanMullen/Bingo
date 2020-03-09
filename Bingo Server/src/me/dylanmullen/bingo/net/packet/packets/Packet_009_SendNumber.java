package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_009_SendNumber extends Packet
{
	
	// 9;/m/<number>/m/
	public Packet_009_SendNumber(Client c, String message)
	{
		super(9, c, message, true);
	}

	@Override
	public void handle()
	{

	}

}
