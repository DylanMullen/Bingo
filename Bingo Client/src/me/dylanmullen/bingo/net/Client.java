package me.dylanmullen.bingo.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import me.dylanmullen.bingo.net.packet.Packet;

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

	public synchronized void sendPacket(Packet p)
	{
		DatagramPacket packet = new DatagramPacket(p.getDataByte(), p.getDataByte().length, ip, port);
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
			e.printStackTrace();
		}
		return decodeData(packet.getData());
	}

	private String decodeData(byte[] data)
	{
		return new String(data);
	}

}
