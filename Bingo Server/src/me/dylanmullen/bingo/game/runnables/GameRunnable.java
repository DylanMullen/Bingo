package me.dylanmullen.bingo.game.runnables;

import me.dylanmullen.bingo.game.BingoGame;

public class GameRunnable implements Runnable
{

	private BingoGame game;

	private boolean playing;
	private boolean paused;

	public GameRunnable(BingoGame game)
	{
		this.game = game;
		this.playing = true;
	}

	@Override
	public void run()
	{
		game.sendPacket(game.createGamestatePacket());
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
			game.sendPacket(game.createNextNumberPacket(num));

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
