package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class ProfilePanel extends Panel
{

	private static final long serialVersionUID = 3952772083758555561L;

	public ProfilePanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBorder(new EmptyBorder(12, 12, 12, 12));
		setOpaque(false);
		size = getWidth() / 10;
		System.out.println(getWidth());
	}

	@Override
	public void create()
	{
		
	}

	private int size;

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(UIColour.BTN_FAILURE.toColor());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

//		paintGrid(g);
	}

	private void paintGrid(Graphics g)
	{
		g.setColor(Color.white);
		for (int y = 0; y < getHeight(); y += size)
		{
			g.drawLine(0, y, getHeight(), y);
			for (int x = 0; x < getWidth(); x += size)
			{
				g.drawLine(x, 0, x, getWidth());
			}
		}
	}

}
