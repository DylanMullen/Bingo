package me.dylanmullen.bingo.net.packet;

import java.net.DatagramPacket;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.handlers.ClientHandler;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class Packet
{

	private int id;
	private JSONObject packet;

	public Packet(int packetID)
	{
		this.id = packetID;
		this.packet = new JSONObject();
		constructPacketInformation(packetID);
		addMainSection("packetMessage");
	}

	public void setMessage(JSONObject object)
	{
		set(getPacketMessage(), "packetMessage", object);
	}

	public void setTimestamp()
	{
		set((JSONObject) packet.get("packetInformation"), "timestamp", System.currentTimeMillis());
	}

	public void setPacketUUID(UUID uuid)
	{
		set((JSONObject) ((JSONObject) getPacketMessage().get("packetInformation")).get("uuids"), "packetUUID",
				uuid.toString());
	}

	public void setUserUUID(UUID uuid)
	{
		set((JSONObject) ((JSONObject) getPacketMessage().get("packetInformation")).get("uuids"), "senderUUID",
				uuid.toString());
	}

	private void constructPacketInformation(int packetID)
	{
		JSONObject packetInformation = addMainSection("packetInformation");
		addSection(packetInformation, "uuids");
		set(packetInformation, "packetID", packetID);
	}

	public DatagramPacket constructDatagramPacket(Client client, boolean encrypt)
	{
		String data = toString();

		System.out.println(data + ":" + getID());
		if (encrypt)
			data = ClientHandler.getInstance().getEncryption().encrypt(data);
		if (data == null)
			return null;
		
		return new DatagramPacket(data.getBytes(), data.getBytes().length, client.getIP(), client.getPort());
	}

	private JSONObject addMainSection(String key)
	{
		return addSection(getPacketMessage(), key);
	}

	@SuppressWarnings("unchecked")
	private void set(JSONObject object, String key, Object value)
	{
		object.put(key, value);
	}

	@SuppressWarnings("unchecked")
	private JSONObject addSection(JSONObject object, String key)
	{
		object.put(key, new JSONObject());
		return (JSONObject) object.get(key);
	}

	public JSONObject getPacketMessage()
	{
		return packet;
	}

	@Override
	public String toString()
	{
		return packet.toJSONString();
	}

	public int getID()
	{
		return id;
	}

}
