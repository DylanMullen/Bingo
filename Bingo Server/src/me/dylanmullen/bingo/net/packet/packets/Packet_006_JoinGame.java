package me.dylanmullen.bingo.net.packet.packets;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class Packet_006_JoinGame extends Packet
{

	public Packet_006_JoinGame(Client c, JSONObject message)
	{
		super(6, c, message);
	}

	@Override
	public void handle()
	{
		User u = UserManager.getInstance().getUser(getSender());
		Packet packet = PacketHandler.createPacket(getSender(), 5, null);

		if (u.getCurrentGame() != null)
		{
			JSONObject object = constructMessage(500);
			set(object, "errorMessage", "Already in Game");
			packet.setMessageSection(object);
			packet.setPacketUUID(getPacketUUID());
			PacketHandler.sendPacket(packet);
			return;
		}

		BingoGame game = BingoServer.getInstance().getGame().placeUser(u);

		JSONObject object = constructMessage(500);
		set(object, "gameUUID", game.getGameUUID().toString());
		set(object, "gameState", game.getGameState());
		
		packet.setMessageSection(object);
		packet.setPacketUUID(getPacketUUID());
		
		PacketHandler.sendPacket(packet);
	}

	private JSONObject constructMessage(int responseCode)
	{
		JSONObject data = new JSONObject();
		set(data, "responseType", responseCode);
		return data;
	}

	@SuppressWarnings("unchecked")
	private void set(JSONObject object, String key, Object value)
	{
		object.put(key, value);
	}

}
