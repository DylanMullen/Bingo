package me.dylanmullen.bingo.net.handlers;

import java.util.ArrayList;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.PacketTicket;

public class ClientHandler
{

	private Client client;
	private static ClientHandler handler;

	private ClientIncomingHandler income;
	private ClientOutgoingHandler outgoing;

	private ArrayList<PacketTicket> tickets = new ArrayList<>();

	public ClientHandler(String address, int port)
	{
		if (handler == null)
			handler = this;
		createClient(address, port);
		createHandlers();
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
			System.out.println("Adding");
			outgoing.addPacket(ticket.getPacketToSend());
			System.out.println("Added");
	}

	public void handleIncoming(UUID uuid, String decode)
	{
		PacketTicket ticket = getTicket(uuid);
		if (ticket != null && ticket.getCallback() != null)
		{
			ticket.getCallback().setData(decode);
			ticket.getCallback().callback();

			tickets.remove(ticket);
			return;
		}
		// PacketHandler;
	}

	private void createClient(String address, int port)
	{
		this.client = new Client(address, port);
	}

	public PacketTicket getTicket(UUID uuid)
	{
		if(uuid == null)
			return null;
		
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

}
