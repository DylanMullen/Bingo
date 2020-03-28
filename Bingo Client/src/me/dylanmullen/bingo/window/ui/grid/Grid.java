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

	public void updateItems()
	{
		int yPos = settings.getGap();
		for (int i = 0; i < settings.getRows(); i++)
		{
			List<GridItem> itemsRow = getItemsOnRow(i);
			int xPos = settings.getGap();
			for (GridItem item : itemsRow)
			{
				item.updateSize(xPos, yPos, settings.getItemWidth(), settings.getItemHeight(), settings.getGap());
				xPos += item.getWidth(settings.getItemWidth(), settings.getGap()) + settings.getGap() * 2;
			}
			yPos += settings.getItemHeight();
		}
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
}
