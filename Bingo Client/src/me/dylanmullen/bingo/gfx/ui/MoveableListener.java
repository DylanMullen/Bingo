package me.dylanmullen.bingo.gfx.ui;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class MoveableListener extends MouseAdapter
{

	private Point initialClick;

	private JComponent component;
	private Container parent;

	public MoveableListener(JComponent component, Container container)
	{
		this.component = component;
		this.parent = container;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		int thisX = e.getComponent().getLocation().x;
		int thisY = e.getComponent().getLocation().y;

		int xMoved = e.getX() - initialClick.x;
		int yMoved = e.getY() - initialClick.y;

		int X = thisX + xMoved;
		int Y = thisY + yMoved;

		if (parent != null)
		{
			component.setLocation(clampX(X), clampY(Y));
			return;
		}
		component.setLocation(X, Y);
	}

	public void mousePressed(MouseEvent e)
	{
		initialClick = e.getPoint();
		component.getComponentAt(initialClick);
	}

	public int clampX(int pos)
	{
		if (pos < 0)
			return 0;
		else if (pos > parent.getWidth() - component.getWidth())
			return parent.getWidth() - component.getWidth();
		else
			return pos;
	}

	public int clampY(int pos)
	{
		if (pos < 0)
			return 0;
		else
		{
			int temp = pos + component.getHeight() - 1;
			if (temp > parent.getHeight())
			{
				return parent.getHeight() - component.getHeight();
			}
			else
				return pos;
		}
	}

}
