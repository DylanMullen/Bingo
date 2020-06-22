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
		int state = ((Number)getMessage().get("gameState")).intValue();
		
		BingoGame.getInstance().setGameJoined(true, state);
		return false;
	}

}
