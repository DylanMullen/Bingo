package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.callbacks.PurchaseCallback;
import me.dylanmullen.bingo.net.PacketHandler;

public class PurchaseListener extends MouseAdapter
{

	private long SENT_DELAY = 2500;

	private BingoCard card;
	private long lastSent;

	public PurchaseListener(BingoCard card)
	{
		this.card = card;
		System.err.println("CARD IS NULL");
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (System.currentTimeMillis() - lastSent <= SENT_DELAY)
			return;
		if (card == null)
			return;
		PacketHandler.sendPacket(PacketHandler.createPacket(8, card.getUUID().toString()), new PurchaseCallback(card));
		lastSent = System.currentTimeMillis();
	}
}
