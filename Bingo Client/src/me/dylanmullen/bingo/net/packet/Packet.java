package me.dylanmullen.bingo.net.packet;

import java.net.DatagramPacket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

public class Packet
{

	private int id;
	private String data;
	private DateFormat formatter;
	
	private final String format = "?id;/m/?message/m/;?pu;?time";

	// <id>;/m<message>m/;<packetUUID>;<time>
	public Packet(int id, String data)
	{
		this.id = id;
		setData(data);

		this.formatter = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss.SSS");
	}

	protected String generatePacketString(String message)
	{
		String temp = format;
		temp = temp.replace("?id", getID());
		temp = temp.replace("?message", message);
		return temp;
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
	
	public void setTime()
	{
		data = data.replace("?time", System.currentTimeMillis()+"");
	}
	
	public void setPacketUUID(UUID uuid)
	{
		data = data.replace("?pu", uuid.toString());
	}

	public DatagramPacket convert(Client c)
	{
		return new DatagramPacket(getDataByte(), getDataByte().length, c.getIP(), c.getPort());
	}
}
