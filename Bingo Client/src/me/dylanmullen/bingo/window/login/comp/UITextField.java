package me.dylanmullen.bingo.window.login.comp;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;

public class UITextField extends JTextField
{
	private Shape shape;
	public UITextField(String text, int x, int y, int w, int h)
	{
		setBounds(x, y, w, h);
		setOpaque(true);
		setText(text);
	}

	protected void paintComponent(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g);
	}

	protected void paintBorder(Graphics g)
	{
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
	}

}
