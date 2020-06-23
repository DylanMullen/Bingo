package me.dylanmullen.bingo.events.events.droplet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;

public class CurrencyChangeEvent extends DropletEvent
{

	private double credits;

	public CurrencyChangeEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.credits = ((Number) message.get("credits")).doubleValue();
	}

	public double getCredits()
	{
		return credits;
	}

}
