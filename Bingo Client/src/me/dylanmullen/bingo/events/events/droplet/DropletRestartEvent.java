package me.dylanmullen.bingo.events.events.droplet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.game.cards.CardInformation;

public class DropletRestartEvent extends DropletEvent
{

	private List<CardInformation> cards;

	public DropletRestartEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.cards = new ArrayList<>();
		for (Object obj : ((JSONObject) message.get("cards")).keySet())
		{
			UUID uuid = UUID.fromString((String) obj);
			JSONArray cardNumbers = (JSONArray) ((JSONObject) message.get("cards")).get(obj);
			cards.add(new CardInformation(uuid, cardNumbers));
		}
	}

	public List<CardInformation> getCards()
	{
		return cards;
	}

}
