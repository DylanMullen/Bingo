package me.dylanmullen.bingo.net.packet;

import java.net.DatagramPacket;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.Client;

public abstract class Packet
{

	private int id;
	private Client client;
	private JSONObject data;

	private String encrypted;

	public Packet(int id, Client client, JSONObject object)
	{
		this.id = id;
		this.client = client;
		if (object != null)
			this.data = object;
		else
		{
			this.data = constructDefaultPacket();
		}
	}

	public abstract void handle();

	protected JSONObject constructDefaultPacket()
	{
		JSONObject information = new JSONObject();
		set(information, "packetID", this.id);
		set(information, "uuids", new JSONObject());

		JSONObject data = new JSONObject();
		set(data, "packetInformation", information);
		set(data, "packetMessage", new JSONObject());
		return data;
	}

	@SuppressWarnings("unchecked")
	public void setMessageSection(JSONObject object)
	{
		data.put("packetMessage", object);
	}

	public void setTimestamp()
	{
		set(getSection(data, "packetInformation"), "timestamp", System.currentTimeMillis());
	}

	public void setPacketUUID(UUID uuid)
	{
		if (getPacketUUID() == null)
			set(getUUIDSection(), "packetUUID", uuid.toString());
	}

	@SuppressWarnings("unchecked")
	private void set(JSONObject object, String key, Object value)
	{
		object.put(key, value);
	}

	protected JSONObject getMessageSection()
	{
		return (JSONObject) data.get("packetMessage");
	}

	protected JSONObject getUUIDSection()
	{
		return getSection(getSection(data, "packetInformation"), "uuids");
	}

	protected UUID getPacketUUID()
	{
		try
		{
			return UUID.fromString((String) getUUIDSection().get("packetUUID"));
		} catch (NullPointerException e)
		{
			return null;
		}
	}

	protected UUID getSenderUUID()
	{
		String uuid = (String) getUUIDSection().get("senderUUID");
		if (uuid == null)
			return null;
		return UUID.fromString((String) getUUIDSection().get("senderUUID"));
	}

	protected long getTimestamp()
	{
		return (long) getSection(data, "packetInformation").get("timestamp");
	}

	protected JSONObject getSection(JSONObject object, String key)
	{
		return (JSONObject) object.get(key);
	}

	public int getID()
	{
		return id;
	}

	public Client getSender()
	{
		return client;
	}

	public void setEncrypted(String data)
	{
		this.encrypted = data;
	}

	public DatagramPacket constructDatagramPacket()
	{
		return new DatagramPacket(encrypted.getBytes(), encrypted.getBytes().length, client.getAddress(),
				client.getPort());
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	@Override
	public String toString()
	{
		return data.toJSONString();
	}
}
