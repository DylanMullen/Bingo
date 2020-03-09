package me.dylanmullen.bingo.net.packet;

public enum PacketType
{

	INVALID(-1), LOGIN(001), REGISTER(002), DISCONNECT(003), REQUEST(004), RESPONSE(005), JOIN_GAME(006),
	REQUEST_CARD(007), PURCHASE_CARD(8), SEND_NUMBER(9), GAME_STATE_CHANGE(10), SEND_CARDS(11);

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
