package me.dylanmullen.bingo.game.components;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class NumbersCalledComponent extends Panel
{

	private static final long serialVersionUID = 4164051545883107133L;

	public NumbersCalledComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	private NumberGrid[] numbers;

	private int gridSize;

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		this.numbers = new NumberGrid[5];
		this.gridSize = (getHeight() - 24);
	}

	@Override
	public void create()
	{
		int indentY = 12;
		int indentX = (getWidth() - ((gridSize + 12) * numbers.length)) / 2;
		for (int i = 0; i < numbers.length; i++)
		{
			numbers[i] = new NumberGrid(indentX, indentY, gridSize, gridSize);
			numbers[i].setup();
			indentX += gridSize + 12;
			numbers[i].updateCurrentNumber(i);
			add(numbers[i]);
		}
		numbers[0].setFirst(true);

	}

}
