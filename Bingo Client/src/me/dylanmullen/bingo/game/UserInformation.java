package me.dylanmullen.bingo.game;

import java.util.UUID;

import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SP_Bingo;

public class UserInformation
{

	private UUID uuid;
	private String displayName;
	private int credits;
	private int wins;
	private int loses;
	
	public UUID getUuid()
	{
		return uuid;
	}
	
	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
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
