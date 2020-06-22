package me.dylanmullen.bingo.game.cards.generator;

import java.util.UUID;

import me.dylanmullen.bingo.game.cards.CardType;

public class Ball90Generator extends BingoNumberGenerator
{

	public Ball90Generator(UUID groupUUID)
	{
		super(groupUUID, CardType.BALL_90);
	}

	@Override
	public int[] generateRow()
	{
		int[] temp = new int[getType().getColumns()];
		for (int i = 0; i < 5; i++)
		{
			int number = getNumber(temp);
			temp[getType().getColumnByNumber(number)] = number;
		}
		return trim(temp);
	}

	private int[] trim(int[] numbers)
	{
		int[] trim = new int[5];
		int trimPointer = 0;
		for (int i = 0; i < numbers.length; i++)
			if (numbers[i] != 0)
			{
				trim[trimPointer] = numbers[i];
				trimPointer++;
			}
		return trim;
	}
}
