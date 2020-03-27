package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_005_Response extends Packet
{

	public enum ResponseType
	{
		SUCCESS(1), FAILURE(-1), NOT_APPLICABLE(0);

		private int id;

		private ResponseType(int id)
		{
			this.id = id;
		}

		public int getID()
		{
			return id;
		}
	}

	// 005;/m/<id>;<pu>message/m/;?pu;?time
	public Packet_005_Response(Client c, ResponseType type, String message)
	{
		super(005, c, message, true);
	}

	public void constructMessage(ResponseType type, String message, UUID packet)
	{
		setDataFormat(type.getID() + "/nl/" + packet.toString() + "/nl/" + message);
	}

	@Override
	public void handle()
	{
	}

}
