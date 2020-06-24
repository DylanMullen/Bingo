package me.dylanmullen.bingo.game.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;

import me.dylanmullen.bingo.game.cards.generator.BingoNumberGenerator;
import me.dylanmullen.bingo.game.droplet.BingoDroplet.LineState;
import me.dylanmullen.bingo.game.user.User;

public class BingoCard
{

	private User user;
	private UUID uuid;

	private CardType cardType;
	private BingoRow[] rows;

	private boolean purchased;
	private boolean winner;

	public BingoCard(User user, CardType type)
	{
		this.user = user;
		this.cardType = type;
		this.uuid = UUID.randomUUID();
		this.rows = new BingoRow[type.getRows()];
	}

	public void generate(BingoNumberGenerator generator)
	{
		for (int i = 0; i < cardType.getRows(); i++)
		{
			BingoRow row = new BingoRow(generator.generateRow());
			rows[i] = row;
		}
	}

	public void markNumber(int number)
	{
		for (BingoRow row : rows)
		{
			if (row.markNumber(number))
				return;
		}
	}

	public boolean isWinner(LineState state)
	{
		int temp = 0;
		for (BingoRow row : rows)
		{
			if (row.isComplete())
				temp++;
		}
		this.winner = temp == state.getLinesRequired();
		return winner;
	}

	public boolean hasWon()
	{
		return winner;
	}

	public List<Integer> getNumbers()
	{
		List<Integer> numbers = new ArrayList<>();
		for (BingoRow row : rows)
		{
			for (int i = 0; i < row.getNumbers().length; i++)
				numbers.add(row.getNumbers()[i]);
		}
		return numbers;
	}

	public JSONArray getCardNumbers()
	{
		JSONArray array = new JSONArray();
		for (BingoRow row : rows)
			for (int i = 0; i < row.getNumbers().length; i++)
				array.add(row.getNumbers()[i]);

		return array;
	}

	public void setPurchased(boolean purchased)
	{
		this.purchased = purchased;
	}

	public boolean isPurchased()
	{
		return purchased;
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public User getOwner()
	{
		return user;
	}
}
