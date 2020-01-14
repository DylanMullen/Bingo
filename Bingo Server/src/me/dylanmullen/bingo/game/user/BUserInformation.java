package me.dylanmullen.bingo.game.user;

import java.util.UUID;

import me.dylanmullen.bingo.core.BingoServer;

public class BUserInformation
{

	private UUID uuid;

	private String username;
	private double credit;
	private int wins, losses;
	
	public BUserInformation(UUID uuid)
	{
		this.uuid = uuid;
		collectInfo();
	}
	
	private void collectInfo()
	{
		BingoServer.getInstance().getMySQL().setPlayerInformation(this);
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setCredit(double credit)
	{
		this.credit = credit;
	}
	
	public void setWins(int wins)
	{
		this.wins = wins;
	}
	
	public void setLosses(int losses)
	{
		this.losses = losses;
	}
	
	public int getWins()
	{
		return wins;
	}
	
	public int getLosses()
	{
		return losses;
	}
	
	public double getCredit()
	{
		return credit;
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
