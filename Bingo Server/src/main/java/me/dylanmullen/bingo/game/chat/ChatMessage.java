package me.dylanmullen.bingo.game.chat;

import me.dylanmullen.bingo.game.user.User;

public class ChatMessage
{

	private User user;
	private String message;
	private long timeProduced;

	public ChatMessage(User user, String message)
	{
		this.user = user;
		this.message = message;
		this.timeProduced = System.currentTimeMillis();
	}

	public User getUser()
	{
		return user;
	}

	public String getMessage()
	{
		return message;
	}

	public long getTimeProduced()
	{
		return timeProduced;
	}

}
