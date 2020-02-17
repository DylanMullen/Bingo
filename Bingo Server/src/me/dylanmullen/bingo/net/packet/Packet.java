package me.dylanmullen.bingo.net.packet;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

public abstract class Packet
{

	private int id;
	private Client client;
	private String data;

	private InetAddress address;
	private int port;

	public Packet(int id, Client c, String message, boolean format)
	{
		this.id = id;
		this.client = c;
		if(format)
			setDataFormat(message);
		else
			this.data=message;
	}
	
	public abstract void handle();

	protected String getMessage()
	{
		String[] dataSplit = data.split(";");
		return dataSplit[1].split("/m/|/m/")[1];
	}
	
	protected UUID getUUID()
	{
		String[] dataSplit = data.split(";");
		return UUID.fromString(dataSplit[2]);
	}
	
	public DatagramPacket convert()
	{
		return new DatagramPacket(getDataAsByteArr(), getDataAsByteArr().length, client.getAddress(), client.getPort());
	}
	
	public void setDataFormat(String message)
	{
		String temp = PacketHandler.PACKET_FORMAT;
		temp = temp.replace("?id", getID());
		temp = temp.replace("?message", message);
		this.data = temp;
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

	public void setTime()
	{
		data = data.replace("?time", System.currentTimeMillis() + "");
	}

	public void setPacketUUID(UUID uuid)
	{
		data = data.replace("?pu", uuid.toString());
	}

	public String getData()
	{
		return data;
	}

	public byte[] getDataAsByteArr()
	{
		return data.getBytes();
	}

	public Client getClient()
	{
		return client;
	}

}
