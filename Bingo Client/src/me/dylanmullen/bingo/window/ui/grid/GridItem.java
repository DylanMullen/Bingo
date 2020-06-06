package me.dylanmullen.bingo.window.ui.grid;

import javax.swing.JComponent;

public class GridItem
{

	private JComponent comp;
	private double rowSpan, colSpan;
	private int rowPosition;
	private int fixedHeight;

	public GridItem(JComponent comp, double rowSpan, double colSpan)
	{
		this.comp = comp;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.fixedHeight = -1;
		this.rowPosition = -1;
	}

	public GridItem(JComponent comp, double rowSpan, double colSpan, int fixedHeight)
	{
		this.comp = comp;
		this.rowSpan = rowSpan;
		this.colSpan = colSpan;
		this.fixedHeight = fixedHeight;
		this.rowPosition = -1;
	}

	public void updateSize(int x, int y, int width, int height, int gap)
	{
		comp.setBounds(x, y, getWidth(width, gap), getHeight(height, gap));
		if (comp instanceof IGridItem)
			((IGridItem) comp).resizeComponents();
	}

	public int getWidth(int width, int gap)
	{
		return (int) (width * colSpan) - gap * 2;
	}

	public int getHeight(int height, int gap)
	{
		return (int) (height * rowSpan) - gap * 2;
	}

	public void setRowPosition(int rowPosition)
	{
		this.rowPosition = rowPosition;
	}

	public int getRowPosition()
	{
		return rowPosition;
	}
	
	public int getFixedHeight()
	{
		return fixedHeight;
	}

	public JComponent getComponent()
	{
		return comp;
	}
}
