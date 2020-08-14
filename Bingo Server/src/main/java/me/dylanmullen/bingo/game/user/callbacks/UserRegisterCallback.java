package me.dylanmullen.bingo.game.user.callbacks;

import java.sql.SQLException;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.mysql.SQLFactory;
import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;
import me.dylanmullen.bingo.mysql.sql_util.SQLTicket;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class UserRegisterCallback extends SQLCallback
{

	private Client client;
	private UUID packetToRelay;

	private String username;
	private String password;

	public UserRegisterCallback(Client client, UUID packetUUID, String username, String password)
	{
		this.packetToRelay = packetUUID;
		this.client = client;
		this.username = username;
		this.password = password;
	}

	@Override
	public boolean callback()
	{
		try
		{
			if (result.next())
			{
				Packet packet = PacketHandler.createPacket(client, 005, null);
				packet.setPacketUUID(packetToRelay);
				packet.setMessageSection(createInvalidMessage());
				
				PacketHandler.sendPacket(packet);
				return true;
			}

			UUID uuid = UUID.randomUUID();
			SQLTicket ticket = SQLFactory.insertData(SQLFactory.getController().getDatabase().getLoginTableName(),
					new String[] { "uuid", "email", "password" }, new String[] { uuid.toString().replace("-", ""), username, password },
					null);
			SQLFactory.sendTicket(ticket);

			UserManager.getInstance().addUser(client, uuid);
			UserManager.getInstance().getUser(uuid).createUserInformation();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return false;
	}

	@SuppressWarnings("unchecked")
	private JSONObject createInvalidMessage()
	{
		JSONObject message = new JSONObject();
		message.put("responseType", 500);
		message.put("errorMessage", "Email already exists");
		return message;
	}
	
}
