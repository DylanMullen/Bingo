package me.dylanmullen.bingo.net.handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class ClientIncomingHandler implements Runnable
{

	private Client client;

	private Thread thread;
	private boolean listening;

	/**
	 * This is the Client Incoming Handler.<br>
	 * This handles any packets being sent from the server to the client.
	 * 
	 * @param client The client.
	 */
	public ClientIncomingHandler(Client client)
	{
		this.client = client;
		start();
	}

	/**
	 * Starts the thread to listen for the incoming packets.
	 */
	public synchronized void start()
	{
		if (this.listening)
			return;
		this.thread = new Thread(this);
		this.thread.start();

	}

	@Override
	public void run()
	{
		this.listening = true;
		while (this.listening)
		{
			receive();
		}
	}

	/**
	 * Receives any packets with the maximum size of 1kb.<br>
	 * After the packet is received, if it is a response packet it will find the
	 * UUID. On both cases, the packet is then sent to be handled by the Client
	 * Hander.
	 */
	private void receive()
	{
		byte[] receive = new byte[1024];
		try
		{
			DatagramPacket dp = new DatagramPacket(receive, receive.length);
			this.client.getSocket().receive(dp);
			String decode = decodeData(dp.getData());
			int id = Integer.parseInt(decode.split(";")[0]);

			if (id == 5)
				ClientHandler.getInstance().handleIncoming(getUUID(decode), decode);
			else
				ClientHandler.getInstance().handleIncoming(null, decode);
		} catch (IOException e)
		{
			System.err.println("Error recieving a packet!");
		}
	}

	/**
	 * Returns the String of the bytes from the packet.
	 * 
	 * @param data The packet data.
	 * @return Returns the String of the bytes from the packet.
	 */
	private String decodeData(byte[] data)
	{
		return new String(data).trim();
	}

	/**
	 * Returns a UUID from the packet. If the UUID cannot be created, it will return
	 * null.
	 * 
	 * @param data Packet data.
	 * @return Returns either a {@link UUID} or null.
	 */
	private UUID getUUID(String data)
	{
		String[] j = data.split("/m/|/m/");
		try
		{
			return UUID.fromString(j[1].split("/nl/")[1]);
		} catch (IllegalArgumentException e)
		{
			return null;
		}
	}
}
