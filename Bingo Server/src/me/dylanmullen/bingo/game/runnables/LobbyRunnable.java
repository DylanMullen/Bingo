package me.dylanmullen.bingo.game.runnables;

import me.dylanmullen.bingo.game.BingoGame;

public class LobbyRunnable implements Runnable
{

	private int COUNTER = 5;

	private BingoGame game;
	private boolean finished = false;
	private long start = -1;

	public LobbyRunnable(BingoGame game)
	{
		this.game = game;
	}

	@Override
	public void run()
	{
		game.getSettings().setupPot();
		while (!finished)
		{
			setStart();
			long now = System.currentTimeMillis();
			if (now - start >= COUNTER * 1000)
			{
				if (game.getPlayersPlayingSize() >= game.getSettings().getMinPlayers())
					finished = true;
				else
				{
					start = System.currentTimeMillis();
				}
			}
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
