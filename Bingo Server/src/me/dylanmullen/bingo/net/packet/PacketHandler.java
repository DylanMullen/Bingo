package me.dylanmullen.bingo.net.packet;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.handlers.ServerHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_001_Login;
import me.dylanmullen.bingo.net.packet.packets.Packet_004_Request;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response;
import me.dylanmullen.bingo.net.packet.packets.Packet_006_JoinGame;
import me.dylanmullen.bingo.net.packet.packets.Packet_007_RequestCard;

public class PacketHandler
{

	public static final String PACKET_FORMAT = "?id;/m/?message/m/;?pu;?time";

	public void handle(Client c, String data)
	{
		int id = (Integer.parseInt(data.split(";", 2)[0]));
		Packet packet = createPacket(c, id, data);

		if (packet != null)
			packet.handle();
	}

	public static Packet createPacket(Client c, int id, String message)
	{
		PacketType type = PacketType.getPacketTypeByID(id);

		switch (type)
		{
			case LOGIN:
				return new Packet_001_Login(c, message);
			case REGISTER:
				break;
			case DISCONNECT:
				break;
			case REQUEST:
				return new Packet_004_Request(004, c, message, false);
			case RESPONSE:
				return new Packet_005_Response(c, null, message, null);
			case JOIN_GAME:
				return new Packet_006_JoinGame(c, message);
			case REQUEST_CARD:
				return new Packet_007_RequestCard(c, message);
			default:
		}
		return null;
	}

	public static void sendPacket(Packet packet, PacketCallback callback)
	{
		PacketTicket ticket = new PacketTicket(packet, callback);
		ServerHandler.getHandler().submitTicket(ticket);
	}

}
