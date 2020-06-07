package me.dylanmullen.bingo.game.user;

import java.sql.SQLException;
import java.util.UUID;

import me.dylanmullen.bingo.mysql.SQLFactory;
import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;
import me.dylanmullen.bingo.mysql.sql_util.SQLTicket;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response;
import me.dylanmullen.bingo.net.packet.packets.Packet_Generic;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response.ResponseType;

public class UserInformation
{

	private UUID uuid;
	private String displayName;
	private int credits;
	private int wins;
	private int loses;

	/**
	 * Contains all the information on a player
	 * 
	 * @param uuid UUID of the player
	 */
	public UserInformation(UUID uuid)
	{
		this.uuid = uuid;
	}

	public void load(Client client, UUID packetUUID)
	{
		System.out.println(uuid.toString().replace("-", ""));
		SQLTicket ticket = SQLFactory.selectData("b_userinfo", "*", new String[] { "uuid" },
				new String[] { uuid.toString().replace("-", "") }, new SQLCallback()
				{
					@Override
					public boolean callback()
					{
						try
						{
							result.next();
							setDisplayName(result.getString("display_name"));
							setCredits(result.getInt("credit"));
							setWins(result.getInt("wins"));
							setLoses(result.getInt("losses"));

							if (packetUUID != null)
								sendLoginPackets(client, packetUUID);
						} catch (SQLException e)
						{
							System.err.println("ERROR: (UUID=" + uuid.toString() + ") FAILED TO LOAD INFORMATION");
							e.printStackTrace();
						}
						return false;
					}
				});
		SQLFactory.sendTicket(ticket);
	}

	private void sendLoginPackets(Client client, UUID packetUUID)
	{
		Packet_005_Response res = (Packet_005_Response) PacketHandler.createPacket(client, 005, "");
		String mes = uuid.toString() + "/nl/" + getDisplayName() + "/nl/" + getCredits() + "/nl/" + getWins()
				+ "/nl/" + getLoses();
		res.constructMessage(ResponseType.SUCCESS, mes, packetUUID);
		PacketHandler.sendPacket(res, null);
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public int getCredits()
	{
		return credits;
	}

	public void setCredits(int credits)
	{
		this.credits = credits;
	}

	public int getWins()
	{
		return wins;
	}

	public void setWins(int wins)
	{
		this.wins = wins;
	}

	public int getLoses()
	{
		return loses;
	}

	public void setLoses(int loses)
	{
		this.loses = loses;
	}

	public UUID getUUID()
	{
		return uuid;
	}

}
