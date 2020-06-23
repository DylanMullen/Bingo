package me.dylanmullen.bingo.events.events.droplet.cardevents;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.game.cards.CardInformation;

public class CardsRecievedEvent extends DropletEvent
{

	private List<CardInformation> cards;

	public CardsRecievedEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.cards = new ArrayList<>();

		for (Object obj : message.keySet())
		{
			if (((String) obj).equalsIgnoreCase("dropletUUID"))
				continue;

			UUID uuid = UUID.fromString((String) obj);
			JSONArray cardNumbers = (JSONArray) message.get(obj);
			cards.add(new CardInformation(uuid, cardNumbers));
		}
	}

	public List<CardInformation> getCards()
	{
		return cards;
	}

}
