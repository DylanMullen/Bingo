package me.dylanmullen.bingo.game.header;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.components.HeaderInfoPanel;
import me.dylanmullen.bingo.game.header.numbers.NumbersCalledComponent;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class HeaderPanel extends UIPanel
{

	private static final long serialVersionUID = 1154494652172939716L;

	private NumbersCalledComponent numbersComp;
	private HeaderInfoPanel info;

	public HeaderPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("header").toColour());
		setLayout(null);

		this.numbersComp = new NumbersCalledComponent(getWidth() / 4, 0, (getWidth() / 4) * 3, getHeight());
		numbersComp.setup();
		numbersComp.create();

		this.info = new HeaderInfoPanel(0, 0, getWidth() / 4, getHeight());
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
		numbersComp.setVisible(true);
		numbersComp.repaint();
		numbersComp.setDisplaying(true);
		add(numbersComp);
		repaint();
	}

	public void debug()
	{
		showNumberComp();
		numbersComp.update(50);
		numbersComp.update(60);
//		numbersComp.update(70);
		numbersComp.update(80);
		numbersComp.update(90);
	}

	public boolean isShowingNumbers()
	{
		return getNumbersComp().isDisplaying();
	}

	public NumbersCalledComponent getNumbersComp()
	{
		return numbersComp;
	}

	public HeaderInfoPanel getInfo()
	{
		return info;
	}

}
