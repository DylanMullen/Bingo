package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.CardGroupComp;

public class BingoCardListener implements MouseListener
{

	private CardGroupComp group;

	public BingoCardListener(CardGroupComp group)
	{
		this.group = group;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		BingoCard card = (BingoCard) e.getComponent();
		if(!card.isPurchased())
			group.showSelector(card);
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
