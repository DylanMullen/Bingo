package me.dylanmullen.bingo.events.events.droplet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.Event;

public class DropletsRetrievedEvent implements Event
{

	// TODO change this.
	private Map<UUID, Integer> droplets;

	public DropletsRetrievedEvent(JSONObject object)
	{
		this.droplets = new HashMap<UUID, Integer>();

		for (Object obj : object.keySet())
		{
			UUID dropletUUID = UUID.fromString((String) obj);
			droplets.put(dropletUUID, ((Number) object.get(obj)).intValue());
		}
	}

	public Map<UUID, Integer> getDroplets()
	{
		return droplets;
	}

}
