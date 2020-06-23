package me.dylanmullen.bingo.events.events.droplet.cardevents;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;

public class CardPurchasedEvent extends DropletEvent
{

	private UUID cardUUID;

	public CardPurchasedEvent(UUID dropletUUID, JSONObject message)
	{
		super(dropletUUID);
		this.cardUUID = UUID.fromString((String) message.get("purchasedCard"));
	}

	public UUID getCardUUID()
	{
		return cardUUID;
	}
}
