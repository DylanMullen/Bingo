package me.dylanmullen.bingo.game;

import java.util.UUID;

import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SideMenu;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class UserInformation
{

	private UUID uuid;
	private String displayName;
	private double credits;
	private int wins;
	private int loses;

	/**
	 * Updates the credits of the Player.<br>
	 * This is called every time the client purchases or wins a game and receives
	 * the packet from the server to update the credit count.
	 * 
	 * @param data The packet data to decode.
	 */
	public void updateCredits(String data)
	{
		String credits = data.split("/m/|/m/")[1];
		setCredits(Double.parseDouble(credits));
		SideMenu.getInstance().getPanel().updateCredits(getCredits());
	}

	/**
	 * Returns the UUID of the Player.
	 * 
	 * @return {@link #uuid}.
	 */
	public UUID getUUID()
	{
		return this.uuid;
	}

	/**
	 * Sets the UUID of the Player.
	 * 
	 * @param uuid The UUID of the Player.
	 */
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * Gets the Display Name of the Player.
	 * 
	 * @return {@link #displayName}
	 */
	public String getDisplayName()
	{
		return this.displayName;
	}

	/**
	 * Sets the Display Name of the Player.
	 * 
	 * @param displayName The Display Name of the Player.
	 */
	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	/**
	 * Returns the Credits of the Player.
	 * 
	 * @return {@link #credits}.
	 */
	public double getCredits()
	{
		return this.credits;
	}

	/**
	 * Sets the Credits of the Player.
	 * 
	 * @param credits The credits of the Player.
	 */
	public void setCredits(double credits)
	{
		this.credits = credits;
	}

	/**
	 * Returns the Wins of the Player.
	 * 
	 * @return {@link #wins}
	 */
	public int getWins()
	{
		return this.wins;
	}

	/**
	 * Sets the Wins of the Player.
	 * 
	 * @param wins The wins of the Player.
	 */
	public void setWins(int wins)
	{
		this.wins = wins;
	}

	/**
	 * Returns the Losses of the Player;
	 * 
	 * @return {@link #loses}
	 */
	public int getLosses()
	{
		return this.loses;
	}

	/**
	 * Sets the Losses of the Player.
	 * 
	 * @param loses The losses of the Player.
	 */
	public void setLoses(int loses)
	{
		this.loses = loses;
	}

}
