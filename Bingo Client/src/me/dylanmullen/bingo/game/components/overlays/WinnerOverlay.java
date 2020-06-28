package me.dylanmullen.bingo.game.components.overlays;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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
	private final int OFFSET = 10;

	private UIColourSet set;
	private Font headerFont;
	private Font winnersFont;

	private List<String> lines;

	public WinnerOverlay(int x, int y, int width, int height)
	{
		super(BingoApp.getInstance().getColours().getSet("overlays").getColour("winner-body").applyTransparency(200), x,
				y, width, height);
		setup();
	}

	public void setup()
	{
		this.set = BingoApp.getInstance().getColours().getSet("overlays");
		this.headerFont = new Font("Calibri", Font.PLAIN, 30);
		this.winnersFont = new Font("Calibri", Font.PLAIN, 25);
		this.lines = new ArrayList<String>();

		setBackground(set.getColour("winner-header").toColour());
		setForeground(set.getColour("winner-text").toColour());
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
		Dimension dim = FontUtil.getFontSize(getFontMetrics(headerFont), "We have a Winner!", 0, 0);
		g2.drawString("We have a Winner!", getWidth() / 2 - dim.width / 2, (getHeight() / 8) / 2 + dim.height / 4);
	}

	private void drawBody(Graphics2D g2)
	{
		g2.setColor(getForeground());
		Dimension prev = null;
		int indentY = -1;
		int height = -1;
		for (int i = 0; i < lines.size(); i++)
		{
			String text = lines.get(i);
			prev = FontUtil.getFontSize(getFontMetrics(getFont()), text, 0, 0);
			if (height == -1)
			{
				height = prev.height * lines.size();
				indentY = (getHeight() / 2 + ((getHeight() - height))) / 2;
			}

			g2.drawString(text, getWidth() / 2 - (prev.width / 2), indentY);
			indentY += prev.height;
		}
	}

	public void setWinners(List<String> list)
	{
		if (list == null)
		{
			construct("");
			System.out.println("winners is null");
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i += 2)
		{
			sb.append(list.get(i) + (list.size() - 1 == i ? "   " : "\n"));
			if (list.size() - 1 == i)
				break;
			sb.append(list.get(i + 1) + (list.size() - 1 == i ? "" : "\n"));
		}
		construct(sb.toString());
		repaint();
	}

	private void construct(String message)
	{
		lines.clear();
		FontMetrics metrics = getFontMetrics(getFont());
		StringBuilder lineBuilder = new StringBuilder();
		int currentWidth = 0;

		for (int i = 0; i < message.length(); i++)
		{
			currentWidth = FontUtil.getFontSize(metrics, lineBuilder.toString(), 0, 0).width;
			if (currentWidth >= getWidth() - (OFFSET * 3) || message.charAt(i) == '\n')
			{
				currentWidth = 0;
				lines.add(lineBuilder.toString());
				lineBuilder = new StringBuilder();
			}
			lineBuilder.append(message.charAt(i));

			if (message.length() - 1 == i)
				lines.add(lineBuilder.toString());
		}
		repaint();
	}
}
