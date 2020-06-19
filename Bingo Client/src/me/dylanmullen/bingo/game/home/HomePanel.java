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

	private BingoWindow bingoWindow;

	private static HomePanel instance;
	private int maxUnitIncrement = 20;

	/**
	 * This is the Home Panel for the Bingo Application.<br>
	 * This panel contains all the possible games that a Player can join as well as
	 * a banner for a company logo.
	 * 
	 * @param bingoWindow The Bingo Window of the Bingo Application.
	 * @param x           X-Position of the Home Panel.
	 * @param y           Y-Position of the Home Panel.
	 * @param width       The width of the Home Panel.
	 * @param height      The height of the Home Panel.
	 */
	public HomePanel(BingoWindow bingoWindow, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		if (HomePanel.instance == null)
			HomePanel.instance = this;
		this.bingoWindow = bingoWindow;
	}

	/**
	 * Returns the instance of the Home Panel
	 * 
	 * @return {@link #instance}
	 */
	public static HomePanel getInstance()
	{
		return HomePanel.instance;
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

		ImageComponent ic = new ImageComponent(15, 15, getWidth() - 30, (int) (getHeight() / 8) * 2);
		try
		{
			ic.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		add(ic);

		Grid grid = new Grid(new GridSettings(getWidth() - 100, (int) (getHeight() / 8) * 6 - 50, -1, 3, 15), 50,
				getHeight() + 25 - (int) (getHeight() / 8) * 6);

		grid.getGridSettings().setFixedRowHeight(290);

		for (int i = 0; i < 12; i++)
		{
			GameSelector selector = new GameSelector(10, 10, width, 100);
			int temp = i + 1;
			int row = temp / 3;
			if (temp % 3 == 0 && temp != 0)
				row--;
			grid.addGridItem(new GridItem(selector, 1, 1), row, false);
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

	/**
	 * Returns the Bingo Window of the Bingo Application.
	 * 
	 * @return {@link #bingoWindow}
	 */
	public BingoWindow getWindow()
	{
		return this.bingoWindow;
	}

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
