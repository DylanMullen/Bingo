package me.dylanmullen.bingo.game.home;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.window.bingo.BingoWindow;
import me.dylanmullen.bingo.window.ui.ImageComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

public class HomePanel extends Panel implements Scrollable, MouseMotionListener
{

	private static final long serialVersionUID = 1L;

	private BingoWindow window;

	private static HomePanel instance;

	public HomePanel(BingoWindow window, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		if (instance == null)
			instance = this;
		this.window = window;
	}

	public static HomePanel getInstance()
	{
		return instance;
	}

	@Override
	public void setup()
	{
		setBackground(UIColour.FRAME_BINGO_BG.toColor());
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				repaint();
			}
		});
		
		int width = (getWidth() - (10 * 3)) / 2;

		ImageComponent ic = new ImageComponent(15, 15, getWidth() - 30, (int) (height / 8) * 2);
		try
		{
			ic.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		add(ic);

		Grid grid = new Grid(new GridSettings(getWidth() - 100, (int) (height / 8) * 6 - 50, -1, 3, 15), 50,
				height + 25 - (int) (height / 8) * 6);

		grid.getSettings().setFixedRowHeight(290);

		for (int i = 0; i < 12; i++)
		{
			GameSelector selector = new GameSelector(10, 10, width, 100);
			int temp = i + 1;
			int row = temp / 3;
			if (temp % 3 == 0 && temp != 0)
				row--;
			grid.addGridItem(new GridItem(selector, 1, 1), row, false, true);
			add(selector);
		}
		grid.updatePositions();

		List<GridItem> items = grid.getItems();
		int yPos = -1;
		for (int i = 0; i < items.size(); i++)
		{
			GameSelector gs = (GameSelector) items.get(i).getComponent();
			gs.create();
			if (i == items.size() - 1)
				yPos = gs.getY() + gs.getHeight() + 25;
		}
		if (yPos != -1)
			setBounds(0, 0, getWidth(), yPos);
	}

	@Override
	public void create()
	{

	}

	public BingoWindow getWindow()
	{
		return window;
	}

	// Methods required by the MouseMotionListener interface:
	public void mouseMoved(MouseEvent e)
	{
	}

	public void mouseDragged(MouseEvent e)
	{
		// The user is dragging us, so scroll!
		Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
		scrollRectToVisible(r);
	}

	public Dimension getPreferredSize()
	{
		return new Dimension(getWidth(), getHeight());
	}

	private int maxUnitIncrement = 20;

	public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		// Get the current position.
		int currentPosition = 0;
		if (orientation == SwingConstants.HORIZONTAL)
		{
			currentPosition = visibleRect.x;
		} else
		{
			currentPosition = visibleRect.y;
		}

		// Return the number of pixels between currentPosition
		// and the nearest tick mark in the indicated direction.
		if (direction < 0)
		{
			int newPosition = currentPosition - (currentPosition / maxUnitIncrement) * maxUnitIncrement;
			return (newPosition == 0) ? maxUnitIncrement : newPosition;
		} else
		{
			return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
		}
	}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
	{
		if (orientation == SwingConstants.HORIZONTAL)
		{
			return visibleRect.width - maxUnitIncrement;
		} else
		{
			return visibleRect.height - maxUnitIncrement;
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
		maxUnitIncrement = pixels;
	}
}
