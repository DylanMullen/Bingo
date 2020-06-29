package me.dylanmullen.bingo.game.home;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.window.bingo.BingoWindow;
import me.dylanmullen.bingo.window.ui.ImageComponent;

public class HomePanel extends UIPanel
{

	private static final long serialVersionUID = 1L;

	private BingoWindow bingoWindow;

	private static HomePanel instance;
	private List<CloudSelector> gameSelectors;
	private Grid grid;

	private int minHeight;

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
					CloudSelector selector = new CloudSelector(0, 0, width, 0);
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
		setBackground(BingoApp.getInstance().getColours().getSet("frame").getColour("content").toColour());
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
			CloudSelector selector = gameSelectors.get(i);
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
			CloudSelector gs = gameSelectors.get(i);
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
}
