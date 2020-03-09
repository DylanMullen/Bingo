package me.dylanmullen.bingo.net.handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

public class ClientIncomingHandler implements Runnable
{

	private Client client;

	private Thread thread;
	private boolean listening;

	public ClientIncomingHandler(Client client)
	{
		this.client = client;
		start();

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
			client.getSocket().receive(dp);
			String decode = decodeData(dp.getData());
			int id = Integer.parseInt(decode.split(";")[0]);
			
			if(id == 5)
				ClientHandler.getInstance().handleIncoming(getUUID(decode), decode);
			else
				ClientHandler.getInstance().handleIncoming(null, decode);
		} catch (IOException e)
		{
			System.err.println("Error recieving a packet!");
		}
	}

	private String decodeData(byte[] data)
	{
		return new String(data).trim();
	}

	private UUID getUUID(String s)
	{
		String[] j = s.split("/m/|/m/");
		return UUID.fromString(j[1].split("/nl/")[1]);
	}
}
