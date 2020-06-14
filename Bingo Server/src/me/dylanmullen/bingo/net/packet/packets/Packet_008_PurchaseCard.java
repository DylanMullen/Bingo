package me.dylanmullen.bingo.net.packet.packets;

import java.util.UUID;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;

public class Packet_008_PurchaseCard extends Packet
{

	// /m/uuid/m/
	public Packet_008_PurchaseCard(Client c, String message)
	{
		super(8, c, message, false);
	}

	@Override
	public void handle()
	{
		User u = UserManager.getInstance().getUser(getClient());
		UUID uuid = UUID.fromString(getMessage());
		BingoServer.getInstance().getGame().purchaseCard(u, uuid, getUUID());
	}

}
