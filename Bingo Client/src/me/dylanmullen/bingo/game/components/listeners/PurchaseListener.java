package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.callbacks.PurchaseCallback;
import me.dylanmullen.bingo.net.PacketHandler;

public class PurchaseListener implements MouseListener
{

	private long SENT_DELAY = 2500;

	private BingoCard card;
	private long lastSent;

	public PurchaseListener(BingoCard card)
	{
		this.card = card;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (System.currentTimeMillis() - lastSent <= SENT_DELAY)
			return;
		PacketHandler.sendPacket(PacketHandler.createPacket(8, card.getUUID().toString()), new PurchaseCallback(card));
		lastSent = System.currentTimeMillis();
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
