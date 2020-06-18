package me.dylanmullen.bingo.net;

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
	public static Packet createPacket(int id, String data)
	{
		Packet p = new Packet(id, data);
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
	public static void handlePacket(String data)
	{
		int id = (Integer.parseInt(data.split(";", 2)[0]));
		switch (id)
		{
			// Sets the next number of the game.
			case 9:
				BingoGame.getInstance().setNextNumber(data);
				break;
			// Sets the new game state of the game.
			case 10:
				BingoGame.getInstance().setGameState(Integer.parseInt(data.split(";")[1].split("/m/|/m/")[1]));
				break;
			// Updates the cards when the game begins.
			case 11:
				BingoGame.getInstance().updateCards(data);
				break;
			// Updates the line state of the game.
			case 12:
				BingoGame.getInstance().updateLineState(data);
				break;
			// Shows the winners of a line state.
			case 13:
				BingoGame.getInstance().showWinners(data);
				break;
			// Restarts the game after the game is finished.
			case 14:
				BingoGame.getInstance().restart(data);
				break;
			// Updates the credits of the player after it changes on the server.
			case 15:
				BingoGame.getInstance().getUserInformation().updateCredits(data);
				break;
			default:
				return;
		}
	}
}
