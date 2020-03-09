package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.callbacks.PurchaseCallback;
import me.dylanmullen.bingo.net.PacketHandler;

public class PurchaseListener implements MouseListener
{

	private BingoCard card;
	private boolean sent;

	public PurchaseListener(BingoCard card)
	{
		this.card = card;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (sent)
			return;
		PacketHandler.sendPacket(PacketHandler.createPacket(8, card.getUUID().toString()), new PurchaseCallback(card));
		sent = true;
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
