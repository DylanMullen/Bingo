package me.dylanmullen.bingo.net;

import me.dylanmullen.bingo.net.handlers.ClientHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.net.packet.PacketType;

public class PacketHandler
{

	public static Packet createPacket(int id, String data)
	{
		PacketType type = PacketType.getPacket(id);
		if (type == PacketType.INVALID)
			return null;

		Packet p = new Packet(type.getID(), data);
		return p;
	}

	public static void sendPacket(Packet packet, PacketCallback callback)
	{
		PacketTicket ticket = new PacketTicket(packet, callback);
		ClientHandler.getInstance().submitTicket(ticket);
	}
}
