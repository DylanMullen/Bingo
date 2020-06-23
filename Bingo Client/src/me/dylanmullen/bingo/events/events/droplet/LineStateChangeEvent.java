package me.dylanmullen.bingo.events.events.droplet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.game.droplet.GameState;
import me.dylanmullen.bingo.game.droplet.LineState;

public class LineStateChangeEvent extends DropletEvent
{
	
	private LineState newState;

	public LineStateChangeEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.newState = LineState.get(((Number) message.get("gameState")).intValue());
	}
	
	public LineState getNewState()
	{
		return newState;
	}

}
