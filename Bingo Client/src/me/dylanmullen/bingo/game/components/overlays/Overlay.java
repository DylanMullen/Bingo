package me.dylanmullen.bingo.game.components.overlays;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public abstract class Overlay extends JComponent
{

	private static final long serialVersionUID = -2003166127277432695L;

	private Color backgroundColour;

	public Overlay(Color bgColour, int x, int y, int width, int height)
	{
		this.backgroundColour = bgColour;
		setBounds(x, y, width, height);
		setLayout(null);
	}

	public abstract void setup();

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(backgroundColour);
		g2.fillRect(0, 0, getWidth(), getHeight());
	}

}
