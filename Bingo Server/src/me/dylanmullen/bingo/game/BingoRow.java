package me.dylanmullen.bingo.game;

public class BingoRow
{

	private int[] numbers;

	public BingoRow()
	{
		this.numbers = new int[9];
		for (int i = 0; i < numbers.length; i++)
			this.numbers[i] = -1;
	}

	public void complete()
	{
		int[] temp = new int[5];
		int index = 0;
		for (int i = 0; i < numbers.length; i++)
		{
			if (numbers[i] == -1)
				continue;

			temp[index] = numbers[i];
			index++;
		}
		this.numbers = temp;
	}

	public void addNumber(int num)
	{
		numbers[getIndex(num)] = num;
	}

	public boolean containsNumber(int num)
	{
		for (int i = 0; i < numbers.length; i++)
			if (numbers[i] == num)
				return true;
		return false;
	}

	public boolean isGroupSet(int num)
	{
		if (numbers[getIndex(num)] != -1)
			return true;
		return false;
	}

	private int getIndex(int num)
	{
		return (num / 10 == 9 ? (num / 10) - 1 : num / 10);
	}

	public int[] getNumbers()
	{
		return this.numbers;
	}

	public boolean isSet()
	{
		for (int i = 0; i < numbers.length; i++)
			if (numbers[i] == -1)
				return false;
		return true;
	}

	public boolean isFinished(int[] nums)
	{
		for (int i = 0; i < numbers.length; i++)
		{
			int num = numbers[i];
			if (nums[num - 1] != 1)
				return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numbers.length; i++)
		{
			sb.append(numbers[i] + (numbers.length - 1 == i ? "" : "."));
		}
		return sb.toString();
	}

}
