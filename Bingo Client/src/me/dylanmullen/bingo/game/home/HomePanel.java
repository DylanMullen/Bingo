package me.dylanmullen.bingo.game.home;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.util.Vector2I;
import me.dylanmullen.bingo.window.bingo.BingoWindow;

public class HomePanel extends UIPanel
{

	private static final long serialVersionUID = 1L;

	private BingoWindow bingoWindow;

	private static HomePanel instance;
	private List<CloudSelector> gameSelectors;
	private Grid grid;

	private UIColourSet set;
	private int minHeight;
	private int indentY;

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
		this.set = BingoApp.getInstance().getColourManager().getSet("frame");
		this.indentY = (int) (height / 2.5);
		setup();
		sendCloudRetrivalPacket();
	}

	private void sendCloudRetrivalPacket()
	{
		debug();
		Packet packet = PacketHandler.createPacket(17, new JSONObject());
		PacketHandler.sendPacket(packet, new PacketCallback()
		{
			@Override
			public boolean callback()
			{
				for (Object key : getMessage().keySet())
				{
					CloudSelector selector = new CloudSelector(0, 0, width, 0);
					selector.setupInformation(UUID.fromString((String) key), (JSONObject) getMessage().get(key));
					gameSelectors.add(selector);
				}
				updateAllSelectors();
				return false;
			}
		});
	}

	private void debug()
	{
		JSONObject object = new JSONObject();
		object.put("cloudName", "Charlis Angles");
		object.put("ticketPrice", 0.15);
		for (int i = 0; i < 6; i++)
		{
			CloudSelector selector = new CloudSelector(0, 0, width, 0);
			selector.setupInformation(UUID.randomUUID(), object);
			gameSelectors.add(selector);
		}
		updateAllSelectors();
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
		setBackground(set.getColour("content").toColour());
		setForeground(set.getColour("bingo-selector-header").toColour());
		grid = new Grid(new GridSettings(getWidth() - 70, ((getHeight() - indentY)), -1, 3, 10), 35, indentY + 25);
		grid.getGridSettings().setFixedRowHeight(200);
	}

	public void updateAllSelectors()
	{
		for (int i = 0; i < gameSelectors.size(); i++)
		{
			CloudSelector selector = gameSelectors.get(i);
			int temp = i + 1;
			int row = temp / 3;
			if (temp % 3 == 0 && temp != 0)
				row--;
			grid.addGridItem(new GridItem(selector, 1, 1), row, false);
			add(selector);
		}
		int width = grid.getTotalWidth();
		grid.updatePosition(new Vector2I(getWidth() / 2 - width / 2 + 20, grid.getY())).updatePositions();
		int yPos = -1;
		for (int i = 0; i < gameSelectors.size(); i++)
		{
			CloudSelector gs = gameSelectors.get(i);
			gs.create();
			if (i == gameSelectors.size() - 1)
				yPos = gs.getY() + gs.getHeight() + 25;
		}

		if (yPos != -1 && yPos >= minHeight)
			resize(yPos + 15);
	}

	public void resize(int height)
	{
		setBounds(0, 0, getWidth() - 20, height);
		grid.updateWidth(getWidth() - 100);
		repaint();
	}

	@Override
	public void create()
	{
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		paintHeader(g2);
		paintBody(g2);
	}

	private void paintHeader(Graphics2D g2)
	{
		g2.setColor(getForeground());
		g2.fillRect(0, 0, getWidth(), indentY - 10);
		g2.setColor(set.getColour("bingo-selector-header").darken(0.15).toColour());
		g2.fillRect(0, indentY - 10, getWidth(), 10);
	}

	private void paintBody(Graphics2D g2)
	{
		g2.setColor(set.getColour("content").darken(0.20).toColour());
		int height = grid.getTotalHeight();
		g2.fillRoundRect(20, indentY + 20, getWidth() - 40, height, 15, 15);

		g2.setStroke(new BasicStroke(2));
		g2.setColor(set.getColour("content").darken(0.35).toColour());
		g2.drawRoundRect(20 + 1, indentY + 20 + 1, (getWidth() - 40) - 1, height - 1, 15, 15);
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
}
