package me.dylanmullen.bingo.game;

import java.util.ArrayList;

import me.dylanmullen.bingo.game.user.User;

public class CardGroup
{

	private User owner;
	private long seed;
	private ArrayList<BingoCard> cards;

	public CardGroup(long seed, User user)
	{
		this.owner = user;
		this.seed = seed;
		this.cards = new ArrayList<BingoCard>();
	}

	public void generate()
	{
		for (int i = 0; i < 3; i++)
		{
			BingoCard card = new BingoCard(owner, seed);
			card.generate();
			cards.add(card);
		}
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("/c/");
		for (int i = 0; i < cards.size(); i++)
		{
			BingoCard card = cards.get(i);
			sb.append(card.toString());
			sb.append("/c/");
		}

		return sb.toString();
	}

}
