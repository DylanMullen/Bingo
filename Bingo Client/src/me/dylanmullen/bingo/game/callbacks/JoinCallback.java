package me.dylanmullen.bingo.game.callbacks;

import java.util.UUID;

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
		UUID uuid = UUID.fromString(j[2]);
		int id = Integer.parseInt(j[3]);
		BingoGame.getInstance().setGameJoined(true, id);
		return false;
	}

}
