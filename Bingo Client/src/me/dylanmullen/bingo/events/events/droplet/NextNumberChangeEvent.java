package me.dylanmullen.bingo.events.events.droplet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;

public class NextNumberChangeEvent extends DropletEvent
{

	private int numberToChange;

	public NextNumberChangeEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.numberToChange = ((Number) message.get("number")).intValue();
	}

	public int getNumberToChange()
	{
		return numberToChange;
	}

}
