package me.dylanmullen.bingo.events.events.droplet;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.DropletEvent;

public class ChatMessageEvent extends DropletEvent
{

	private String username;
	private String userGroup;
	private String message;
	private long timestamp;

	public ChatMessageEvent(UUID uuid, JSONObject message)
	{
		super(uuid);
		this.username = (String) message.get("displayName");
		this.userGroup = (String) message.get("userGroup");
		this.message = (String) message.get("message");
		this.timestamp=((Number)message.get("timestamp")).longValue();
	}

	public String getUsername()
	{
		return username;
	}

	public String getUserGroup()
	{
		return userGroup;
	}

	public String getMessage()
	{
		return message;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}

}
