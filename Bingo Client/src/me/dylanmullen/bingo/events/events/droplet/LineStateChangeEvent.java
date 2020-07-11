package me.dylanmullen.bingo.events.events.droplet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.game.droplet.LineState;

public class LineStateChangeEvent extends DropletEvent
{

	private LineState newState;
	private double prize;

	public LineStateChangeEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.newState = LineState.get(((Number) message.get("lineState")).intValue());
		this.prize = ((Number) message.get("prize")).doubleValue();
	}

	public LineState getNewState()
	{
		return newState;
	}
	
	public double getPrize()
	{
		return prize;
	}

}
