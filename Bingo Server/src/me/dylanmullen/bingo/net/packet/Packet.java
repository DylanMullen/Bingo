package me.dylanmullen.bingo.net.packet;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class Packet
{

	private int id;
	private String data;
	private DateFormat formatter;

	private PacketHandler handler;
	private InetAddress address;
	private int port;

	// /id/<id>/m/(message/nl/message...)/t/<time>
	public Packet(PacketHandler handler, InetAddress address, int port, int id, String data)
	{
		this.handler = handler;
		this.address = address;
		this.port = port;
		this.data = data;
		this.id = id;

		this.formatter = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss.SSS");
	}

	public abstract void handle();

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

	protected String[] getAbsoluteData()
	{
		String s = getData();
		s = s.replace("/m/", "");
		return s.split("/t/");
	}

	protected void sendPacket()
	{
		handler.sendPacket(this);
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

	protected InetAddress getAddress()
	{
		return address;
	}

	protected int getPort()
	{
		return port;
	}

	public byte[] getDataByte()
	{
		return getData().getBytes();
	}
}
