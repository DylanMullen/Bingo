package me.dylanmullen.bingo.net.handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.Server;

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
			synchronized (server.getClients())
			{
				client = server.getClient(dp.getAddress(), dp.getPort());
			}

			if (client == null)
				return;

			JSONObject decode = decodeData(dp.getData());
			if (decode == null)
				return;
			
			ServerHandler.getHandler().handleIncoming(getPacketUUID(decode), client, decode);
		} catch (IOException e)
		{
			System.err.println("Error recieving a packet!");
		}
	}

	private JSONObject decodeData(byte[] data)
	{
		String jsonString = new String(data).trim();
		JSONParser parser = new JSONParser();
		try
		{
			return (JSONObject) parser.parse(jsonString);
		} catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}

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

	public void end()
	{
		this.listening = false;
	}
}
