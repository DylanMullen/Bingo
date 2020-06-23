package me.dylanmullen.bingo.game;

import java.util.UUID;

import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.events.user.CurrencyChangeEvent;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class UserInformation implements EventListener
{

	private UUID uuid;
	private String displayName;
	private double credits;
	private int wins;

	public UserInformation()
	{
		EventHandler.getHandler().registerListener(UserInformationChangeEvent.class, this);
		EventHandler.getHandler().registerListener(CurrencyChangeEvent.class, this);
	}

	/**
	 * Updates the credits of the Player.<br>
	 * This is called every time the client purchases or wins a game and receives
	 * the packet from the server to update the credit count.
	 * 
	 * @param data The packet data to decode.
	 */
	public void updateCredits(double credits)
	{
		setCredits(credits);
	}

	public void updateInformation(UserInformationChangeEvent event)
	{
		setUUID(event.getUUID());
		setDisplayName(event.getUsername());
		setCredits(event.getCredits());
		setWins(event.getWins());
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

	@Override
	public void receive(Event event)
	{
		if (event instanceof UserInformationChangeEvent)
			updateInformation((UserInformationChangeEvent) event);
		else if (event instanceof CurrencyChangeEvent)
			updateCredits(((CurrencyChangeEvent) event).getCredits());
	}

}
