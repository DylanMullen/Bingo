package me.dylanmullen.bingo.gfx.ui.panel;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

public abstract class UIPanel extends JPanel implements Scrollable, MouseMotionListener
{

	private static final long serialVersionUID = -3182799998146452612L;

	protected int x, y;
	protected int width, height;

	private int maxUnitIncrement = 20;

	public UIPanel(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		setLayout(null);
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		this.x = getX();
		this.y = getY();
		this.width = getWidth();
		this.height = getHeight();
	}

	public abstract void setup();
	public abstract void create();

	public void mouseMoved(MouseEvent e)
	{
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
		scrollRectToVisible(r);
	}

	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(getWidth(), getHeight());
	}

	public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		int currentPosition = 0;
		if (orientation == SwingConstants.HORIZONTAL)
		{
			currentPosition = visibleRect.x;
		} else
		{
			currentPosition = visibleRect.y;
		}
		if (direction < 0)
		{
			int newPosition = currentPosition - (currentPosition / this.maxUnitIncrement) * this.maxUnitIncrement;
			return (newPosition == 0) ? this.maxUnitIncrement : newPosition;
		} else
		{
			return ((currentPosition / this.maxUnitIncrement) + 1) * this.maxUnitIncrement - currentPosition;
		}
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		if (orientation == SwingConstants.HORIZONTAL)
		{
			return visibleRect.width - this.maxUnitIncrement;
		} else
		{
			return visibleRect.height - this.maxUnitIncrement;
		}
	}

	public boolean getScrollableTracksViewportWidth()
	{
		return false;
	}

	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}

	public void setMaxUnitIncrement(int pixels)
	{
		this.maxUnitIncrement = pixels;
	}

}
