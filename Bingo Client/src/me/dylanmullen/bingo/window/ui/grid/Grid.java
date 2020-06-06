package me.dylanmullen.bingo.window.ui.grid;

import java.util.ArrayList;
import java.util.List;

public class Grid
{

	private GridSettings settings;
	private int x, y;

	private List<GridItem> items;

	/**
	 * @param x        xPosition (must not include indentations)
	 * @param y        yPosition (must not include indentations)
	 * @param settings settings of the Grid
	 */
	public Grid(GridSettings settings, int x, int y)
	{
		this.settings = settings;
		this.x = x;
		this.y = y;
		this.items = new ArrayList<GridItem>();
	}

	public void addGridItem(GridItem item, int row)
	{
		item.setRowPosition(row);
		if (items.contains(item))
			return;
		items.add(item);
	}

	public List<GridItem> updateItems()
	{
		List<GridItem> items = new ArrayList<>();
		int yPos = y + settings.getGap();
		for (int i = 0; i < settings.getRows(); i++)
		{
			List<GridItem> itemsRow = getItemsOnRow(i);
			int height = getMaxHeightOnRow(itemsRow);
			height = (height == -1 ? settings.getItemHeight() : height);

			int xPos = x + settings.getGap();
			for (GridItem item : itemsRow)
			{
				item.updateSize(xPos, yPos, settings.getItemWidth(), height, settings.getGap());
				xPos += item.getWidth(settings.getItemWidth(), settings.getGap()) + settings.getGap() * 2;
				items.add(item);
			}
			yPos += height;
		}
		return items;
	}

	public List<GridItem> getItemsOnRow(int row)
	{
		List<GridItem> items = new ArrayList<GridItem>();
		for (GridItem item : this.items)
		{
			if (item.getRowPosition() == row)
			{
				items.add(item);
			}
		}

		return items;
	}

	public int getMaxHeightOnRow(List<GridItem> row)
	{
		int height = -1;
		for (GridItem item : row)
		{
			if (item.getFixedHeight() == -1)
				continue;
			if (item.getFixedHeight() > height)
				height = item.getFixedHeight();
		}
		return height;
	}
}
