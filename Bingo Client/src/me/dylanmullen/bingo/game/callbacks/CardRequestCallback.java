package me.dylanmullen.bingo.game.callbacks;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.net.packet.PacketCallback;

public class CardRequestCallback extends PacketCallback
{

	@Override
	public boolean callback()
	{
		if(getResponseType()==200)
			BingoGame.getInstance().getGamePanel().getGameComponent().getCardGroup().setCardInformation(getNumbers());
		return false;
	}

	private String[] getNumbers()
	{
		return ((String) getMessage().get("cards")).split("/c/|/c/");
	}

}
