package me.dylanmullen.bingo.gfx.ui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public abstract class RoundedPanel extends UIPanel
{

	private static final long serialVersionUID = 9015493102729113496L;

	public RoundedPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		super.paintComponent(g2);
	}

}
