package me.dylanmullen.bingo.game.callbacks;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.net.packet.PacketCallback;

public class CardRequestCallback extends PacketCallback
{

	@Override
	public boolean callback()
	{
		BingoGame.getInstance().getGamePanel().getGameComponent().getCardGroup().setCardInformation(getNumbers());
		return false;
	}

	private String[] getNumbers()
	{
		return getMessage().split("/nl/")[2].split("/c/|/c/");
	}

}
