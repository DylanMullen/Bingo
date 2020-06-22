package me.dylanmullen.bingo.net.handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

			JSONObject decode = decodeData(dp.getData());
			
			if (decode == null)
				return;
			int id = getID(decode);

			ClientHandler.getInstance().handleIncoming(id, getPacketUUID(decode), decode);
		} catch (IOException e)
		{
			System.err.println("Error recieving a packet!");
		}
	}

	/**
	 * Returns the String of the bytes from the packet.
	 * 
	 * @param data The packet data.
	 * @return Returns the JSONObject of the bytes from the packet.
	 */
	private JSONObject decodeData(byte[] data)
	{
		String jsonString = new String(data).trim();

		jsonString = ClientHandler.getInstance().getEncryption().decyrptAES(jsonString);
		if (jsonString == null)
			jsonString = ClientHandler.getInstance().getEncryption().decyrptRSA(new String(data).trim());

		JSONParser parser = new JSONParser();
		System.out.println(jsonString);
		try
		{
			return (JSONObject) parser.parse(jsonString);
		} catch (ParseException e)
		{
			return null;
		}
	}

	/**
	 * Returns a UUID from the packet. If the UUID cannot be created, it will return
	 * null.
	 * 
	 * @param data Packet data.
	 * @return Returns either a {@link UUID} or null.
	 */
	private UUID getPacketUUID(JSONObject object)
	{
		try
		{
			JSONObject uuids = (JSONObject) (((JSONObject) object.get("packetInformation")).get("uuids"));
			return UUID.fromString((String) uuids.get("packetUUID"));
		} catch (NullPointerException | IllegalArgumentException e)
		{
			return null;
		}
	}

	private int getID(JSONObject object)
	{
		JSONObject packetInformation = (JSONObject) object.get("packetInformation");
		return ((Number) packetInformation.get("packetID")).intValue();
	}

}
