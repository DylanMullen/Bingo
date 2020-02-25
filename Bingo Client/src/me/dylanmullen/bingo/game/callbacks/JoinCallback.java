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
		System.out.println(getData());
		BingoGame.getInstance().setGameJoined(true);
		return false;
	}

}
