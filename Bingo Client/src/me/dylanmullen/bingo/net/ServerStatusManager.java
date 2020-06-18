package me.dylanmullen.bingo.net;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.ServerStatusChangeEvent;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class ServerStatusManager
{

	private static ServerStatus status = ServerStatus.UNDEFINED;

	public enum ServerStatus
	{
		UNDEFINED("Undefined"), CONNECTED("Connected"), DISCONNECTED("Disconnected");

		private String messsage;

		/**
		 * The status of the server.
		 * 
		 * @param statusMessage The message for the status.
		 */
		ServerStatus(String statusMessage)
		{
			this.messsage = statusMessage;
		}

		public String getMesssage()
		{
			return this.messsage;
		}
	}

	private static ServerStatusManager instance;

	/**
	 * Returns the Server Status Manager instance.
	 * 
	 * @return {@link #instance}
	 */
	public static ServerStatusManager getManager()
	{
		if (ServerStatusManager.instance == null)
			ServerStatusManager.instance = new ServerStatusManager();
		return ServerStatusManager.instance;
	}

	/**
	 * Sets the new status of the server and fires the
	 * {@link ServerStatusChangeEvent} event to all listeners listening for it.
	 * 
	 * @param status The new status of the server.
	 */
	public void setStatus(ServerStatus status)
	{
		ServerStatusManager.status = status;
		EventHandler.fireEvent(new ServerStatusChangeEvent(status));
	}

	/**
	 * @return Returns the current status of the server.
	 */
	public ServerStatus getStatus()
	{
		return status;
	}

}
