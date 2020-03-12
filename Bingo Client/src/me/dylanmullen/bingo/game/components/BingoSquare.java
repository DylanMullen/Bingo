package me.dylanmullen.bingo.game.components;

import java.awt.Rectangle;

public class BingoSquare extends Rectangle
{

	private static final long serialVersionUID = 782016313217689471L;

	private int number=-1;
	private boolean called;

	public BingoSquare(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		setCalled(false);
		this.number = number;
	}

	public boolean isCalled()
	{
		return called;
	}

	public void setCalled(boolean called)
	{
		this.called = called;
	}

	public boolean isEmpty()
	{
		return number == -1;
	}

}
