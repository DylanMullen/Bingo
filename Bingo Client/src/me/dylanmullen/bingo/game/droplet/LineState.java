package me.dylanmullen.bingo.game.droplet;

public enum LineState
{
	ONE(0), TWO(1), FULLHOUSE(2);

	private int lines;

	/**
	 * The Line State of the current Bingo Game.
	 * 
	 * @param lines
	 */
	private LineState(int lines)
	{
		this.lines = lines;
	}

	/**
	 * Returns the lines of the Line State.
	 * 
	 * @return {@link #lines}
	 */
	public int getLines()
	{
		return this.lines;
	}

	/**
	 * Returns the LineState based on the lines.
	 * 
	 * @param lines Lines of the state you are looking for.
	 * @return LineState
	 */
	public static LineState get(int lines)
	{
		for (LineState lineState : values())
		{
			if (lineState.getLines() == lines)
				return lineState;
		}
		return null;
	}
}
