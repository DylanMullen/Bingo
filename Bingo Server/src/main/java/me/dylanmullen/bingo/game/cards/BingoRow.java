package me.dylanmullen.bingo.game.cards;

public class BingoRow
{

	private int[] numbers;

	public BingoRow(int[] numbers)
	{
		setNumbers(numbers);
	}

	public boolean markNumber(int number)
	{
		if (!hasNumber(number))
			return false;
		numbers[getIndex(number)] = -1;
		return true;
	}

	private boolean hasNumber(int number)
	{
		for (int i = 0; i < numbers.length; i++)
			if (numbers[i] == number)
				return true;
		return false;
	}

	private int getIndex(int number)
	{
		for (int i = 0; i < numbers.length; i++)
			if (numbers[i] == number)
				return i;
		return -1;
	}

	public void setNumbers(int[] numbers)
	{
		this.numbers = numbers;
	}

	public int[] getNumbers()
	{
		return numbers;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numbers.length; i++)
			sb.append(numbers[i] + "\t");
		return sb.toString();
	}

	public boolean isComplete()
	{
		boolean status = true;
		for (int i = 0; i < getNumbers().length; i++)
			if (getNumbers()[i] != -1)
				status = false;
		return status;
	}
}
