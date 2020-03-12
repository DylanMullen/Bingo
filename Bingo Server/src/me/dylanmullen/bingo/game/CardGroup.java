package me.dylanmullen.bingo.game;

import java.util.ArrayList;
import java.util.UUID;

import me.dylanmullen.bingo.game.BingoGame.LineState;
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

	public BingoCard getCard(UUID uuid)
	{
		for (BingoCard bingoCard : cards)
		{
			if (uuid.equals(bingoCard.getUuid()))
				return bingoCard;
		}
		return null;
	}

	public int[] firstNumbers()
	{
		BingoCard card = cards.get(0);
		return card.getNumbers();
	}

	public boolean checkWinner(LineState state, ArrayList<Integer> numbers)
	{
		boolean status = false;
		for (BingoCard card : cards)
		{
			status = card.isWinner(state, numbers);
			if (status)
				return status;
		}
		return status;
	}

	public ArrayList<BingoCard> getWinningCards(LineState state, ArrayList<Integer> numbers)
	{
		ArrayList<BingoCard> winners = new ArrayList<BingoCard>();
		for (BingoCard card : cards)
		{
			if (card.isWinner(state, numbers))
				winners.add(card);
		}
		return winners;
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

	public String getCardUUIDs()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cards.size(); i++)
		{
			BingoCard card = cards.get(i);
			sb.append(card.getUuid().toString() + (cards.size() - 1 == i ? "" : "/nl/"));
		}
		return sb.toString();
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
