package me.dylanmullen.spring.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.droplet.BingoCloud;
import me.dylanmullen.bingo.game.droplet.BingoDroplet;
import me.dylanmullen.bingo.game.droplet.DropletManager;

@Service
public class DropletService
{

	@SuppressWarnings("unchecked")
	public JSONObject getDropletsFromCloud(String name)
	{
		BingoCloud cloud = BingoServer.getInstance().getGame().getBingoCloud(name);

		JSONObject json = new JSONObject();
		cloud.getDroplets().stream().forEach(e -> json.put(e.getUUID(), e.toJSON()));
		return json;
	}

	public JSONObject getDropletFromUUID(UUID uuid)
	{
		BingoDroplet droplet = DropletManager.getManager().getDropletFromUUID(uuid);
		if (droplet == null)
			return null;
		else
			return droplet.toJSON();
	}

	public List<JSONObject> getAllDroplets()
	{
		List<BingoDroplet> droplets = DropletManager.getManager().getDroplets();
		return droplets.stream().map(e -> e.toJSON()).collect(Collectors.toList());
	}

}
