package me.dylanmullen.bingo.game.user;

import java.util.UUID;

public class BUserInformation
{

	private UUID uuid;

	private String username;
	
	public BUserInformation(UUID uuid)
	{
		this.uuid = uuid;
		collectInfo();
	}
	
	private void collectInfo()
	{
		//TO-DO
		// MySQL fetch
	}

	public String getUsername()
	{
		return username;
	}
	
	public UUID getUUID()
	{
		return uuid;
	}
}
