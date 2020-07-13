package me.dylanmullen.bingo.game.cards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.cards.generator.Ball75Generator;
import me.dylanmullen.bingo.game.cards.generator.Ball90Generator;
import me.dylanmullen.bingo.game.cards.generator.BingoNumberGenerator;
import me.dylanmullen.bingo.game.droplet.BingoDroplet.LineState;
import me.dylanmullen.bingo.game.user.User;

public class BingoCardGroup
{

	private User owner;
	private UUID uuid;
	private List<BingoCard> cards;
	private int numberToGenerate;
	private BingoNumberGenerator generator;

	public BingoCardGroup(User owner, int numberToGenerate, int maxNumbers)
	{
		this.owner = owner;
		this.cards = new ArrayList<BingoCard>();
		this.numberToGenerate = numberToGenerate;
		setGenerator(maxNumbers);
	}

	@SuppressWarnings("deprecation")
	private void setGenerator(int numbers)
	{
		this.uuid = UUID.randomUUID();
		switch (numbers)
		{
			case 75:
				this.generator = new Ball75Generator(uuid);
				break;
			default:
				this.generator = new Ball90Generator(uuid);
				break;
		}
	}

	public void generateCards()
	{
		for (int i = 0; i < numberToGenerate; i++)
		{
			BingoCard card = new BingoCard(owner, generator.getType());
			card.generate(generator);
			cards.add(card);
		}
	}

	public void markNumber(int number)
	{
		for (BingoCard card : getPurchasedCards())
		{
			card.markNumber(number);
		}
	}

	public BingoCard getCard(UUID uuid)
	{
		for (BingoCard card : cards)
			if (card.getUUID().toString().equalsIgnoreCase(uuid.toString()))
				return card;
		return null;
	}

	public BingoCard getCard()
	{
		return getPurchasedCards().get(0);
	}

	public Set<BingoCard> getWinningCards()
	{
		Set<BingoCard> winners = new HashSet<BingoCard>();
		for (BingoCard cards : getPurchasedCards())
			if (cards.hasWon())
				winners.add(cards);
		return winners;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getCardsJSON()
	{
		JSONObject object = new JSONObject();
		for (BingoCard card : cards)
		{
			object.put(card.getUUID().toString(), card.getCardNumbers());
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public JSONArray getPurchasedCardsArray()
	{
		JSONArray object = new JSONArray();
		for (BingoCard card : getPurchasedCards())
		{
			object.add(card.getUUID().toString());
		}
		return object;
	}

	public List<BingoCard> getPurchasedCards()
	{
		List<BingoCard> purchased = new ArrayList<BingoCard>();
		for (BingoCard card : cards)
		{
			if (card.isPurchased())
				purchased.add(card);
		}
		return purchased;
	}

	public boolean hasPurchasedCards()
	{
		for (BingoCard card : cards)
		{
			if (card.isPurchased())
				return true;
		}
		return false;
	}

	public boolean hasWinners(LineState state)
	{
		for (BingoCard card : getPurchasedCards())
			if (card.isWinner(state))
				return true;
		return false;
	}

	public User getOwner()
	{
		return owner;
	}

}
