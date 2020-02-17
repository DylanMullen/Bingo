package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class Packet_002_Register extends Packet
{

	// username/nl/password/nl/email
	public Packet_002_Register(int id, Client c, String message)
	{
		super(id, c, message, false);
	}

	@Override
	public void handle()
	{
		String[] message = getMessage().split(";");
		UUID uuid = getUUID();
		
		String username = message[0];
		String password = message[1];
		String email = message[2];
		
		// Auth
		
		String response = "ACCEPTED;"+uuid;
		PacketHandler.createPacket(getClient(), 005, response);
		
	}
	
	

}
