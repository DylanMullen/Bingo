package me.dylanmullen.bingo.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client
{

	private DatagramSocket socket;
	private InetAddress ip;
	private int port;

	public Client(String address, int port)
	{
		try
		{
			this.socket = new DatagramSocket();
			this.ip = InetAddress.getByName(address);
			this.port = port;
		} catch (SocketException | UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public DatagramSocket getSocket()
	{
		return socket;
	}
	
	public InetAddress getIP()
	{
		return ip;
	}
	
	public int getPort()
	{
		return port;
	}

}
