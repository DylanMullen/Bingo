package me.dylanmullen.bingo.net.packet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.util.Callback;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public abstract class PacketCallback implements Callback
{

	private JSONObject data;

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
	public void setData(JSONObject data)
	{
		this.data = data;
	}

	/**
	 * @return Returns the data of the Packet response.
	 */
	public JSONObject getData()
	{
		return this.data;
	}

	/**
	 * Gets the message part of the packet response.
	 * 
	 * @return Returns the message part of the packet.
	 */
	public JSONObject getMessage()
	{
		return (JSONObject) data.get("packetMessage");
	}

	public int getResponseType()
	{
		return ((Number) getMessage().get("responseType")).intValue();
	}
	
	public UUID getDropletUUID()
	{
		return UUID.fromString((String)getMessage().get("dropletUUID"));
	}
}
