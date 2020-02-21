package me.dylanmullen.bingo.window.ui;

import javax.swing.JPanel;

public abstract class Panel extends JPanel
{

	private static final long serialVersionUID = -3182799998146452612L;

	protected int x, y;
	protected int width, height;

	public Panel(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		setLayout(null);
	}

	public abstract void setup();

	public abstract void create();

}
