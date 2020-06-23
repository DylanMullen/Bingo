package me.dylanmullen.bingo.game.callbacks;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.droplet.DropletJoinEvent;
import me.dylanmullen.bingo.net.packet.PacketCallback;

public class JoinCallback extends PacketCallback
{

	@Override
	public boolean callback()
	{
		EventHandler.fireEvent(new DropletJoinEvent(getDropletUUID(), getMessage()));
		return false;
	}

}
