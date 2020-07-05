package me.dylanmullen.bingo.game.header.numbers;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class NumbersCalledComponent extends UIPanel
{

	private static final long serialVersionUID = 4164051545883107133L;

	private boolean displaying = false;
	private UIColourSet colours;
	private UIColour current;

	public NumbersCalledComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.colours = BingoApp.getInstance().getColourManager().getSet("numbers");
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				repaint();
			}
		});
	}

	private int[] numbers;

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setOpaque(false);
		this.numbers = new int[5];
		for (int i = 0; i < numbers.length; i++)
		{
			numbers[i] = -1;
		}
	}

	@Override
	public void create()
	{
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawBubbles(g2);
	}

	private void drawBubbles(Graphics2D g2)
	{
		int dim = (getHeight() - 10) / 4 * 3;
		int indentX = getWidth() / 2 - (((dim * 4) / 2) + (dim + 10));
		int indentY = getHeight() / 2 - (dim / 2);
		for (int i = 0; i < numbers.length; i++)
		{
			if (numbers[i] == -1)
				continue;
			setColour(numbers[i]);
			g2.setColor(current.toColour());
			g2.fillOval(indentX, indentY - (i == 0 ? 10 : 0), dim + (i == 0 ? 20 : 0), dim + (i == 0 ? 20 : 0));
			g2.setStroke(new BasicStroke(2));
			g2.setColor(current.darken(0.15).toColour());
			g2.drawOval(indentX, indentY - (i == 0 ? 10 : 0), dim + (i == 0 ? 20 : 0), dim + (i == 0 ? 20 : 0));

			g2.setColor(current.getTextColour());

			indentX += dim + (i == 0 ? 20 : 0) + 5;
		}
	}

	private void setColour(int number)
	{
		int temp = number / 10;
		if (temp == 9)
			temp--;
		System.out.println(number);
		this.current = colours.getColour("ball-" + temp);
	}

	public void update(int num)
	{
		int temp = numbers[0];
		numbers[0] = num;
		if (temp != -1)
		{
			for (int i = 1; i < numbers.length; i++)
			{
				if (temp == -1)
					break;
				int x = numbers[i];
				numbers[i] = temp;
				temp = x;
			}
		}
		updateCalls();
		repaint();
	}

	public void restart()
	{
		for (int i = 0; i < numbers.length; i++)
		{
			numbers[i] = -1;
		}
	}

	private void updateCalls()
	{
	}

	public void setDisplaying(boolean displaying)
	{
		this.displaying = displaying;
	}

	public boolean isDisplaying()
	{
		return displaying;
	}

}
