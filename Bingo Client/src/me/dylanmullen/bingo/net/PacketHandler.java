package me.dylanmullen.bingo.net;

import me.dylanmullen.bingo.game.BingoGame;
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

	public static void handlePacket(String data)
	{
		int id = (Integer.parseInt(data.split(";", 2)[0]));
		System.out.println("recieveed");
		switch (id)
		{
			case 9:
				BingoGame.getInstance().setNextNumber(data);
				break;
			case 10:
				BingoGame.getInstance().setState(Integer.parseInt(data.split(";")[1].split("/m/|/m/")[1]));
				break;
			case 11:
				BingoGame.getInstance().updateCards(data);
			default:
				return;
		}
	}
}
