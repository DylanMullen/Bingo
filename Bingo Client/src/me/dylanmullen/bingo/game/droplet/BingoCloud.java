package me.dylanmullen.bingo.game.droplet;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.home.DropletSelector;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class BingoCloud extends Panel
{

	private static final long serialVersionUID = 8868641235552384755L;

	private UUID uuid;

	private List<DropletSelector> dropletSelectors;
	private Grid grid;
	private int indentY, minHeight;

	public BingoCloud(UUID uuid, JSONObject droplets, int width, int height)
	{
		super(0, 0, width, height);
		this.uuid = uuid;
		this.indentY = height / 4;
		this.minHeight = height;
		setup();
		setupDropletSelectors(droplets);
	}

	private void setupDropletSelectors(JSONObject droplets)
	{
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
			int row = temp / 3;
			if (temp % 3 == 0 && temp != 0)
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
				yPos = gs.getY() + gs.getHeight() + 25;
		}
		if (yPos != -1 && yPos >= minHeight)
			setBounds(0, 0, getWidth(), yPos);
	}

	@Override
	public void setup()
	{
		this.dropletSelectors = new ArrayList<>();
		setBackground(UIColour.FRAME_BINGO_BG.toColor());
		this.grid = new Grid(new GridSettings(getWidth() - 50, getHeight() - indentY - 30, -1, 2, 5), 25, indentY + 15);
		grid.getGridSettings().setFixedRowHeight(50);
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
		g2.setColor(Color.red);
		paintHeader(g2);
	}

	private void paintHeader(Graphics2D g2)
	{
		g2.fillRect(0, 0, getWidth(), indentY);
	}

}
