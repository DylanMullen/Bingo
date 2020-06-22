package me.dylanmullen.bingo.net.packet.packets;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_002_Register extends Packet
{

	// username/nl/password/nl/email
	public Packet_002_Register(Client c, JSONObject packetData)
	{
		super(2, c, packetData);
	}

	@Override
	public void handle()
	{
		String username = (String) getMessageSection().get("email");
		String password = (String) getMessageSection().get("password");

		UserManager.getInstance().register(getSender(), getPacketUUID(), username, password);
	}

}
