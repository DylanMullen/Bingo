package me.dylanmullen.bingo.net;

import java.io.IOException;
import java.net.DatagramPacket;
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

	public void sendPacket(String mes)
	{
		DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, ip, port);
		try
		{
			socket.send(packet);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public String recieve()
	{
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		try
		{
			socket.receive(packet);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return decodeData(packet.getData());
	}
	
	private String decodeData(byte[] data)
	{
		return new String(data);
	}
	
}
