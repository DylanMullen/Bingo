package me.dylanmullen.bingo.net.packet;

import me.dylanmullen.bingo.util.Callback;

public abstract class PacketCallback implements Callback
{

	private String data;

	public PacketCallback()
	{
	}

	public void setData(String decode)
	{
		data=decode;
	}
	
	public String getData()
	{
		return data;
	}
	
	public String getMessage()
	{
		return data.split("/m/|/m/")[1];
	}
}
