package me.dylanmullen.bingo.game.components;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class HeaderPanel extends Panel
{

	private static final long serialVersionUID = 1154494652172939716L;

	private NumbersCalledComponent numbersComp;
	private InfoPanel info;

	public HeaderPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBackground(UIColour.BTN_BINGO_ACTIVE.toColor());
		setLayout(null);

		this.numbersComp = new NumbersCalledComponent(0, 0, getWidth(), getHeight());
		numbersComp.setup();
		numbersComp.create();

		this.info = new InfoPanel(getWidth() / 4, 0, getWidth() / 2, getHeight());
		info.setup();
		info.create();
		info.setText("Join A Game!");
	}

	@Override
	public void create()
	{
		add(info);
	}
	
	public void showNumberComp()
	{
		info.setVisible(false);
		info.repaint();
		remove(info);
		
		numbersComp.setVisible(true);
		numbersComp.repaint();
		numbersComp.setDisplaying(true);
		add(numbersComp);
		repaint();
	}
	
	public boolean isShowingNumbers()
	{
		return getNumbersComp().isDisplaying();
	}

	public NumbersCalledComponent getNumbersComp()
	{
		return numbersComp;
	}
	
	public InfoPanel getInfo()
	{
		return info;
	}

}
