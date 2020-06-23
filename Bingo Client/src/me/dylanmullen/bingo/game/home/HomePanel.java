package me.dylanmullen.bingo.game.home;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
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
	private List<GameSelector> gameSelectors;
	private Grid grid;

	private int minHeight;
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
		this.minHeight = height;
		if (HomePanel.instance == null)
			HomePanel.instance = this;
		this.bingoWindow = bingoWindow;
		setup();
		sendCloudRetrivalPacket();
	}

	private void sendCloudRetrivalPacket()
	{
		Packet packet = PacketHandler.createPacket(17, new JSONObject());
		PacketHandler.sendPacket(packet, new PacketCallback()
		{
			@Override
			public boolean callback()
			{
				for (Object key : getMessage().keySet())
				{
					GameSelector selector = new GameSelector(0, 0, width, 0);
					selector.setupInformation(UUID.fromString((String) key), (JSONObject) getMessage().get(key));
					gameSelectors.add(selector);
				}
				updateAllSelectors();
				return false;
			}
		});
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

	int width = (getWidth() - (10 * 3)) / 2;

	@Override
	public void setup()
	{
		this.gameSelectors = new ArrayList<>();
		setBackground(UIColour.FRAME_BINGO_BG.toColor());
		grid = new Grid(new GridSettings(getWidth() - 100, (int) (getHeight() / 8) * 6 - 50, -1, 3, 15), 50,
				getHeight() + 25 - (int) (getHeight() / 8) * 6);
		grid.getGridSettings().setFixedRowHeight(290);

		ImageComponent ic = new ImageComponent(15, 15, getWidth() - 30, (int) (getHeight() / 8) * 2);
		try
		{
			ic.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		add(ic);
	}

	public void updateAllSelectors()
	{
		for (int i = 0; i < gameSelectors.size(); i++)
		{
			GameSelector selector = gameSelectors.get(i);
			int temp = i + 1;
			int row = temp / 3;
			if (temp % 3 == 0 && temp != 0)
				row--;
			grid.addGridItem(new GridItem(selector, 1, 1), row, false);
			add(selector);
		}
		grid.updatePositions();
		int yPos = -1;
		for (int i = 0; i < gameSelectors.size(); i++)
		{
			GameSelector gs = gameSelectors.get(i);
			gs.create();
			if (i == gameSelectors.size() - 1)
				yPos = gs.getY() + gs.getHeight() + 25;
		}
		if (yPos != -1 && yPos >= minHeight)
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
