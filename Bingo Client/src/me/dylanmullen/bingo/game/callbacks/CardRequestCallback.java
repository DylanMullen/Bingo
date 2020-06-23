package me.dylanmullen.bingo.game.callbacks;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.droplet.cardevents.CardsRecievedEvent;
import me.dylanmullen.bingo.net.packet.PacketCallback;

public class CardRequestCallback extends PacketCallback
{

	@Override
	public boolean callback()
	{
		if(getResponseType()==200)
			EventHandler.fireEvent(new CardsRecievedEvent(getDropletUUID(), getMessage()));
		return false;
	}

}
