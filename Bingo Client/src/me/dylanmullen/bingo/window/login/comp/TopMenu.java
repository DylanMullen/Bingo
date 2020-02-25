package me.dylanmullen.bingo.window.login.comp;

import me.dylanmullen.bingo.window.bingo.ui.buttons.CloseButton;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class TopMenu extends Panel
{

	public TopMenu(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}
	
	private CloseButton close;
	
	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		close = new CloseButton("", getWidth()-100, 0, 100, getHeight());
		close.create();
	}

	@Override
	public void create()
	{
		add(close);
	}

}
