package me.dylanmullen.bingo.game.user.callbacks;

import java.sql.SQLException;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class UserLoginCallback extends SQLCallback
{

	private Client client;
	private UUID packetToRelay;

	public UserLoginCallback(Client client, UUID packetUUID)
	{
		this.packetToRelay = packetUUID;
		this.client = client;
	}

	@Override
	public boolean callback()
	{
		sendResponse();
		return false;
	}

	private void sendResponse()
	{
		try
		{
			if (!result.isBeforeFirst())
			{
				Packet packet = PacketHandler.createPacket(client, 5, null);
				packet.setMessageSection(createInvalidMessage());
				packet.setPacketUUID(packetToRelay);
				PacketHandler.sendPacket(packet);
				return;
			}
			result.next();
			String temp = result.getString(1);
			temp = temp.replaceAll("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
			
			UUID uuid = UUID.fromString(temp);
			UserManager.getInstance().addUser(client, uuid);
			UserManager.getInstance().getUser(uuid).loadInformation(packetToRelay);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private JSONObject createInvalidMessage()
	{
		JSONObject message = new JSONObject();
		message.put("responseType", 500);
		message.put("errorMessage", "Invalid Username/Password");
		return message;
	}
}
