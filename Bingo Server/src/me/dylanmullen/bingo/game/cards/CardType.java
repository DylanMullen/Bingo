package me.dylanmullen.bingo.game.cards;

public enum CardType
{

	BALL_90(90, 3, 9), BALL_75(75, 5, 5);

	private int numbers;
	private int rows, cols;

	private CardType(int numbers, int rows, int cols)
	{
		this.numbers = numbers;
		this.rows = rows;
		this.cols = cols;
	}

	public int getColumns()
	{
		return cols;
	}

	public int getRows()
	{
		return rows;
	}

	public int getMaxNumbers()
	{
		return numbers;
	}

	public int getColumnByNumber(int number)
	{
		int column = number / (numbers / cols);
		column -= (number == numbers ? 1 : 0);
		return column;
	}

}
