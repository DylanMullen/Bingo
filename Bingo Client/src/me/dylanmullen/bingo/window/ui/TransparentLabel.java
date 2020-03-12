package me.dylanmullen.bingo.window.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

public class TransparentLabel extends JLabel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5994128161256191356L;
	private int tValue;

	public TransparentLabel(int transparency)
	{
		this.tValue = transparency;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Color colour = getBackground();
		if (colour == null)
			return;

		Graphics2D g2 = (Graphics2D) g;
		int r = colour.getRed();
		int gr = colour.getGreen();
		int b = colour.getBlue();

		g2.setColor(new Color(r, gr, b, tValue));
		g2.fillRect(0, 0, getWidth(), getHeight());
		
		super.paintComponent(g);
	}

}
