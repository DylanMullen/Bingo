package me.dylanmullen.bingo.net.packet.packets;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.game.GameController;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response.ResponseType;

public class Packet_006_JoinGame extends Packet
{

	public Packet_006_JoinGame(Client c, String message)
	{
		super(006, c, message, false);
	}

	@Override
	public void handle()
	{
		User u = UserManager.getInstance().getUser(getClient());
		Packet_005_Response res = (Packet_005_Response) PacketHandler.createPacket(getClient(), 005, "");

		if (u.getCurrentGame() != null)
		{
			res.constructMessage(ResponseType.FAILURE, "Already in game", getUUID());
			PacketHandler.sendPacket(res, null);
			return;
		}

		BingoGame game = GameController.getInstance().placeUser(u);
		res.constructMessage(ResponseType.SUCCESS, game.getGameUUID().toString(), getUUID());
		PacketHandler.sendPacket(res, null);
	}

}
