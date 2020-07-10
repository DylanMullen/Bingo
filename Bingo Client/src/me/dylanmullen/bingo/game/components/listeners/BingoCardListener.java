package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.game.cards.BingoCard;
import me.dylanmullen.bingo.game.cards.BingoCardGroup;

public class BingoCardListener extends MouseAdapter
{

	private BingoCardGroup group;

	public BingoCardListener(BingoCardGroup group)
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
