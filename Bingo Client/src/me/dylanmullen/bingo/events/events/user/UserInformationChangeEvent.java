package me.dylanmullen.bingo.events.events.user;

import java.util.UUID;

import me.dylanmullen.bingo.events.Event;

public class UserInformationChangeEvent implements Event
{

	private UUID uuid;
	private String username;
	private double credits;
	private int wins;

	public UserInformationChangeEvent(UUID uuid, String username, double credits, int wins)
	{
		this.uuid = uuid;
		this.username = username;
		this.credits = credits;
		this.wins = wins;
	}
	
	public UUID getUUID()
	{
		return uuid;
	}

	public String getUsername()
	{
		return username;
	}

	public double getCredits()
	{
		return credits;
	}

	public int getWins()
	{
		return wins;
	}
}
