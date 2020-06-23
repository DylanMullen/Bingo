package me.dylanmullen.bingo.net.packet;

public enum PacketType
{

	INVALID(-1), HELLO_WORLD(0), LOGIN(1), REGISTER(2), DISCONNECT(3), PING(4), RESPONSE(5), JOIN_GAME(6),
	REQUEST_CARD(7), PURCHASE_CARD(8), SEND_NUMBER(9), GAME_STATE_CHANGE(10), SEND_CARDS(11), CHAT_MESSAGE(16),
	RETRIEVE_CLOUDS(17), RETRIEVE_DROPLETS(18);

	private int id;

	private PacketType(int id)
	{
		this.id = id;
	}

	public static PacketType getPacketTypeByID(int id)
	{
		for (int i = 0; i < PacketType.values().length; i++)
		{
			PacketType type = PacketType.values()[i];
			if (type.id == id)
				return type;
		}
		return INVALID;
	}

	public int getID()
	{
		return id;
	}
}
