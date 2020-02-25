package me.dylanmullen.bingo.net.handlers;

import java.util.ArrayList;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.PacketTicket;
import me.dylanmullen.bingo.net.runnables.PingTask;

public class ClientHandler
{

	private Client client;
	private static ClientHandler handler;

	private ClientIncomingHandler income;
	private ClientOutgoingHandler outgoing;
	
	private PingTask pingTask;

	private boolean connected;
	
	private ArrayList<PacketTicket> tickets = new ArrayList<>();

	public ClientHandler(String address, int port)
	{
		if (handler == null)
			handler = this;
		createClient(address, port);
		createHandlers();
		createRunnables();
	}

	private void createRunnables()
	{
//		pingTask = new PingTask(2);
//		pingTask.start();
	}

	private void createHandlers()
	{
		this.income = new ClientIncomingHandler(getClient());
		this.outgoing = new ClientOutgoingHandler(client);

	}

	public static ClientHandler getInstance()
	{
		if (handler != null)
			return handler;
		return null;
	}

	public void submitTicket(PacketTicket ticket)
	{
		if (tickets.contains(ticket))
			return;
		tickets.add(ticket);
		handleOutgoing(ticket);
	}

	private void handleOutgoing(PacketTicket ticket)
	{
		outgoing.addPacket(ticket.getPacketToSend());
	}

	public void handleIncoming(UUID uuid, String decode)
	{
		PacketTicket ticket = getTicket(uuid);
		if (ticket != null && ticket.getCallback() != null)
		{
			ticket.getCallback().setData(decode);
			ticket.getCallback().callback();
			System.out.println("recied and callbacked");
			tickets.remove(ticket);
			return;
		}
	}

	private void createClient(String address, int port)
	{
		this.client = new Client(address, port);
	}

	public PacketTicket getTicket(UUID uuid)
	{
		if (uuid == null)
			return null;

		System.out.println(uuid.toString());

		for (int i = 0; i < tickets.size(); i++)
		{
			PacketTicket ticket = tickets.get(i);
			if (ticket.getUUID().equals(uuid))
				return ticket;
		}
		return null;
	}

	public Client getClient()
	{
		return client;
	}

	
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	public boolean isConnected()
	{
		return connected;
	}
}
