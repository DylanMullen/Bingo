package me.dylanmullen.bingo.gfx.ui.grid;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class GridSettings
{

	private int width, height;
	private int rows, cols;
	private int gap;
	private int fixedRowHeight;

	public GridSettings(int width, int height, int row, int cols, int gap)
	{
		this.width = width;
		this.height = height;
		this.rows = row;
		this.cols = cols;
		this.gap = gap;
		this.fixedRowHeight = -1;
	}

	/**
	 * @return height without gaps
	 */
	public int getItemHeight(int size)
	{
		if (fixedRowHeight != -1)
			return fixedRowHeight;
		return height / getRows(size);
	}

	/**
	 * @return width without gaps
	 */
	public int getItemWidth()
	{
		return width / cols;
	}

	public int getGap()
	{
		return gap;
	}

	public int getRows(int size)
	{
		if (rows == -1 && size != -1)
		{
			rows = size / getCols();
			rows = (rows == 0 ? 1 : rows);
		}
		return rows;
	}

	public int getCols()
	{
		return cols;
	}
	
	public void setFixedRowHeight(int fixedRowHeight)
	{
		this.fixedRowHeight = fixedRowHeight;
	}

}
