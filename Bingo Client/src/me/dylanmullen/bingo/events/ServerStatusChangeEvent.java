package me.dylanmullen.bingo.events;

import me.dylanmullen.bingo.net.ServerStatusManager.ServerStatus;

public class ServerStatusChangeEvent extends Event
{

	private ServerStatus status;
	
	public ServerStatusChangeEvent(ServerStatus status)
	{
		this.status=status;
	}
	
	public ServerStatus getStatus()
	{
		return status;
	}
}
