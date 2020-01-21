package me.dylanmullen.bingo.game;

import java.util.Random;

public class BingoCard
{

	private BingoRow[] rows;
	private Random random;

	public BingoCard()
	{
		this.rows = new BingoRow[3];
		this.random = new Random();

		for (int i = 0; i < 3; i++)
			this.rows[i] = new BingoRow();

		this.random.setSeed(System.currentTimeMillis());
	}

	public void generate()
	{
		for (int i = 0; i < rows.length; i++)
		{
			BingoRow row = rows[i];
			for (int j = 0; j < 5; j++)
			{
				int number = getRandomNumber(row);
				row.addNumber(number);
			}
			row.complete();
		}
		print();
	}

	private void print()
	{
		for (int i = 0; i < rows.length; i++)
		{
			BingoRow row = rows[i];
			for (int j = 0; j < row.getNumbers().length; j++)
			{
				int num = row.getNumbers()[j];
				if (num == -1)
					continue;
				System.out.print(num + "\t");
			}
			System.out.println("");
		}
		System.out.println(toString());
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < rows.length; i++)
		{
			BingoRow row = rows[i];
			sb.append(row.toString() + (rows.length - 1 == i ? "" : "/"));
		}
		return sb.toString();
	}

	public int getRandomNumber(BingoRow row)
	{
		int number = random.nextInt(90) + 1;

		if (row.isGroupSet(number))
			return getRandomNumber(row);
		if (isNumber(number))
			return getRandomNumber(row);

		return number;
	}

	public boolean isNumber(int number)
	{
		for (int i = 0; i < rows.length; i++)
		{
			BingoRow row = rows[i];
			if (row.containsNumber(number))
				return true;
		}
		return false;
	}
}
