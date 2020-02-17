package me.dylanmullen.bingo.net.handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.Server;
import me.dylanmullen.bingo.util.DebugUtils;

public class IncomingHandler implements Runnable
{

	private Thread thread;
	private boolean listening;

	private Server server;

	public IncomingHandler(Server server)
	{
		this.server = server;
	}

	public synchronized void start()
	{
		if (listening)
			return;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run()
	{
		listening = true;
		System.out.println("Listening... @ Thread: " + thread.getId());
		while (listening)
		{
			recieve();
		}
	}

	private void recieve()
	{
		byte[] recieve = new byte[1024];
		try
		{
			DatagramPacket dp = new DatagramPacket(recieve, recieve.length);
			server.getServer().receive(dp);
			
			Client client = null;
			synchronized(server.getClients())
			{
				client = server.getClient(dp.getAddress(), dp.getPort());
			}
			
			if(client == null)
				return;
			
			String decode = decodeData(dp.getData());
			DebugUtils.sendThreadInformation(Thread.currentThread());
			ServerHandler.getHandler().handleIncoming(getPacketUUID(decode), client, decode);
		} catch (IOException e)
		{
			System.err.println("Error recieving a packet!");
		}
	}

	private String decodeData(byte[] data)
	{
		return new String(data);
	}
	
	private UUID getPacketUUID(String s)
	{
		return UUID.fromString(s.split(";")[2]);
	}
	
	public void end()
	{
		this.listening=false;
	}
}
