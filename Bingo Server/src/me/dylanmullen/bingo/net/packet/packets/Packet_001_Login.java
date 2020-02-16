package me.dylanmullen.bingo.net.packet.packets;

import java.net.InetAddress;
import java.util.UUID;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.PacketType;

public class Packet_001_Login extends Packet
{

	// /id/001/m/username/nl/password/t/<time>
	public Packet_001_Login(PacketHandler handler, InetAddress address, int port, String data)
	{
		super(handler, address, port, PacketType.LOGIN.getID(), data);
	}

	@Override
	public void handle()
	{
		String mes = getAbsoluteData()[0];
		
		String username = mes.split("/nl/")[0];
		String password = mes.split("/nl/")[1];
		
		UUID uuid = UUID.randomUUID();
		UserManager.getInstance().create(new Client(getAddress(), getPort()), uuid);
		
		System.out.println(username + " "+password);
	}

}
