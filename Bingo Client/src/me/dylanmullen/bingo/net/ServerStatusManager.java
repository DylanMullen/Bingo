package me.dylanmullen.bingo.net;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.ServerStatusChangeEvent;

public class ServerStatusManager
{

	private static ServerStatus status = ServerStatus.UNDEFINED;

	public enum ServerStatus
	{
		UNDEFINED("Undefined"), CONNECTED("Connected"), DISCONNECTED("Disconnected");

		private String messsage;

		ServerStatus(String statusMessage)
		{
			this.messsage = statusMessage;
		}

		public String getMesssage()
		{
			return messsage;
		}
	}

	private static ServerStatusManager instance;

	public static ServerStatusManager getManager()
	{
		if (instance == null)
			instance = new ServerStatusManager();
		return instance;
	}

	public void setStatus(ServerStatus status)
	{
		ServerStatusManager.status = status;
		EventHandler.fireEvent(new ServerStatusChangeEvent(status));
	}

	public ServerStatus getStatus()
	{
		return status;
	}

}
