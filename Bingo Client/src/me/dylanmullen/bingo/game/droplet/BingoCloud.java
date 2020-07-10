package me.dylanmullen.bingo.game.droplet;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.UIManager;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.home.DropletSelector;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class BingoCloud extends UIPanel
{

	private static final long serialVersionUID = 8868641235552384755L;

	private UUID uuid;

	private List<DropletSelector> dropletSelectors;
	private Grid grid;
	private int indentY, minHeight;

	private UIColourSet set;

	public BingoCloud(UUID uuid, JSONObject droplets, int width, int height)
	{
		super(0, 0, width, height);
		this.uuid = uuid;
		this.indentY = (int) (height / 2.5);
		this.minHeight = height;
		this.set = BingoApp.getInstance().getColourManager().getSet("frame");
		setup();
		setupDropletSelectors(droplets);

	}

	private void setupDropletSelectors(JSONObject droplets)
	{
//		for (int i = 0; i < 4; i++)
//		{
//			DropletSelector selector = new DropletSelector(0, 0, width, 0);
//			selector.setupInformation(uuid, UUID.randomUUID(), i, i, 0);
//			dropletSelectors.add(selector);
//		}
		int index = 1;
		for (Object key : droplets.keySet())
		{
			DropletSelector selector = new DropletSelector(0, 0, width, 0);
			selector.setupInformation(uuid, UUID.fromString((String) key), index,
					((Number) droplets.get(key)).intValue(), 0);
			dropletSelectors.add(selector);
			index++;
		}
		updateAllSelectors();
	}

	private void updateAllSelectors()
	{
		for (int i = 0; i < dropletSelectors.size(); i++)
		{
			DropletSelector selector = dropletSelectors.get(i);
			int temp = i + 1;
			int row = temp / 2;
			if (temp % 2 == 0 && temp != 0)
				row--;
			grid.addGridItem(new GridItem(selector, 1, 1), row, false);
			add(selector);
		}
		grid.updatePositions();
		int yPos = -1;
		for (int i = 0; i < dropletSelectors.size(); i++)
		{
			DropletSelector gs = dropletSelectors.get(i);
			gs.setup();
			if (i == dropletSelectors.size() - 1)
				yPos = gs.getY() + gs.getHeight() + 20 + 15 + 5;
		}
		if (yPos != -1 && yPos >= minHeight)
			resize(yPos);
	}

	@Override
	public void setup()
	{
		this.dropletSelectors = new ArrayList<>();
		this.grid = new Grid(new GridSettings(getWidth() - 40 - 30, ((getHeight() - indentY) - 40) - 30, -1, 2, 5),
				20 + 15, indentY + 20 + 15);
		grid.getGridSettings().setFixedRowHeight(50);

		setBackground(set.getColour("content").toColour());
	}

	@Override
	public void create()
	{
	}

	public void resize(int height)
	{
		setBounds(0, 0, getWidth() - ((Integer) UIManager.get("ScrollBar.width")).intValue(), height);
		grid.updateWidth(getWidth() - 40 - 30);
		repaint();
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
		g2.setColor(set.getColour("header").toColour());
		g2.fillRect(0, 0, getWidth(), indentY - 15);

		g2.setColor(set.getColour("header").darken(0.15).toColour());
		g2.fillRect(0, indentY - 15, getWidth(), 15);
	}

	private void paintBody(Graphics2D g2)
	{
		g2.setColor(set.getColour("content").darken(0.20).toColour());
		g2.fillRoundRect(20, indentY + 20, getWidth() - 40, (getHeight() - indentY) - 40, 15, 15);

		g2.setStroke(new BasicStroke(2));
		g2.setColor(set.getColour("content").darken(0.35).toColour());
		g2.drawRoundRect(20, indentY + 20, getWidth() - 40, (getHeight() - indentY) - 40, 15, 15);
	}

}
