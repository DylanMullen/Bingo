package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_010_GameStateChange extends Packet
{

	// /m/state/m/
	public Packet_010_GameStateChange(Client c, String message)
	{
		super(10, c, message, true);
	}

	@Override
	public void handle()
	{
		
	}

}
