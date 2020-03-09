package me.dylanmullen.bingo.net.packet;

public enum PacketType
{
	INVALID(-1), LOGIN(001), REGISTER(002), DISCONNECT(003), REQUEST(004), RESPONSE(005), JOIN_GAME(006),
	REQUEST_CARD(007), PURCHASE_CARD(8);

	private int id;

	PacketType(int id)
	{
		this.id = id;
	}

	public int getID()
	{
		return id;
	}

	public static PacketType getPacket(int id)
	{
		for (PacketType type : PacketType.values())
		{
			if (type.id == id)
				return type;
		}

		return PacketType.INVALID;
	}
}
