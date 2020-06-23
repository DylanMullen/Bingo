package me.dylanmullen.bingo.net;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.droplet.ChatMessageEvent;
import me.dylanmullen.bingo.events.events.droplet.CurrencyChangeEvent;
import me.dylanmullen.bingo.events.events.droplet.DropletRestartEvent;
import me.dylanmullen.bingo.events.events.droplet.DropletStartingEvent;
import me.dylanmullen.bingo.events.events.droplet.GameStateChangeEvent;
import me.dylanmullen.bingo.events.events.droplet.LineStateChangeEvent;
import me.dylanmullen.bingo.events.events.droplet.NextNumberChangeEvent;
import me.dylanmullen.bingo.events.events.droplet.RecieveWinnerEvent;
import me.dylanmullen.bingo.net.handlers.ClientHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class PacketHandler
{

	/**
	 * Creates a new packet to be sent to the server.
	 * 
	 * @param id   The ID of the packet.
	 * @param data
	 * @return
	 */
	public static Packet createPacket(int id, JSONObject data)
	{
		Packet p = new Packet(id);
		p.setMessage(data);
		return p;
	}

	/**
	 * Submits the packet to the Client Handler.
	 * 
	 * @param packet   The packet to be sent to the server.
	 * @param callback A callback if you are expecting a response.
	 */
	public static void sendPacket(Packet packet, PacketCallback callback)
	{
		PacketTicket ticket = new PacketTicket(packet, callback);
		ClientHandler.getInstance().submitTicket(ticket);
	}

	/**
	 * Handles the packet based on the ID of the packet.
	 * 
	 * @param data The data of a packet.
	 */
	public static void handlePacket(int id, JSONObject data)
	{
		JSONObject message = (JSONObject) data.get("packetMessage");
		UUID dropletUUID = UUID.fromString((String) message.get("dropletUUID"));
		switch (id)
		{
			// Sets the next number of the game.
			case 9:
				EventHandler.fireEvent(new NextNumberChangeEvent(dropletUUID, message));
				break;
			// Sets the new game state of the game.
			case 10:
				EventHandler.fireEvent(new GameStateChangeEvent(dropletUUID, message));
				break;
			// Updates the cards when the game begins.
			case 11:
				EventHandler.fireEvent(new DropletStartingEvent(dropletUUID, message));
				break;
			// Updates the line state of the game.
			case 12:
				EventHandler.fireEvent(new LineStateChangeEvent(dropletUUID, message));
				break;
			// Shows the winners of a line state.
			case 13:
				EventHandler.fireEvent(new RecieveWinnerEvent(dropletUUID, message));
				break;
			// Restarts the game after the game is finished.
			case 14:
				EventHandler.fireEvent(new DropletRestartEvent(dropletUUID, message));
				break;
			// Updates the credits of the player after it changes on the server.
			case 15:
				EventHandler.fireEvent(new CurrencyChangeEvent(dropletUUID, message));
				break;
			// Updates the chat with a new message.
			case 16:
				EventHandler.fireEvent(new ChatMessageEvent(dropletUUID, message));
				break;
			default:
				return;
		}
	}

}
