package me.dylanmullen.bingo.net.packet.packets;

import java.net.InetAddress;
import java.util.Date;
import java.util.UUID;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.user.BUser;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_001_Login extends Packet
{

	private InetAddress address;
	private int port;

	public Packet_001_Login(InetAddress address, int port, String data)
	{
		super(data);
		this.address = address;
		this.port = port;
	}

	/*
	 * Packet: 001 UUID Time Sent
	 */
	@Override
	public void handle()
	{
		String[] data = getData().split("/nl/");
		UUID uuid = UUID.fromString(data[0]);
		long time = Long.parseLong(data[1]);

		printTime(time);

		Client c = new Client(address, port);
		System.out.println(c.getAddress().getHostAddress());
		System.out.println(c.getPort());
		BUser user = new BUser(c, uuid);

		UserManager.getInstance().add(user);
		setData("001\nConnected\n" + System.currentTimeMillis());
		BingoServer.getInstance().getServer().send(c, this);
	}

	private void printTime(long time)
	{
		Date d = new Date(time);
		System.out.println("Client sent Packet: " + getFormatter().format(d));
		System.out.println("Time taken: " + (System.currentTimeMillis() - time) + "ms");
	}

}
