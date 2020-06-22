package me.dylanmullen.bingo.net;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.BingoGame;
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
		switch (id)
		{
			// Sets the next number of the game.
			case 9:
				BingoGame.getInstance().setNextNumber(message);
				break;
			// Sets the new game state of the game.
			case 10:
				BingoGame.getInstance().setGameState(((Number) message.get("gameState")).intValue());
				break;
			// Updates the cards when the game begins.
			case 11:
				BingoGame.getInstance().updateCards(message);
				break;
			// Updates the line state of the game.
			case 12:
				BingoGame.getInstance().updateLineState(((Number) message.get("linestate")).intValue());
				break;
			// Shows the winners of a line state.
			case 13:
				BingoGame.getInstance().showWinners((JSONArray) message.get("winners"));
				break;
			// Restarts the game after the game is finished.
			case 14:
				BingoGame.getInstance().restart(message);
				break;
			// Updates the credits of the player after it changes on the server.
			case 15:
				BingoGame.getInstance().getUserInformation()
						.updateCredits(((Number) message.get("credits")).doubleValue());
				break;
			// Updates the chat with a new message.
			case 16:
				BingoGame.getInstance().getGamePanel().getChatComponent().recieveMessage(message);
				break;
			default:
				return;
		}
	}
}
