package me.dylanmullen.bingo.net.handlers;

import java.net.DatagramPacket;
import java.util.ArrayList;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class ClientOutgoingHandler implements Runnable
{

	private Thread thread;
	private boolean running;

	public ArrayList<Packet> queue;

	private Client client;

	/**
	 * This is the Client Outgoing Handler.<br>
	 * This handles any packets that the Client will be sending to the server.
	 * 
	 * @param client The Client.
	 */
	public ClientOutgoingHandler(Client client)
	{
		this.client = client;
		this.queue = new ArrayList<Packet>();
		start();
	}

	/**
	 * Starts a new Thread to handle any outgoing traffic as long as it is not
	 * already running.
	 */
	public synchronized void start()
	{
		if (this.running)
			return;
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run()
	{
		this.running = true;
		while (this.running)
		{
			process();
		}
	}

	/**
	 * Processes any tickets in the queue and sends the packet to the server.
	 */
	private void process()
	{
		synchronized (this.queue)
		{
			for (int i = 0; i < this.queue.size(); i++)
			{
				sendPacket(this.queue.get(i));
				this.queue.remove(i);
			}
		}
	}

	/**
	 * Sends a packet to the Bingo Server.
	 * 
	 * @param packet The packet to send.
	 */
	private void sendPacket(Packet packet)
	{
		try
		{
			packet.setTimestamp();
			DatagramPacket dp = packet.constructDatagramPacket(this.client, packet.getID() != 0);
			if (dp == null)
				return;
			this.client.getSocket().send(dp);
		} catch (Exception e)
		{
			System.out.println("Failed to send packet: " + e.getMessage());
		}
	}

	/**
	 * Queues any packet that needs to be sent to the server.
	 * 
	 * @param packet
	 */
	public void enqueue(Packet packet)
	{
		synchronized (this.queue)
		{
			this.queue.add(packet);
		}
	}

}
