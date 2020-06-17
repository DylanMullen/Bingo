package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.BingoCardContainer;

public class BingoCardListener extends MouseAdapter
{

	private BingoCardContainer group;

	public BingoCardListener(BingoCardContainer group)
	{
		this.group = group;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		BingoCard card = (BingoCard) e.getComponent();
		if(!card.isPurchased())
		{
			group.selectCard(card);
		}
	}
}
