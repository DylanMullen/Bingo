package me.dylanmullen.bingo.net.packet;

import java.net.DatagramPacket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class Packet
{

	private int id;
	private String data;
	private DateFormat formatter;

	private final String format = "?id;/m/?message/m/;?pu;?time";

	// <id>;/m<message>m/;<packetUUID>;<time>
	/**
	 * A packet that will be sent to the server. The packet format which is sent
	 * will be:<br>
	 * ?id;/m/?message/m/;?packetUUID;?time
	 * 
	 * @param id   Packet ID
	 * @param data The data to send in the packet.
	 */
	public Packet(int id, String data)
	{
		this.id = id;
		setData(data);

		this.formatter = new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss.SSS");
	}

	/**
	 * Generates a Packet String using the packet {@link #format}.
	 * 
	 * @param message The message to be sent to the server.
	 * @return Returns a Packet String using the packet {@link #format}.
	 */
	protected String generatePacketString(String message)
	{
		String temp = this.format;
		temp = temp.replace("?id", getID());
		temp = temp.replace("?message", message);
		return temp;
	}

	/**
	 * @return Returns the ID in String format with 3 digits.
	 */
	private String getID()
	{
		String s = this.id + "";
		switch (s.length())
		{
			case 1:
				s = "00" + this.id;
				break;
			case 2:
				s = "0" + this.id;
				break;
		}
		return s;
	}

	/**
	 * @return Returns the data of the Packet.
	 */
	protected String getData()
	{
		return this.data;
	}

	/**
	 * Sets the data of the Packet.
	 * 
	 * @param data The data of the packet.
	 */
	protected void setData(String data)
	{
		this.data = generatePacketString(data);
	}

	/**
	 * @return Returns the date format.
	 */
	protected DateFormat getFormatter()
	{
		return this.formatter;
	}

	/**
	 * @return Returns the data represented in bytes.
	 */
	public byte[] getDataByte()
	{
		return getData().getBytes();
	}

	/**
	 * Sets the time of the packet.
	 */
	public void setTime()
	{
		this.data = this.data.replace("?time", System.currentTimeMillis() + "");
	}

	/**
	 * Sets the packet UUID.
	 * 
	 * @param uuid The UUID of the packet.
	 */
	public void setPacketUUID(UUID uuid)
	{
		this.data = this.data.replace("?pu", uuid.toString());
	}

	/**
	 * Creates a Datagram Packet of the packet.
	 * 
	 * @param client The client sending the packet.
	 * @return Returns the Datagram Packet of the packet.
	 */
	public DatagramPacket createDatagramPacket(Client client)
	{
		return new DatagramPacket(getDataByte(), getDataByte().length, client.getIP(), client.getPort());
	}
}
