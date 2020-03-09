package me.dylanmullen.bingo.game.runnables;

import me.dylanmullen.bingo.game.BingoGame;

public class LobbyRunnable implements Runnable
{

	private final int COUNTER = 5;

	private BingoGame game;
	private boolean finished;
	private long start = -1;

	public LobbyRunnable(BingoGame game)
	{
		this.game = game;
	}

	@Override
	public void run()
	{
		while (!finished)
		{
			setStart();
			long now = System.currentTimeMillis();
			if (now - start >= COUNTER * 1000)
				finished = true;
		}
		game.sendUserCards();
	}

	public void setStart()
	{
		if (start == -1)
			start = System.currentTimeMillis();
	}

	public boolean isFinished()
	{
		return finished;
	}

}
