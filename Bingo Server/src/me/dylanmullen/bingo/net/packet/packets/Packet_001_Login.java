package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_001_Login extends Packet
{

	public Packet_001_Login(Client client, JSONObject packetData)
	{
		super(1, client, packetData);
	}

	@Override
	public void handle()
	{
		UUID uuid = getPacketUUID();

		String username = (String) getMessageSection().get("email");
		String password = (String) getMessageSection().get("password");

		// Auth
		UserManager.getInstance().login(getSender(), uuid, username, password);
	}

}
