package me.dylanmullen.bingo.net.handlers;

import java.io.IOException;
import java.util.ArrayList;

import me.dylanmullen.bingo.net.Server;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.util.DebugUtils;

public class OutgoingHandler implements Runnable
{

	private Server server;

	private Thread thread;
	private boolean running;

	private ArrayList<Packet> queue;

	public OutgoingHandler(Server server)
	{
		this.server = server;
		queue = new ArrayList<Packet>();
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
		System.out.print("Packeting Outgoing on: " + thread.getId());
		while (running)
		{
			process();
		}
	}

	public void process()
	{
		synchronized (queue)
		{
			for (int i = 0; i < queue.size(); i++)
			{
				DebugUtils.sendThreadInformation(Thread.currentThread());
				sendPacket(queue.get(i));
				queue.remove(i);
			}
		}
	}
	
	public void submitPacket(Packet packet)
	{
		queue.add(packet);
	}

	private void sendPacket(Packet packet)
	{
		try
		{
			packet.setTime();
			server.getServer().send(packet.convert());
		}catch(IOException e)
		{
			System.err.println("Failed to send packet: "+e.getMessage());
		}
	}
	
	public void end()
	{
		this.running=false;
	}

}
