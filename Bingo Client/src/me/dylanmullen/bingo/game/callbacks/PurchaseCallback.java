package me.dylanmullen.bingo.game.callbacks;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.droplet.cardevents.CardPurchasedEvent;
import me.dylanmullen.bingo.net.packet.PacketCallback;

public class PurchaseCallback extends PacketCallback
{

	@Override
	public boolean callback()
	{
		EventHandler.fireEvent(new CardPurchasedEvent(getDropletUUID(), getMessage()));
		return false;
	}

}
