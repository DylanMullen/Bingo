package me.dylanmullen.bingo.window.ui;

import javax.swing.JPanel;

public abstract class Panel extends JPanel
{

	private static final long serialVersionUID = -3182799998146452612L;

	protected int x, y;
	protected int width, height;

	public Panel(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		setLayout(null);
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		this.x = getX();
		this.y = getY();
		this.width = getWidth();
		this.height = getHeight();
	}

	public abstract void setup();

	public abstract void create();

}
