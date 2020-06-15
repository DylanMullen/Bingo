package me.dylanmullen.bingo.game;

import java.util.UUID;

import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SideMenu;

public class UserInformation
{

	private UUID uuid;
	private String displayName;
	private double credits;
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

	public double getCredits()
	{
		return credits;
	}

	public void setCredits(double credits)
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

	public void updateCredits(String data)
	{
		String credits = data.split("/m/|/m/")[1];
		setCredits(Double.parseDouble(credits));
		SideMenu.getInstance().getPanel().updateCredits(getCredits());
	}

}
