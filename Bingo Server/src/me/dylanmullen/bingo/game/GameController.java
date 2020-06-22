package me.dylanmullen.bingo.game;

import java.util.HashSet;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.configs.ConfigManager;
import me.dylanmullen.bingo.game.currency.CurrencyController;
import me.dylanmullen.bingo.game.currency.InvalidAmountException;
import me.dylanmullen.bingo.game.droplet.BingoGame;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class GameController
{

	private HashSet<BingoGame> games;

	public GameController()
	{
		ConfigManager.getInstance().loadBingoFiles();
		games = new HashSet<BingoGame>();
	}

	@SuppressWarnings("unchecked")
	public void handleCardRequest(User u, UUID packetToRelay)
	{
		BingoGame game = u.getCurrentGame();
		CardGroup cg = game.generateCardGroup(u);
		
		Packet packet = PacketHandler.createPacket(u.getClient(), 5, null);
		packet.setPacketUUID(packetToRelay);
		JSONObject message = new JSONObject();
		message.put("responseType", 200);
		message.put("cards", cg.toString());
		packet.setMessageSection(message);
		PacketHandler.sendPacket(packet);
	}

	@SuppressWarnings("unchecked")
	public void purchaseCard(User u, UUID uuid, UUID packetToRelay)
	{
		BingoGame game = u.getCurrentGame();
		Packet packet = PacketHandler.createPacket(u.getClient(), 5, null);
		
		try
		{
			CurrencyController.getController().deduct(u, game.getSettings().getTicketPrice());
		} catch (InvalidAmountException e)
		{
			System.err.println(e.getMessage());
			return;
		}

		CardGroup cg = game.getCardGroup(u);
		BingoCards card = cg.getCard(uuid);
		game.addCard(u, card);
		game.getSettings().incrementPot();
		if (cg.remove(card))
			game.removeCardGroup(cg);
		
		JSONObject data = new JSONObject();
		data.put("responseType", 200);
		data.put("purchasedCard", uuid.toString());
		packet.setMessageSection(data);
		packet.setPacketUUID(packetToRelay);

		PacketHandler.sendPacket(packet);

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
			if (game.getPlayers().size() < game.getSettings().getMaxPlayers())
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
