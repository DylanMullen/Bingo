package me.dylanmullen.bingo.game.components;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class HeaderPanel extends UIPanel
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
		setBackground(BingoApp.getInstance().getColours().getSet("frame").getColour("header").toColour());
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
