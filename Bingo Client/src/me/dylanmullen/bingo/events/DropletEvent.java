package me.dylanmullen.bingo.events;

import java.util.UUID;

public abstract class DropletEvent implements Event
{

	private UUID dropletUUID;
	
	public DropletEvent(UUID dropletUUID)
	{
		this.dropletUUID = dropletUUID;
	}
	
	public UUID getDropletUUID()
	{
		return dropletUUID;
	}
}
