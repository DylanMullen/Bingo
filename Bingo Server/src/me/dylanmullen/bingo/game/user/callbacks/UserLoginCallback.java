package me.dylanmullen.bingo.game.user.callbacks;

import java.sql.SQLException;
import java.util.UUID;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response.ResponseType;

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
				Packet_005_Response res = (Packet_005_Response) PacketHandler.createPacket(client, 005, "");
				res.constructMessage(ResponseType.FAILURE, "Invalid Username/Password", packetToRelay);
				PacketHandler.sendPacket(res, null);
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
}
