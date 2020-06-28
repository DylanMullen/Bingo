package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class RoundedButton extends UIButton
{

	private static final long serialVersionUID = 7148220456273640049L;

	public RoundedButton(String text, ButtonInformation information)
	{
		super(text, information);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		g2.setColor(getForeground());
		drawText(g2, 0);
	}

}
