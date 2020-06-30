package me.dylanmullen.bingo.net.handlers;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.PacketTicket;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.net.runnables.PingTask;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class ClientHandler
{

	private Client client;
	private static ClientHandler handler;

	private ClientIncomingHandler incoming;
	private ClientOutgoingHandler outgoing;
	private EncryptionHandler encryption;

	private PingTask pingTask;

	private boolean connected;

	private ArrayList<PacketTicket> tickets = new ArrayList<>();

	/**
	 * This is the Client Handler which handles anything related to networking for
	 * the Client to communicate with the Bingo Server.<br>
	 * The Client Handler will open up two new threads to handle incoming and
	 * outgoing traffic between the client and server.<br>
	 * The Client Handler will also open up a ping task which will ping the server
	 * every 5 seconds to ensure that the server is still up.
	 * 
	 * @param address IPv4 Address of the Bingo Server.
	 * @param port    The Port that the Bingo Server is open on.
	 */
	public ClientHandler(String address, int port)
	{
		if (ClientHandler.handler == null)
			ClientHandler.handler = this;

		createClient(address, port);
		createHandlers();
		sendIdentityPacket();
		createRunnables();
	}

	/**
	 * Creates any runnables that the Client Handler needs to operate.
	 */
	private void createRunnables()
	{
		this.pingTask = new PingTask(5);
		this.pingTask.start();
	}

	/**
	 * Creates the incoming and outgoing handlers.
	 */
	private void createHandlers()
	{
		this.incoming = new ClientIncomingHandler(getClient());
		this.outgoing = new ClientOutgoingHandler(client);
		this.encryption = new EncryptionHandler();
	}

	@SuppressWarnings("unchecked")
	private void sendIdentityPacket()
	{
		Packet packet = new Packet(0);
		JSONObject message = new JSONObject();
		message.put("publicKey", encryption.getBase64PublicKey());
		packet.setMessage(message);
		submitTicket(new PacketTicket(packet, new PacketCallback()
		{

			@Override
			public boolean callback()
			{
				encryption.setAesKey((String) getMessage().get("aesKey"));
				getPingTask().forcePing();
				return false;
			}
		}));
	}

	/**
	 * Returns the Client Handler instance.
	 * 
	 * @return {@link #handler}
	 */
	public static ClientHandler getInstance()
	{
		if (ClientHandler.handler != null)
			return ClientHandler.handler;
		return null;
	}

	/**
	 * Submits a new ticket to the Client Handler. This will then be pushed to the
	 * outgoing handler to send the packet to the server.
	 * 
	 * @param ticket The packet ticket to send to the server.
	 */
	public void submitTicket(PacketTicket ticket)
	{
		if (this.tickets.contains(ticket))
			return;
		this.tickets.add(ticket);
		handleOutgoing(ticket);
	}

	/**
	 * Sends the packet ticket to the outgoing handler.
	 * 
	 * @param ticket The packet ticket to send to the server.
	 */
	private void handleOutgoing(PacketTicket ticket)
	{
		this.outgoing.enqueue(ticket.getPacketToSend());
	}

	/**
	 * Handles an incoming packet.<br>
	 * This will check if the UUID submitted is a UUID of a submitted ticket and
	 * call the callback of that ticket. If this is not the case or is null, the
	 * packet will be sent to the Packet Handler.
	 * 
	 * @param uuid The UUID of an incoming response packet.
	 * @param data The data of the incoming packet.
	 */
	public void handleIncoming(int id, UUID uuid, JSONObject data)
	{
		if (uuid != null && id == 5)
		{
			PacketTicket ticket = getTicket(uuid);
			if (ticket != null && ticket.getCallback() != null)
			{
				ticket.getCallback().setData(data);
				ticket.getCallback().callback();
				this.tickets.remove(ticket);
				return;
			}
		}
		PacketHandler.handlePacket(id, data);
	}

	/**
	 * Creates a new Client object.
	 * 
	 * @param address IPv4 address of the server.
	 * @param port    The open port of the server.
	 */
	private void createClient(String address, int port)
	{
		this.client = new Client(address, port);
	}

	/**
	 * Returns a ticket based on the UUID submitted. <br>
	 * If this UUID is null or cannot be found, it will return null.
	 * 
	 * @param uuid UUID to search for.
	 * @return Returns a {@link PacketTicket} or null.
	 */
	public PacketTicket getTicket(UUID uuid)
	{
		if (uuid == null)
			return null;

		for (int i = 0; i < this.tickets.size(); i++)
		{
			PacketTicket ticket = this.tickets.get(i);
			if (ticket.getUUID().equals(uuid))
				return ticket;
		}
		return null;
	}

	/**
	 * Returns the Client.
	 * 
	 * @return {@link #client}
	 */
	public Client getClient()
	{
		return this.client;
	}

	/**
	 * Sets the connection as either connected or disconnected.
	 * 
	 * @param connected Connection value.
	 */
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}

	/**
	 * Returns if the Client is connected to the server.
	 * 
	 * @return {@link #connected}
	 */
	public boolean isConnected()
	{
		return this.connected;
	}

	/**
	 * @return Returns the incoming handler of the client.
	 */
	public ClientIncomingHandler getIncomingHandler()
	{
		return this.incoming;
	}

	/**
	 * @return Returns the outgoing handler of the client.
	 */
	public ClientOutgoingHandler getOutgoingHandler()
	{
		return outgoing;
	}

	/**
	 * @return Returns the ping task by the client.
	 */
	public PingTask getPingTask()
	{
		return pingTask;
	}

	public EncryptionHandler getEncryption()
	{
		return this.encryption;
	}
}
