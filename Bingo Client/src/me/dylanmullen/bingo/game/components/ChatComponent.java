package me.dylanmullen.bingo.game.components;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ChatComponent extends Panel
{

	public ChatComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
	}

	@Override
	public void create()
	{

	}

}
