package me.dylanmullen.bingo.game;

import java.util.ArrayList;
import java.util.UUID;

import me.dylanmullen.bingo.game.user.User;

public class CardGroup
{

	private User owner;
	private ArrayList<BingoCard> cards;

	public CardGroup(User user)
	{
		this.owner = user;
		this.cards = new ArrayList<BingoCard>();
	}

	public void generate()
	{
		for (int i = 0; i < 3; i++)
		{
			BingoCard card = new BingoCard(owner);
			card.generate();
			cards.add(card);
		}
	}
	
	public BingoCard getCard(User user)
	{
		for (BingoCard bingoCard : cards)
		{
			if(bingoCard.getOwner().equals(user))
				return bingoCard;
		}
		return null;
	}
	
	public boolean remove(BingoCard card)
	{
		cards.remove(card);
		return cards.isEmpty();
	}
	
	public void addCard(BingoCard card)
	{
		cards.add(card);
	}
	
	public User getOwner()
	{
		return owner;
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
