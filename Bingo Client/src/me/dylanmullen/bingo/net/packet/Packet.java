package me.dylanmullen.bingo.net.packet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Packet
{

	private int id;
	private String data;
	private DateFormat formatter;

	// /id/<id>/m/(message/nl/message...)/t/<time>
	public Packet(int id, String data)
	{
		this.id = id;
		setData(data);

		this.formatter = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss.SSS");
	}

	protected String generatePacketString(String message)
	{
		message = message.replace("\n", "/nl/");
		return "/id/" + getID() + "/m/" + message + "/t/" + System.currentTimeMillis();
	}

	private String getID()
	{
		String s = id + "";
		switch (s.length())
		{
			case 1:
				s = "00" + id;
				break;
			case 2:
				s = "0" + id;
				break;
		}
		return s;
	}

	protected String getData()
	{
		return data;
	}

	protected void setData(String data)
	{
		this.data = generatePacketString(data);
	}

	protected DateFormat getFormatter()
	{
		return formatter;
	}

	public byte[] getDataByte()
	{
		return getData().getBytes();
	}
}
