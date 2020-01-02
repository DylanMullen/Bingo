package me.dylanmullen.bingo.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class Server
{

	private DatagramSocket socket;
	private int port;

	private Thread thread;
	private boolean listening;

	private PacketHandler handler;

	public Server(int port)
	{
		this.port = port;
	}

	private void init()
	{
		try
		{
			this.socket = new DatagramSocket(port);
			this.handler = new PacketHandler(socket);
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}

		byte[] recieve = new byte[1024];

		thread = new Thread(() ->
		{
			while (listening)
			{
				DatagramPacket packet = new DatagramPacket(recieve, recieve.length);
				try
				{
					socket.receive(packet);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				System.out.println("Address:" + packet.getAddress() + ":" + packet.getPort());
				handler.handle(packet);
			}
		});
		this.listening = true;
	}

	public void send(Client c, Packet packet)
	{
		handler.sendPacket(c, packet);
	}

	public void send(InetAddress address, int port, Packet packet)
	{

	}

	public synchronized void start()
	{
		if (listening)
			return;

		init();
		thread.start();
	}

	public synchronized void stop()
	{
		if (!listening)
			return;

		try
		{
			listening = false;
			thread.join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
