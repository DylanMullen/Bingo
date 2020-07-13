package me.dylanmullen.bingo.game.components.overlays;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.util.FontUtil;

public class WinnerOverlay extends Overlay
{

	private static final long serialVersionUID = 5162085351393194993L;

	private UIColourSet set;
	private Font headerFont;
	private Font winnersFont;

	private List<String> lines;

	public WinnerOverlay(int x, int y, int width, int height)
	{
		super(BingoApp.getInstance().getColourManager().getSet("overlays").getColour("winner-body")
				.applyTransparency(200), x, y, width, height);
		setup();
	}

	public void setup()
	{
		this.set = BingoApp.getInstance().getColourManager().getSet("overlays");
		this.headerFont = new Font("Calibri", Font.PLAIN, 36);
		this.winnersFont = new Font("Calibri", Font.PLAIN, 18);
		this.lines = new ArrayList<String>();

		setBackground(set.getColour("winner-header").toColour());
		setForeground(set.getColour("winner-header").getTextColour());
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawHeader(g2);
		drawBody(g2);
	}

	private void drawHeader(Graphics2D g2)
	{
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight() / 8);
		g2.setColor(getForeground());
		g2.setFont(headerFont);
		Dimension dim = FontUtil.getFontSize(getFontMetrics(headerFont), "We have a Winner!", 0, 0);
		g2.drawString("We have a Winner!", getWidth() / 2 - dim.width / 2, (getHeight() / 8) / 2 + dim.height / 4);
	}

	private void drawBody(Graphics2D g2)
	{
		g2.setColor(getForeground());
		Dimension prev = null;
		int indentY = -1;
		for (int i = 0; i < lines.size(); i++)
		{
			String text = lines.get(i);
			prev = FontUtil.getFontSize(getFontMetrics(winnersFont), text, 0, 0);
			int indentX = (i % 2 == 0 ? getWidth() / 4 : getWidth() / 4 * 3);
			if (indentY == -1)
			{
				indentY = getHeight() / 8;
			}

			if (i % 2 == 0)
				indentY += prev.height + 15;
			g2.drawString(text, indentX - (prev.width), indentY);
		}
	}

	public void setWinners(List<String> list)
	{
		if (list == null)
		{
			lines.clear();
			return;
		}

		for (int i = 0; i < list.size(); i++)
		{
			lines.add(list.get(i) + (list.size() - 1 == i ? "" : "\n"));
		}
		repaint();
	}
}
