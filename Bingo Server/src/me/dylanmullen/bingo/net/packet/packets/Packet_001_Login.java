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

	// /id/001/m/<uuid>/t/<time>
	public Packet_001_Login(PacketHandler handler, InetAddress address, int port, String data)
	{
		super(handler, address, port, PacketType.LOGIN.getID(), data);
	}

	@Override
	public void handle()
	{
		String mes = getAbsoluteData()[0];
		String time = getAbsoluteData()[1];

		UUID uuid = UUID.fromString(mes);
		long millis = Long.parseLong(time);

		Client c = new Client(getAddress(), getPort());
		UserManager.getInstance().create(c, uuid);
		System.out.println("Connected @ " + System.currentTimeMillis());
		System.out.println("Time Taken: " + (System.currentTimeMillis() - millis));
		System.out.println("Users: " + UserManager.getInstance().users.size());
	}

}
