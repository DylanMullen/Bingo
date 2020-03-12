package me.dylanmullen.bingo.game;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import me.dylanmullen.bingo.game.BingoGame.LineState;
import me.dylanmullen.bingo.game.user.User;

public class BingoCard
{

	private BingoRow[] rows;
	private Random random;
	private User owner;

	private UUID uuid;

	public BingoCard(User user)
	{
		this.owner = user;
		this.rows = new BingoRow[3];
		this.random = new Random();

		for (int i = 0; i < 3; i++)
			this.rows[i] = new BingoRow();

		this.random = new Random();
		this.uuid = UUID.randomUUID();
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
		sb.append("/u/" + uuid.toString());
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

	public boolean isWinner(LineState state, ArrayList<Integer> numbers)
	{
		int required = 0;

		switch (state)
		{
			case ONE:
				required = 1;
				break;
			case TWO:
				required = 2;
				break;
			case FULLHOUSE:
				required = 3;
				break;
			default:
				break;
		}

		if (required == 0)
			return false;

		int temp = 0;
		for (BingoRow row : rows)
		{
			if (row.isFinished(numbers))
				temp++;
		}

		return temp == required;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public User getOwner()
	{
		return owner;
	}

	public int[] getNumbers()
	{
		int[] numbers = new int[15];
		int index = 0;
		for(BingoRow row : rows)
		{
			if(index > 14)
				break;
			for(int x =0; x < row.getNumbers().length;x++)
			{
				int j = row.getNumbers()[x];
				numbers[index]=j;
				index++;
			}
		}
		return numbers;
	}

}
