package me.dylanmullen.bingo.window.ui.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class Grid
{

	private GridSettings gridSettings;
	private int x, y;

	private Map<Integer, List<GridItem>> gridItems;

	/**
	 * This creates a Grid system based on the settings parsed through the
	 * constructor.
	 * 
	 * @param settings The settings of the Grid System.
	 * @param x        X-Position of the Grid
	 * @param y        Y-Position of the Grid
	 */
	public Grid(GridSettings settings, int x, int y)
	{
		this.gridSettings = settings;
		this.x = x;
		this.y = y;
		this.gridItems = new HashMap<>();
	}

	/**
	 * Adds a GridItem to the System.
	 * 
	 * @param gridItem The {@link GridItem} to add to the Grid System.
	 * @param row      The row that the GridItem should be placed on.
	 * @param update   Should the items bounds on the row be updated.
	 */
	public void addGridItem(GridItem gridItem, int row, boolean update)
	{
		if (!getGridItems().containsKey(row))
			getGridItems().put(row, new ArrayList<GridItem>());

		List<GridItem> items = getGridItems().get(row);
		items.add(gridItem);

		if (update)
			updateBounds(row, -1);
	}

	/**
	 * Adds a GridItem to the System. <br>
	 * This method will update the items on the row.
	 * 
	 * @param gridItem The {@link GridItem} to add to the Grid System.
	 * @param row      The row that the GridItem should be placed on.
	 */
	public void addGridItem(GridItem item, int row)
	{
		addGridItem(item, row, true);
	}

	/**
	 * Sets a full row of the Grid System.
	 * 
	 * @param row      The row that the Grid Items should be set on.
	 * @param itemList The GridItems that the row will have.
	 */
	public void setGridItems(int row, List<GridItem> itemList)
	{
		getGridItems().put(row, itemList);
		updateBounds(row, -1);
	}

	/**
	 * Updates the bounds of all the items on a row.
	 * 
	 * @param row       The row to update.
	 * @param valueSize The size of the values in the Grid System.
	 */
	public void updateBounds(int row, int valueSize)
	{
		List<GridItem> items = getGridItems().get(row);
		int height = getGridSettings().getItemHeight(valueSize);
		int xPos = getX() + getGridSettings().getGap();
		int yPos = getY() + getGridSettings().getGap() + (row * height);
		for (GridItem item : items)
		{
			if (item.getFixedHeight() != -1)
				height = item.getFixedHeight();
			item.updateSize(xPos, yPos, getGridSettings().getItemWidth(), height, getGridSettings().getGap());
			xPos += item.getWidth(getGridSettings().getItemWidth(), getGridSettings().getGap())
					+ getGridSettings().getGap() * 2;
		}
	}

	/**
	 * Updates the bounds of all the items in the Grid System.
	 */
	public void updatePositions()
	{
		int valueSize = getItems().size();
		for (int i = 0; i < getGridItems().keySet().size(); i++)
		{
			updateBounds(i, valueSize);
		}
	}

	/**
	 * Returns a list of all the Grid Items that the Grid System will need to
	 * display.
	 * 
	 * @return {@link List}
	 */
	public List<GridItem> getItems()
	{
		List<GridItem> items = new ArrayList<>();
		for (List<GridItem> i : getGridItems().values())
			for (GridItem item : i)
				items.add(item);
		return items;
	}

	/**
	 * Returns the Grid System settings.
	 * 
	 * @return {@link #gridSettings}
	 */
	public GridSettings getGridSettings()
	{
		return this.gridSettings;
	}

	/**
	 * Returns the Grid System item map.
	 * 
	 * @return {@link #gridItems}
	 */
	public Map<Integer, List<GridItem>> getGridItems()
	{
		return this.gridItems;
	}

	/**
	 * Returns the X-Position of the Grid System.
	 * 
	 * @return {@link #x}
	 */
	public int getX()
	{
		return this.x;
	}

	/**
	 * Returns the Y-Position of the Grid System.
	 * 
	 * @return {@link #y}
	 */
	public int getY()
	{
		return this.y;
	}
}
