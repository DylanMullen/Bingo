package me.dylanmullen.bingo.events.events;

import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.net.ServerStatusManager.ServerStatus;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class ServerStatusChangeEvent implements Event
{

	private ServerStatus serverStatus;

	/**
	 * Event fired when the server status is changed from the currently cached
	 * figure.
	 * 
	 * @param status The new status.
	 */
	public ServerStatusChangeEvent(ServerStatus status)
	{
		this.serverStatus = status;
	}

	/**
	 * Returns the server status that was updated.
	 * @return serverStatus The updated server status.
	 */
	public ServerStatus getStatus()
	{
		return this.serverStatus;
	}
}
