package me.dylanmullen.bingo.net.packet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class Packet
{
	
	private String data;
	private DateFormat formatter;

	public Packet(String data)
	{
		this.data=data;
		this.formatter=new SimpleDateFormat("dd/MM/yyyy @ HH:mm:ss.SSS");
	}
	
	public abstract void handle();
	
	protected String getData()
	{
		return data;
	}
	
	protected void setData(String data)
	{
		this.data = data;
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
