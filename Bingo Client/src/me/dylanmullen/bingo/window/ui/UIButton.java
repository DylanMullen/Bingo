package me.dylanmullen.bingo.window.ui;

import javax.swing.JPanel;

public abstract class UIButton extends JPanel
{

	private static final long serialVersionUID = -5731949917159100728L;

	private String text;
	private int x, y;
	private int width, height;

	private boolean active;

	public UIButton(String text, int x, int y, int width, int height)
	{
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}

	public UIButton(String text)
	{
		this(text, 0, 0, 0, 0);
	}

	protected abstract void setup();

	public abstract UIButton create();

	public void init()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setOpaque(true);
	}

	public String getText()
	{
		return text;
	}

	public boolean isActive()
	{
		return active;
	}

	public UIButton setActive(boolean active)
	{
		this.active = active;
		return this;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

}
