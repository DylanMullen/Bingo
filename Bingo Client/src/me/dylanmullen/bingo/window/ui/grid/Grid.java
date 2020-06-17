package me.dylanmullen.bingo.window.ui.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid
{

	private GridSettings settings;
	private int x, y;

	private Map<Integer, List<GridItem>> itemsMap;

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
		this.itemsMap = new HashMap<>();
	}

	public void addGridItem(GridItem item, int row, boolean update)
	{
		if (!itemsMap.containsKey(row))
			itemsMap.put(row, new ArrayList<GridItem>());

		List<GridItem> items = itemsMap.get(row);
		items.add(item);

		if (update)
			updateBounds(row, -1);
	}

	public void addGridItem(GridItem item, int row, boolean update, boolean debug)
	{
		if (!itemsMap.containsKey(row))
			itemsMap.put(row, new ArrayList<GridItem>());

		List<GridItem> items = itemsMap.get(row);
		items.add(item);

		if (debug)
			System.out.println(row);

		if (update)
			updateBounds(row, -1);
	}

	public void addGridItem(GridItem item, int row)
	{
		addGridItem(item, row, true);
	}

	public void setGridItems(int row, List<GridItem> itemList)
	{
		itemsMap.put(row, itemList);
		updateBounds(row, -1);
	}

	public void updateBounds(int row, int size)
	{
		List<GridItem> items = itemsMap.get(row);
		int height = settings.getItemHeight(size);
		int xPos = x + settings.getGap();
		int yPos = y + settings.getGap() + (row * height);
		for (GridItem item : items)
		{
			if (item.getFixedHeight() != -1)
				height = item.getFixedHeight();
			item.updateSize(xPos, yPos, settings.getItemWidth(), height, settings.getGap());
			xPos += item.getWidth(settings.getItemWidth(), settings.getGap()) + settings.getGap() * 2;
		}
	}

	public void updatePositions()
	{
		int size = getItems().size();
		for (int i = 0; i < itemsMap.keySet().size(); i++)
		{
			updateBounds(i, size);
		}
	}

	public List<GridItem> getItems()
	{
		List<GridItem> items = new ArrayList<>();
		for (List<GridItem> i : itemsMap.values())
			for (GridItem item : i)
				items.add(item);
		return items;
	}

	public GridSettings getSettings()
	{
		return settings;
	}

}
