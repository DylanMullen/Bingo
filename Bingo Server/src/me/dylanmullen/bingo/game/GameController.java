package me.dylanmullen.bingo.game;

import java.util.HashSet;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.configs.Config;
import me.dylanmullen.bingo.configs.ConfigManager;
import me.dylanmullen.bingo.game.cards.BingoCardGroup;
import me.dylanmullen.bingo.game.currency.CurrencyController;
import me.dylanmullen.bingo.game.currency.InvalidAmountException;
import me.dylanmullen.bingo.game.droplet.BingoCloud;
import me.dylanmullen.bingo.game.droplet.BingoDroplet;
import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class GameController
{

	private HashSet<BingoCloud> bingoGames;

	public GameController()
	{
		ConfigManager.getInstance().loadBingoFiles();
		bingoGames = new HashSet<>();
		setup();
	}

	private void setup()
	{
		for (Config cfg : ConfigManager.getInstance().getBingoConfigs())
		{
			BingoCloud cloud = new BingoCloud(new GameSettings(cfg));
			bingoGames.add(cloud);
		}
	}

	public void connectUser(User user, UUID cloudUUID, UUID packetToRelay)
	{
		BingoCloud cloud = getBingoCloud(cloudUUID);
		Packet packet = PacketHandler.createPacket(user.getClient(), 5, null);
		JSONObject message = new JSONObject();
		packet.setMessageSection(message);
		packet.setPacketUUID(packetToRelay);
		if (cloud == null)
			return;

		BingoDroplet droplet = cloud.placeUser(user);
		if (droplet == null)
			return; // TODO already connected to that cloud.

		message.put("dropletUUID", droplet.getUUID().toString());
		message.put("gameState", droplet.getGameState().getStateCode());

		PacketHandler.sendPacket(packet);
	}

	public void handleCardRequest(User user, UUID dropletUUID, UUID packetToRelay)
	{
		BingoDroplet droplet = user.getDropletByUUID(dropletUUID);
		if (droplet == null)
			return; // TODO not in droplet;

		BingoCardGroup cardGroup = droplet.generateCards(user);
		JSONObject message = new JSONObject();
		message.put("responseType", 200);
		message.put("cards", cardGroup.getCardsJSON());

		Packet packet = PacketHandler.createPacket(user.getClient(), 5, null);
		packet.setMessageSection(message);
		packet.setPacketUUID(packetToRelay);
		PacketHandler.sendPacket(packet);
	}

	public void handlePurchaseCardRequest(User user, UUID dropletUUID, UUID card, UUID packetToRelay)
	{
		BingoDroplet droplet = user.getDropletByUUID(dropletUUID);
		Packet packet = PacketHandler.createPacket(user.getClient(), 5, null);
		JSONObject message = new JSONObject();
		packet.setMessageSection(message);
		packet.setPacketUUID(packetToRelay);

		if (droplet == null)
			return; // TODO not in droplet;

		try
		{
			CurrencyController.getController().deduct(user, droplet.getSettings().getTicketPrice());
		} catch (InvalidAmountException e)
		{
			System.err.println(e.getMessage());
			return;
		}

		BingoCardGroup cards = droplet.getCards(user);
		cards.getCard(card).setPurchased(true);
		droplet.getSettings().incrementPot();

		message.put("responseType", 200);
		message.put("purchasedCard", cards.getCard(card).getUUID().toString());
		PacketHandler.sendPacket(packet);
	}

	public void handleChatMessage(User user, UUID dropletUUID, String message)
	{
		// TODO
	}

	public BingoCloud getBingoCloud(UUID cloudUUID)
	{
		for (BingoCloud cloud : bingoGames)
			if (cloud.getUUID().toString().equalsIgnoreCase(cloudUUID.toString()))
				return cloud;
		return null;
	}

}
