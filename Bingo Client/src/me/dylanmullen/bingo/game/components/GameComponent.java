package me.dylanmullen.bingo.game.components;

import java.awt.Color;

import me.dylanmullen.bingo.game.CardGroupComp;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class GameComponent extends Panel
{

	private JoinComponent join;
	private CardGroupComp comp;

	public GameComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG.toColor());

//		join = new JoinComponent(12, (getHeight() / 10), getWidth() - 24, (getHeight() / 2));
//		join.setup();
//		add(join);

		comp = new CardGroupComp(12, 12, getWidth() - 24, getHeight() -  24);
		comp.setup();
		add(comp);
	}

	@Override
	public void create()
	{

	}

	public void disableJoinButton()
	{
		remove(join);
	}
	
	public CardGroupComp getCardGroup()
	{
		return comp;
	}
}
