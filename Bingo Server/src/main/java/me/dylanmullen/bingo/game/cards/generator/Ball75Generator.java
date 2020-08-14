package me.dylanmullen.bingo.game.cards.generator;

import java.util.UUID;

import me.dylanmullen.bingo.game.cards.CardType;

public class Ball75Generator extends BingoNumberGenerator
{

	@Deprecated
	public Ball75Generator(UUID groupUUID)
	{
		super(groupUUID, CardType.BALL_75);
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
		return temp;
	}

}
