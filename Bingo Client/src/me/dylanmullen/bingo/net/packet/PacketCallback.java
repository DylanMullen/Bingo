package me.dylanmullen.bingo.net.packet;

import me.dylanmullen.bingo.util.Callback;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public abstract class PacketCallback implements Callback
{

	private String data;

	/**
	 * A callback being set off when a packet is received a response from the Bingo
	 * Server.
	 */
	public PacketCallback()
	{
	}

	/**
	 * Sets the data of the Packet
	 * 
	 * @param data
	 */
	public void setData(String data)
	{
		this.data = data;
	}

	/**
	 * @return Returns the data of the Packet response.
	 */
	public String getData()
	{
		return this.data;
	}

	/**
	 * Gets the message part of the packet response.
	 * 
	 * @return Returns the message part of the packet.
	 */
	public String getMessage()
	{
		return data.split("/m/|/m/")[1];
	}
}
