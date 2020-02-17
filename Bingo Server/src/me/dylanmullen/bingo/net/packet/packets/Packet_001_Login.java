package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_001_Login extends Packet
{

	@SuppressWarnings("unused")
	private final String format = "username/nl/password";
	
	public Packet_001_Login(Client client, String message)
	{
		super(001, client, message, false);
	}

	@Override
	public void handle()
	{
		String message = getMessage();
		UUID uuid = getUUID();
		
		String username = message.split("/nl/")[0];
		String password = message.split("/nl/")[1];
		
		//Auth
		UserManager.getInstance().login(getClient(), uuid, username, password);
	}
	
	

}
