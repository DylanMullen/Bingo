package me.dylanmullen.bingo.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server
{

	private DatagramSocket server;

	private ArrayList<Client> clientsConnected = new ArrayList<>();
	private boolean valid = true;

	public Server(int port)
	{
		try
		{
			this.server = new DatagramSocket(port);
		} catch (SocketException e)
		{
			System.err.println("Failed to initialize server: " + e.getMessage());
			valid = false;
		}
	}

	public void addClient(Client client)
	{
		if (clientsConnected.contains(client))
			return;
		clientsConnected.add(client);
	}

	public void removeClient(Client client)
	{
		if (!clientsConnected.contains(client))
			return;
		clientsConnected.remove(client);
	}

	public Client getClient(InetAddress address, int port)
	{
		for (Client c : clientsConnected)
		{
			if (c.getAddress().equals(address) && c.getPort() == port)
				return c;
		}

		return null;
	}

	public Client createClient(String RSA, InetAddress address, int port)
	{
		Client client = new Client(RSA, address, port);
		addClient(client);
		return client;
	}

	public DatagramSocket getServer()
	{
		return server;
	}

	public ArrayList<Client> getClients()
	{
		return clientsConnected;
	}

	public boolean isValid()
	{
		return valid;
	}
}
