package me.dylanmullen.bingo.game;

import java.util.UUID;

import me.dylanmullen.bingo.window.ui.Panel;

public class CardGroupComp extends Panel
{

	private static final long serialVersionUID = -2951295854163796573L;
	
	private BingoCard[] cards;

	public CardGroupComp(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setOpaque(false);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		this.cards = new BingoCard[3];

		int indentY = 5;
		int contHeight = getHeight() - (5 * 5);
		int height = contHeight / 3 - 35;
		for (int i = 0; i < cards.length; i++)
		{
			cards[i] = new BingoCard(5, indentY, ((getWidth() - 10) / 4) * 3,
					height);
			indentY += height + 35 + 10;
			add(cards[i]);
		}
	}

	public void setCardNumbers(String[] data)
	{
		int index = 0;
		for (int i = 1; i < data.length; i++)
		{
			if (index >= cards.length)
				break;
			if (cards[index] == null)
				break;

			String[] temp = data[i].split("/u/");
			UUID uuid = UUID.fromString(temp[1]);
			cards[index].setCardNumbers(temp[0], uuid);
			index++;
		}
		cards[0].paintComponent(cards[0].getGraphics());
	}

	@Override
	public void create()
	{

	}
}
