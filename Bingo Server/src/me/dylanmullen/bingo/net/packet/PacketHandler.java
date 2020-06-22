package me.dylanmullen.bingo.net.packet;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.handlers.ServerHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_001_Login;
import me.dylanmullen.bingo.net.packet.packets.Packet_002_Register;
import me.dylanmullen.bingo.net.packet.packets.Packet_004_Ping;
import me.dylanmullen.bingo.net.packet.packets.Packet_006_JoinGame;
import me.dylanmullen.bingo.net.packet.packets.Packet_007_RequestCard;
import me.dylanmullen.bingo.net.packet.packets.Packet_008_PurchaseCard;
import me.dylanmullen.bingo.net.packet.packets.Packet_Chat;
import me.dylanmullen.bingo.net.packet.packets.Packet_Generic;

public class PacketHandler
{

	public static final String PACKET_FORMAT = "?id;/m/?message/m/;?pu;?time";

	public void handle(Client c, JSONObject jsonData)
	{
		int id = ((Number) ((JSONObject) jsonData.get("packetInformation")).get("packetID")).intValue();
		Packet packet = createPacket(c, id, jsonData);
		packet.handle();
	}

	public static Packet createPacket(Client c, int id, JSONObject packetData)
	{
		PacketType type = PacketType.getPacketTypeByID(id);

		switch (type)
		{
			case LOGIN:
				return new Packet_001_Login(c, packetData);
			case REGISTER:
				return new Packet_002_Register(c, packetData);
			case PING:
				return new Packet_004_Ping(c, packetData);
			case RESPONSE:
				return new Packet_Generic(5, c);
			case JOIN_GAME:
				return new Packet_006_JoinGame(c, packetData);
			case REQUEST_CARD:
				return new Packet_007_RequestCard(c, packetData);
			case PURCHASE_CARD:
				return new Packet_008_PurchaseCard(c, packetData);
			case CHAT_MESSAGE:
				return new Packet_Chat(c, packetData);
			default:
				return new Packet_Generic(id, c);
		}
	}

	public static void sendPacket(Packet packet, PacketCallback callback)
	{
		PacketTicket ticket = new PacketTicket(packet, callback);
		ServerHandler.getHandler().submitTicket(ticket);
	}

	public static void sendPacket(Packet packet)
	{
		PacketTicket ticket = new PacketTicket(packet, null);
		ServerHandler.getHandler().submitTicket(ticket);
	}

}
