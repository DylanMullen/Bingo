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
		game.sendPacket(10, game.getGameState());
		long then = 0;
		while (playing)
		{
			if (paused)
			{
				if (System.currentTimeMillis() - then >= 2000)
					paused = false;
				else
					continue;
			}

			int num = game.pickNumber();

			if (num == -1)
				playing = false;
			game.sendPacket(9, num + "");
			paused = true;
			then = System.currentTimeMillis();
		}
		System.out.println("Game finished");
	}

}
