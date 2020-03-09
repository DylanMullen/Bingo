package me.dylanmullen.bingo.game.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class NumberGrid extends JLabel
{

	private static final long serialVersionUID = 3244273213715012491L;

	private int x, y;
	private int w, h;

	private boolean isFirst;
	private int currentNumber = -1;

	public NumberGrid(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void setup()
	{
		setBounds(x, y, w, h);
		if (currentNumber != -1)
			setText(currentNumber + "");
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(new Font("Calibri", Font.PLAIN, 40));
		setForeground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		if (currentNumber == -1)
			return;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawCircles(g2);
		super.paintComponent(g);
	}
	
	private void drawCircles(Graphics2D g2)
	{
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.BLACK);
		if (!isFirst)
			g2.fillOval((int) ((w / 2) - ((h / 1.25) / 2)), (int) ((w / 2) - ((h / 1.25) / 2)), (int) (h / 1.25),
					(int) (h / 1.25));
		else
			g2.fillOval(w / 2 - w / 2, h / 2 - w / 2, w, h);

	}

	public int updateCurrentNumber(int current)
	{
		int temp = currentNumber;
		this.currentNumber = current;
		setText(current + "");
		repaint();
		return temp;
	}

	public int getCurrentNumber()
	{
		return currentNumber;
	}

	public void setFirst(boolean isFirst)
	{
		this.isFirst = isFirst;
	}
}
