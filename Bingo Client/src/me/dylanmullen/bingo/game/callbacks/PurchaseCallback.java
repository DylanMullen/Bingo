package me.dylanmullen.bingo.game.callbacks;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.net.packet.PacketCallback;

public class PurchaseCallback extends PacketCallback
{

	private BingoCard card;

	public PurchaseCallback(BingoCard card)
	{
		this.card = card;
	}

	@Override
	public boolean callback()
	{
		card.setPurchased(true);
		card.repaint();
		return false;
	}

}
