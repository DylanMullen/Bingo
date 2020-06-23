package me.dylanmullen.bingo.events.events.droplet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;

public class DropletStartingEvent extends DropletEvent
{

	private List<UUID> purchasedCards;

	public DropletStartingEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);

		this.purchasedCards = new ArrayList<>();

		for (Object object : (JSONArray) message.get("purchasedCards"))
		{
			UUID uuid = UUID.fromString((String) object);
			purchasedCards.add(uuid);
		}

	}

	public List<UUID> getPurchasedCards()
	{
		return purchasedCards;
	}
}
