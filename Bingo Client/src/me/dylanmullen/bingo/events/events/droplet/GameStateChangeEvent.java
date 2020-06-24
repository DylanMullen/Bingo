package me.dylanmullen.bingo.events.events.droplet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.game.droplet.GameState;

public class GameStateChangeEvent extends DropletEvent
{

	private GameState newState;

	public GameStateChangeEvent(UUID uuid, JSONObject message)
	{
		super(uuid);
		this.newState = GameState.getGameState(((Number) message.get("gameState")).intValue());
		System.out.println("gamestate change");
	}

	public GameState getNewState()
	{
		return newState;
	}

}
