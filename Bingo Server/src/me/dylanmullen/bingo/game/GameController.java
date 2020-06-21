package me.dylanmullen.bingo.game;

import java.util.HashSet;
import java.util.UUID;

import me.dylanmullen.bingo.configs.ConfigManager;
import me.dylanmullen.bingo.game.currency.CurrencyController;
import me.dylanmullen.bingo.game.currency.InvalidAmountException;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.packet.PacketHandler;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response;
import me.dylanmullen.bingo.net.packet.packets.Packet_005_Response.ResponseType;

public class GameController
{

	private static GameController instance;

	private final int playerCap = 10;

	private HashSet<BingoGame> games;

	public GameController()
	{
		ConfigManager.getInstance().loadBingoFiles();
		games = new HashSet<BingoGame>();
	}

	public void handleCardRequest(User u, UUID packetToRelay)
	{
		BingoGame game = u.getCurrentGame();
		CardGroup cg = game.generateCardGroup(u);
		Packet_005_Response res = (Packet_005_Response) PacketHandler.createPacket(u.getClient(), 005, "");
		res.constructMessage(ResponseType.SUCCESS, cg.toString(), packetToRelay);
		PacketHandler.sendPacket(res, null);
	}

	public void purchaseCard(User u, UUID uuid, UUID packetToRelay)
	{
		BingoGame game = u.getCurrentGame();
		try
		{
			CurrencyController.getController().deduct(u, game.getSettings().getTicketPrice());
		} catch (InvalidAmountException e)
		{
			System.err.println(e.getMessage());
			// TODO sent back not enough money
			return;
		}

		CardGroup cg = game.getCardGroup(u);
		BingoCard card = cg.getCard(uuid);
		game.addCard(u, card);
		game.getSettings().incrementPot();
		if (cg.remove(card))
			game.removeCardGroup(cg);

		Packet_005_Response res = (Packet_005_Response) PacketHandler.createPacket(u.getClient(), 005, "");
		res.constructMessage(ResponseType.SUCCESS, uuid.toString(), packetToRelay);
		PacketHandler.sendPacket(res, null);

	}

	public void submitChatMessage(User u, String message)
	{
		BingoGame game = u.getCurrentGame();
	}
	
	
	public BingoGame placeUser(User u)
	{
		if (u.getCurrentGame() != null)
			return null;
		BingoGame game = getGame();
		game.addPlayer(u);
		return game;
	}

	private BingoGame getGame()
	{
		for (BingoGame game : games)
		{
			if (game.getPlayers().size() < playerCap)
				return game;
		}
		return createNewGame();
	}

	private BingoGame createNewGame()
	{
		BingoGame game = new BingoGame(new GameSettings(ConfigManager.getInstance().getBingoConfig(0)));
		games.add(game);
		return game;
	}

}
