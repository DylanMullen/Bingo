package me.dylanmullen.bingo.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class Client
{

	private DatagramSocket socket;
	private InetAddress ip;
	private int port;

	/**
	 * The client of the application. This is used to communicate with the server.
	 * 
	 * @param address IPv4 address of the server.
	 * @param port    The open port of the server.
	 */
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

	/**
	 * @return Returns the socket of the client for the server.
	 */
	public DatagramSocket getSocket()
	{
		return this.socket;
	}

	/**
	 * @return Returns the {@link InetAddress} of the server.
	 */
	public InetAddress getIP()
	{
		return this.ip;
	}

	/**
	 * @return Returns the open port of the server.
	 */
	public int getPort()
	{
		return this.port;
	}

}
