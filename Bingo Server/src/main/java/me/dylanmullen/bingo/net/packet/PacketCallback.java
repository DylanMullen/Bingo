package me.dylanmullen.bingo.net.packet;

import me.dylanmullen.bingo.util.Callback;

public class PacketCallback implements Callback
{

	private String data;

	public PacketCallback()
	{
	}

	@Override
	public boolean callback()
	{
		System.out.println(data);
		return true;
	}

	public void setData(String decode)
	{
		data=decode;
	}

}
