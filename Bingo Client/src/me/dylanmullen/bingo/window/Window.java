package me.dylanmullen.bingo.window;

import java.awt.Dimension;

import javax.swing.JFrame;

public abstract class Window extends JFrame
{

	private static final long serialVersionUID = 1L;

	private int width, height;
	private boolean setup;
	
	public Window(String title)
	{
		super(title);
		this.width = 1280;
		this.height = 720;
	}

	public Window(String title, int width, int height)
	{
		this(title);
		this.width = width;
		this.height = height;
	}
	
	public void setup()
	{
		if(setup)
			return;
		setSize(width, height);
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	/**
	 * Tool to help align children components
	 * @return
	 */
	protected Dimension getMidPoint()
	{
		return new Dimension(getX()/2, getY()/2);
	}
}
