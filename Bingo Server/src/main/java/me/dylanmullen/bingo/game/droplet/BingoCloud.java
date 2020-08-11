package me.dylanmullen.bingo.game.droplet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.GameSettings;
import me.dylanmullen.bingo.game.user.User;

public class BingoCloud
{

	private UUID uuid;
	private GameSettings settings;
	private List<BingoDroplet> droplets;

	public BingoCloud(GameSettings settings)
	{
		this.uuid = UUID.randomUUID();
		this.settings = settings;
		this.droplets = new ArrayList<>();
		createDroplet();
		createDroplet();
	}

	public BingoDroplet createDroplet()
	{
		BingoDroplet droplet = new BingoDroplet(settings);
		droplets.add(droplet);
		return droplet;
	}

	public BingoDroplet placeUser(UUID dropletUUID, User user)
	{
		BingoDroplet droplet = getDroplet(dropletUUID);
		if (droplet == null)
			return null;
		droplet.addUser(user);
		return droplet;
	}

	public BingoDroplet getDroplet(UUID uuid)
	{
		for (BingoDroplet droplet : droplets)
			if (droplet.getUUID().toString().equalsIgnoreCase(uuid.toString()))
				return droplet;
		return null;
	}

	public int getTotalPlayers()
	{
		int players = 0;
		for (BingoDroplet droplet : droplets)
		{
			players += droplet.getConnectedPlayersSize();
		}
		return players;
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public JSONObject getDropletInformation()
	{
		JSONObject object = new JSONObject();

		for (BingoDroplet droplet : droplets)
		{
			object.put(droplet.getUUID().toString(), droplet.getConnectedPlayersSize());
		}
		return object;
	}

	public JSONObject getCloudInformation()
	{
		JSONObject information = new JSONObject();
		information.put("uuid", this.uuid.toString());
		information.put("name", this.settings.getName());
		information.put("ticket-price", settings.getTicketPrice());
		information.put("instances", droplets.size());
		JSONObject dropletInfo = new JSONObject();
		droplets.stream().forEach(e ->
		{
			JSONObject object = new JSONObject();
			object.put("users-connected", e.getConnectedPlayersSize());
			object.put("gamestate", e.getGameState().getStateCode());
			dropletInfo.put(e.getUUID().toString(), object);
		});

		information.put("droplet-information", dropletInfo);
		return information;
	}

	public JSONObject getCloudJSON()
	{
		JSONObject contents = new JSONObject();
		contents.put("cloudName", settings.getName());
		contents.put("dropletInstances", droplets.size());
		contents.put("totalPlayers", getTotalPlayers());
		contents.put("ticketPrice", settings.getTicketPrice());
		return contents;
	}
	
	public String getName()
	{
		return settings.getName();
	}
}
