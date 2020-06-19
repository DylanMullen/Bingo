package me.dylanmullen.bingo.window.ui.grid;

import javax.swing.JComponent;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class GridItem
{

	private JComponent component;
	private double rowSpan, colSpan;
	private int rowPosition;
	private int fixedHeight;

	/**
	 * Creates a Grid Item for the Grid System. This holds data for the Grid Item
	 * such as how many rows it should take up as well as how many columns.
	 * 
	 * @param component The component this Grid Item relates to.
	 * @param rowSpan   The amount of rows this should span.
	 * @param colSpan   The amount of columns this should span.
	 */
	public GridItem(JComponent component, double rowSpan, double colSpan)
	{
		this.component = component;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.fixedHeight = -1;
		this.rowPosition = -1;
	}

	/**
	 * Creates a Grid Item for the Grid System. This holds data for the Grid Item
	 * such as how many rows it should take up as well as how many columns.
	 * 
	 * @param component   The component this Grid Item relates to.
	 * @param rowSpan     The amount of rows that the Item should span.
	 * @param colSpan     The amount of columns that the Item should span.
	 * @param fixedHeight A fixed height of the item.
	 */
	public GridItem(JComponent component, double rowSpan, double colSpan, int fixedHeight)
	{
		this(component, rowSpan, colSpan);
		this.fixedHeight = fixedHeight;
		this.rowPosition = -1;
	}

	/**
	 * Updates the bounds of the component of the Grid Item.
	 * 
	 * @param x      X-Position of the Component.
	 * @param y      Y-Position of the Component.
	 * @param width  The width of the Component.
	 * @param height The height of the Component.
	 * @param indent The indentation of the y and x.
	 */
	public void updateSize(int x, int y, int width, int height, int indent)
	{
		getComponent().setBounds(x, y, getWidth(width, indent), getHeight(height, indent));
	}

	/**
	 * Returns a calculated width of the Component using the width and the
	 * indentation.
	 * 
	 * @param width  The width of the Component.
	 * @param indent The indentation of the Component.
	 * @return {@link Integer}
	 */
	public int getWidth(int width, int indent)
	{
		return (int) (width * getColumnSpan()) - indent * 2;
	}

	/**
	 * Returns a calculated height of the Component using the height and the
	 * indentation.
	 * 
	 * @param width  The height of the Component.
	 * @param indent The indentation of the Component.
	 * @return {@link Integer}
	 */
	public int getHeight(int height, int indent)
	{
		return (int) (height * getRowSpan()) - indent * 2;
	}

	/**
	 * Sets the row position of the Item.
	 * 
	 * @param rowPosition The row position of the Item.
	 */
	public void setRowPosition(int rowPosition)
	{
		this.rowPosition = rowPosition;
	}

	/**
	 * Returns the row position of the Item
	 * 
	 * @return {@link #rowPosition}
	 */
	public int getRowPosition()
	{
		return this.rowPosition;
	}

	/**
	 * Returns the fixed height of the Item. Will return -1 if the fixed size has
	 * not been set up.
	 * 
	 * @return {@link #fixedHeight}
	 */
	public int getFixedHeight()
	{
		return this.fixedHeight;
	}

	/**
	 * Returns the component of the Item.
	 * 
	 * @return {@link #component}
	 */
	public JComponent getComponent()
	{
		return this.component;
	}

	/**
	 * Returns the amount of columns to span.
	 * 
	 * @return {@link #colSpan}
	 */
	public double getColumnSpan()
	{
		return this.colSpan;
	}

	/**
	 * Returns the amount of rows to span.
	 * 
	 * @return {@link #rowSpan}
	 */
	public double getRowSpan()
	{
		return this.rowSpan;
	}
}
