package me.dylanmullen.bingo.events.events.user;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.Event;;

public class CurrencyChangeEvent implements Event
{

	private double credits;

	public CurrencyChangeEvent(JSONObject message)
	{
		this.credits = ((Number) message.get("credits")).doubleValue();
	}

	public double getCredits()
	{
		return credits;
	}

}
