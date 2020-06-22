package me.dylanmullen.bingo.game.runnables;

import me.dylanmullen.bingo.game.droplet.BingoDroplet;
import me.dylanmullen.bingo.game.droplet.BingoGame;

public class GameRunnable implements Runnable
{

	private BingoDroplet game;

	private boolean playing;
	private boolean paused;

	public GameRunnable(BingoDroplet game)
	{
		this.game = game;
		this.playing = true;
	}

	@Override
	public void run()
	{
		long then = 0;
		while (playing)
		{
			if (paused)
			{
				int delay = 500;

				if (System.currentTimeMillis() - then >= delay)
				{
					if (game.shouldRestart())
					{
						playing = false;
						continue;
					}
					paused = false;
				}
				else
				{
					continue;
				}
			}

			int num = game.pickNumber();
			if (num == -1)
			{
				playing = false;
				continue;
			}
			game.sendNextNumber(num);

			paused = true;
			if (game.checkWinners())
			{
				game.handleWinning();
			}
			then = System.currentTimeMillis();
		}
		System.out.println("Game finished");
	}

}
