package me.dylanmullen.bingo.events.events.droplet;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;

public class RecieveWinnerEvent extends DropletEvent
{

	private List<String> winners;

	public RecieveWinnerEvent(UUID dropletUUID, JSONObject packetMessage)
	{
		super(dropletUUID);
		this.winners = new ArrayList<>();
		System.out.println("winners recieved");
		JSONArray data = ((JSONArray) packetMessage.get("winners"));
		for (int i = 0; i < data.size(); i++)
		{
			winners.add((String) data.get(i));
		}
	}

	public List<String> getWinners()
	{
		return winners;
	}
}
