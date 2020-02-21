package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

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
	}

	@Override
	public void create()
	{

	}


	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(UIColour.BTN_FAILURE.toColor());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		
	}

}
