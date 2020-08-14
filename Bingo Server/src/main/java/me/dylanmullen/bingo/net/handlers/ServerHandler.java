package me.dylanmullen.bingo.net.handlers;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.Server;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.PacketTicket;

public class ServerHandler
{

	private Server server;

	private static ServerHandler handler;
	private IncomingHandler income;
	private OutgoingHandler outgoing;

	private PacketHandler packetHandler;

	private ArrayList<PacketTicket> tickets = new ArrayList<>();
	
	public ServerHandler(int port)
	{
		if (handler == null)
			handler = this;

		createServer(port);
	}

	public static ServerHandler getHandler()
	{
		return handler;
	}
	
	public boolean isSetup()
	{
		return server.isValid();
	}

	public void start()
	{
		createHandlers();
		income.start();
		outgoing.start();
	}
	
	private void createServer(int port)
	{
		this.server = new Server(port);
		System.out.println("Server Created");
	}

	private void createHandlers()
	{
		this.income = new IncomingHandler(server);
		this.outgoing = new OutgoingHandler(server);
		this.packetHandler = new PacketHandler();
	}

	public void submitTicket(PacketTicket ticket)
	{
		if (tickets.contains(ticket))
			return;
		if (ticket.getCallback() != null)
			tickets.add(ticket);

		handleOutgoing(ticket);
	}

	public void handleIncoming(UUID uuid, Client client, JSONObject data)
	{
		if (uuid != null)
		{
			PacketTicket ticket = getTicket(uuid);
			if (ticket != null)
			{
				if(ticket.getCallback()!=null)
					ticket.getCallback().callback();
				tickets.remove(ticket);
				return;
			}
		}
		packetHandler.handle(client, data);
	}

	public void handleOutgoing(PacketTicket ticket)
	{
		outgoing.submitPacket(ticket.getPacket());
	}

	public void dispose()
	{
		income.end();
		outgoing.end();
	}

	private PacketTicket getTicket(UUID uuid)
	{

		for (int i = 0; i < tickets.size(); i++)
		{
			PacketTicket ticket = tickets.get(i);
			if (ticket.getUUID().equals(uuid))
				return ticket;
		}
		return null;
	}

}
