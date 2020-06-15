package me.dylanmullen.bingo.game.callbacks;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.net.packet.PacketCallback;

public class JoinCallback extends PacketCallback
{

	public JoinCallback()
	{
	}

	@Override
	public boolean callback()
	{
		String[] j = getMessage().split("/nl/");
		try
		{
			int id = Integer.parseInt(j[3]);
			BingoGame.getInstance().setGameJoined(true, id);
		} catch (IllegalArgumentException e)
		{
		}
		return false;
	}

}
