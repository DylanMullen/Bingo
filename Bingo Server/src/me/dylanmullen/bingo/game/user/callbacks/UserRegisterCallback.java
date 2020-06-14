package me.dylanmullen.bingo.game.user.callbacks;

import java.sql.SQLException;
import java.util.UUID;

import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.mysql.SQLFactory;
import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;
import me.dylanmullen.bingo.mysql.sql_util.SQLTicket;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response.ResponseType;

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
			Packet_005_Response res = (Packet_005_Response) PacketHandler.createPacket(client, 005, "");
			if (result.next())
			{
				res.constructMessage(ResponseType.FAILURE, "Email already exists", packetToRelay);
				PacketHandler.sendPacket(res, null);
				return true;
			}

			UUID uuid = UUID.randomUUID();
			System.out.println(uuid.toString());
			System.out.println(uuid.toString().replace("-", ""));
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

}
