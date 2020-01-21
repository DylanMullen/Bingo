package me.dylanmullen.bingo.net.packet.packets;

import java.net.InetAddress;
import java.util.UUID;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.PacketType;

public class Packet_002_Disconnect extends Packet
{

	public Packet_002_Disconnect(PacketHandler handler, InetAddress address, int port, String data)
	{
		super(handler, address, port, PacketType.DISCONNECT.getID(), data);
	}

	@Override
	public void handle()
	{
		System.out.println(getData());
		String mes = getAbsoluteData()[0];
		long time = Long.parseLong(getAbsoluteData()[1]);
		
		UUID uuid = UUID.fromString(mes);
		System.out.println();
		UserManager.getInstance().remove(UserManager.getInstance().getUser(uuid));
		
		System.out.println("Disconnected @ " + System.currentTimeMillis());
		System.out.println("Time Taken: " + (System.currentTimeMillis() - time));

	}

}
