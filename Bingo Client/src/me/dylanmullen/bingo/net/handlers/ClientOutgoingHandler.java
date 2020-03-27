package me.dylanmullen.bingo.net.handlers;

import java.util.ArrayList;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class ClientOutgoingHandler implements Runnable
{

	private Thread thread;
	private boolean running;

	public ArrayList<Packet> queue;

	private Client client;

	public ClientOutgoingHandler(Client c)
	{
		this.client = c;
		queue = new ArrayList<Packet>();
		start();
	}

	public synchronized void start()
	{
		if (running)
			return;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run()
	{
		running = true;
		while (running)
		{
			process();
		}
	}

	private void process()
	{
		synchronized (queue)
		{
			for (int i = 0; i < queue.size(); i++)
			{
				sendPacket(queue.get(i));
				queue.remove(i);
			}
		}
	}

	private void sendPacket(Packet packet)
	{
		try
		{
			packet.setTime();
			client.getSocket().send(packet.convert(client));
		} catch (Exception e)
		{
//			System.out.println("Failed to send packet: " + e.);
			e.printStackTrace();
		}
	}

	public void addPacket(Packet packet)
	{
		synchronized(queue)
		{
			queue.add(packet);
		}
	}

}
