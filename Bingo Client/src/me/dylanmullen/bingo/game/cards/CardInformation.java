package me.dylanmullen.bingo.game.cards;

import java.util.UUID;

import org.json.simple.JSONArray;

public class CardInformation
{

	private UUID uuid;
	private int[] numbers;
	private boolean purchased;
	private boolean selected;

	public CardInformation(UUID uuid, JSONArray numbers)
	{
		this.uuid = uuid;
		this.numbers = new int[numbers.size()];
		this.purchased = false;
		this.selected = false;

		for (int i = 0; i < numbers.size(); i++)
		{
			this.numbers[i] = ((Number) numbers.get(i)).intValue();
		}
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public int[] getNumbers()
	{
		return numbers;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public boolean isPurchased()
	{
		return purchased;
	}

	public void setPurchased(boolean purchased)
	{
		this.purchased = purchased;
	}
	
	public void setUUID(UUID uuid)
	{
		this.uuid = uuid;
	}

}
