package me.dylanmullen.bingo.net;

import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketType;

public class PacketHandler
{
	
	public static Packet createPacket(int id, String data)
	{
		PacketType type = PacketType.getPacket(id);
		if(type == PacketType.INVALID)
			return null;
		
		Packet p = new Packet(type.getID(),data);
		System.out.println(type.getID());
		return p;
	}
	
	public static void sendPacket(Client c, Packet packet)
	{
		c.sendPacket(packet);
	}
}
