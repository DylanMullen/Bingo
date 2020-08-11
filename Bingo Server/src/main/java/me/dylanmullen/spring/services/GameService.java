package me.dylanmullen.spring.services;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import me.dylanmullen.bingo.core.BingoServer;
import me.dylanmullen.bingo.game.droplet.BingoCloud;

@Service
public class GameService
{

	public JSONObject getAllGames()
	{
		JSONObject response = new JSONObject();
		addGamesJSON(response);
		return response;
	}

	public JSONObject getCloudInformation(String name)
	{
		return BingoServer.getInstance().getGame().getBingoCloud(name).getCloudInformation();
	}

	@SuppressWarnings("unchecked")
	private void addGamesJSON(JSONObject response)
	{
		int index = 0;
		for (BingoCloud cloud : BingoServer.getInstance().getGame().getClouds())
		{
			response.put(index, cloud.getCloudInformation());
			index++;
		}
	}

}
