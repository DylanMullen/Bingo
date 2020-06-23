package me.dylanmullen.bingo.game.droplet;

public enum GameState
{
	LOBBY(0), PLAYING(1), ENDING(2);

	private int stateValue;

	/**
	 * The Game State of the Bingo Game.
	 * 
	 * @param stateValue The State Value.
	 */
	private GameState(int stateValue)
	{
		this.stateValue = stateValue;
	}

	/**
	 * Returns the state value of the Game State.
	 * 
	 * @return {@link #stateValue}
	 */
	public int getStateValue()
	{
		return this.stateValue;
	}

	/**
	 * Returns a Game State based on the state value.
	 * 
	 * @param stateValue State value to check for.
	 * @return Returns a Game State. If it does not exist, returns null.
	 */
	public static GameState getGameState(int stateValue)
	{
		for (GameState states : values())
			if (states.getStateValue() == stateValue)
				return states;
		return null;
	}

}
