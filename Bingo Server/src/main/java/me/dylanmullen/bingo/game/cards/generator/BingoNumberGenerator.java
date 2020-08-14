package me.dylanmullen.bingo.game.cards.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import me.dylanmullen.bingo.game.cards.CardType;

public abstract class BingoNumberGenerator
{

	private UUID groupUUID;

	private Random random;
	private CardType type;

	private List<Integer> excludedNumbers;

	public BingoNumberGenerator(UUID groupUUID, CardType type)
	{
		this.groupUUID = groupUUID;
		this.random = new Random();
		this.type = type;
		this.excludedNumbers = new ArrayList<Integer>();
		setSeed();
	}

	public abstract int[] generateRow();

	// TODO string seed
	private void setSeed()
	{
		random.setSeed(System.currentTimeMillis());
	}

	public int getNumber(int[] numbers)
	{
		int number = random.nextInt(type.getMaxNumbers()) + 1;
		int column = type.getColumnByNumber(number);
		if (numbers[column] != 0)
			return getNumber(numbers);
		if (excludedNumbers.contains(number))
			return getNumber(numbers);

		addExcludedNumber(number);
		return number;
	}

	private void addExcludedNumber(int number)
	{
		if (excludedNumbers.size() == type.getMaxNumbers())
			excludedNumbers.clear();
		excludedNumbers.add(number);
	}

	public CardType getType()
	{
		return type;
	}

}
