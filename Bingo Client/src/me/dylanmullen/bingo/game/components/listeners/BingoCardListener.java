package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.CardGroupComp;

public class BingoCardListener extends MouseAdapter
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
		{
			group.showSelector(card);
		}
	}
}
