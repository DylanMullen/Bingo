package me.dylanmullen.bingo.game.header;

import java.awt.Graphics;

import me.dylanmullen.bingo.core.BingoApp;
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
		setForeground(
				BingoApp.getInstance().getColourManager().getSet("frame").getColour("header").darken(0.15).toColour());
		setLayout(null);

		this.numbersComp = new NumbersCalledComponent(0, 0, (int) ((getWidth() / 4) * 2.5), getHeight() - 10);
		numbersComp.setup();
		numbersComp.create();

		this.info = new HeaderInfoPanel((int) ((getWidth() / 4) * 2.5), 0, getWidth() - (int) ((getWidth() / 4) * 2.5),
				getHeight());
		info.setBackground(getForeground());
		info.setup();
		info.create();
		info.setText("Join A Game!");
	}

	@Override
	public void create()
	{
		add(info);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(getForeground());
		g.fillRect(0, getHeight() - 10, getWidth(), 10);
	}

	public void showNumberComp()
	{
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

	public HeaderInfoPanel getInfo()
	{
		return info;
	}

}
