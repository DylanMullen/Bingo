package me.dylanmullen.bingo.events.events.droplet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.game.droplet.GameState;

public class DropletJoinEvent extends DropletEvent
{
	
	private GameState newState;

	public DropletJoinEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.newState = GameState.getGameState(((Number) message.get("gameState")).intValue());
	}

	public GameState getNewState()
	{
		return newState;
	}
}
