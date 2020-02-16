package me.dylanmullen.bingo.net.packet;

public enum PacketType
{
	INVALID(-1), //
	LOGIN(001), //
	DISCONNECT(002), //
	REQUEST(003);

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
