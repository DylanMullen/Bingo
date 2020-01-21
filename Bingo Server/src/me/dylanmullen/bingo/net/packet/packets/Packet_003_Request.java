package me.dylanmullen.bingo.net.packet.packets;

import java.net.InetAddress;
import java.util.UUID;

import me.dylanmullen.bingo.controllers.GameController;
import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.PacketType;

public class Packet_003_Request extends Packet
{

	// /m/<request>/nl/uuid/nl/extras.../t/
	public Packet_003_Request(PacketHandler handler, InetAddress address, int port, String data)
	{
		super(handler, address, port, PacketType.REQUEST.getID(), data);
	}

	@Override
	public void handle()
	{
		String[] mes = getAbsoluteData()[0].split("/nl/");
		UUID uuid = UUID.fromString(mes[1]);
		System.out.println(getAbsoluteData()[1]);
		
		switch(mes[0])
		{
			case "bingo-card":
				BingoCard card = GameController.getInstance().createBingoCard(uuid);
				setData(card.toString());
				sendPacket();
				break;
			default:
				break;
		}
	}

}
