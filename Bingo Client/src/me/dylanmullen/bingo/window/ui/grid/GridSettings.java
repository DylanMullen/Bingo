package me.dylanmullen.bingo.window.ui.grid;

public class GridSettings
{

	private int width, height;
	private int rows, cols;
	private int gap;

	public GridSettings(int w, int h, int r, int c, int g)
	{
		this.width = w;
		this.height = h;
		this.rows = r;
		this.cols = c;
		this.gap = g;
	}

	/**
	 * @return height without gaps
	 */
	public int getItemHeight()
	{
		return height / rows;
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
	
	public int getRows()
	{
		return rows;
	}
	
	public int getCols()
	{
		return cols;
	}

}
