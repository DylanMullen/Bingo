package me.dylanmullen.bingo.net.handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.EncryptionHandler;
import me.dylanmullen.bingo.net.Server;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

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

			JSONObject decode = decodeData(client, dp.getData());
			if (decode == null)
				return;

			if (client == null)
			{
				if (getID(decode) == 0)
				{
					handleNewClient(decode, dp.getAddress(), dp.getPort());
				}
				return;
			}

			ServerHandler.getHandler().handleIncoming(getPacketUUID(decode), client, decode);
		} catch (IOException e)
		{
			System.err.println("Error recieving a packet!");
		}
	}

	private JSONObject decodeData(Client client, byte[] data)
	{
		String packetData = new String(data).trim();
		if (client != null)
			packetData = EncryptionHandler.decrypt(client, packetData);

		JSONParser parser = new JSONParser();
		try
		{
			return (JSONObject) parser.parse(packetData);
		} catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private void handleNewClient(JSONObject data, InetAddress address, int port)
	{
		String publicKey = (String) ((JSONObject) data.get("packetMessage")).get("publicKey");
		Client client = server.createClient(publicKey, address, port);
		
		PacketHandler.sendPacket(constructReponsePacket(client, getPacketUUID(data)));
	}

	@SuppressWarnings("unchecked")
	private Packet constructReponsePacket(Client client, UUID packetRelay)
	{
		Packet packet = PacketHandler.createPacket(client, 5, null);
		JSONObject message = new JSONObject();
		message.put("responseType", 200);
		message.put("aesKey", client.getBase64AESKey());
		packet.setMessageSection(message);
		packet.setPacketUUID(packetRelay);
		return packet;
	}

	private int getID(JSONObject jsonData)
	{
		return ((Number) ((JSONObject) jsonData.get("packetInformation")).get("packetID")).intValue();
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
