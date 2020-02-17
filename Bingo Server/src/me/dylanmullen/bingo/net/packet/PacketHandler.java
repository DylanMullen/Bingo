package me.dylanmullen.bingo.net.packet;

import java.util.UUID;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.handlers.ServerHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_001_Login;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response.ResponseType;

public class PacketHandler
{
	
	public static final String PACKET_FORMAT = "?id;/m/?message/m/;?pu;?time";

	public void handle(Client c, String data)
	{
		int id = (Integer.parseInt(data.split(";", 2)[0]));
		Packet packet = createPacket(c, id, data);
		
		if(packet != null)
			packet.handle();
	}
	
	public static Packet createPacket(Client c, int id, String message)
	{
		PacketType type = PacketType.getPacketTypeByID(id);
		
		switch(type)
		{
			case LOGIN:
				return new Packet_001_Login(c, message);
			case REGISTER:
				break;
			case DISCONNECT:
				break;
			case REQUEST:
				break;
			case RESPONSE:
				return new Packet_005_Response(c, null, message, null);
			case INVALID:
				break;
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
