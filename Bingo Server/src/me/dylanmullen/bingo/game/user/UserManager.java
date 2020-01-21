package me.dylanmullen.bingo.game.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

public class UserManager
{

	private static UserManager instance;
	public List<BUser> users;

	public static UserManager getInstance()
	{
		return instance;
	}

	public UserManager()
	{
		if (instance == null)
			instance = this;

		this.users = new ArrayList<BUser>();
	}

	public void create(Client c, UUID uuid)
	{
		add(new BUser(c, uuid));
	}

	private void add(BUser user)
	{
		if (users.contains(user))
			return;
		this.users.add(user);
	}

	public void remove(BUser user)
	{
		if (!users.contains(user))
		{
			return;
		}
		this.users.remove(user);
	}

	public BUser getUser(UUID uuid)
	{
		for (BUser user : users)
		{
			if (user.getUuid().toString().equalsIgnoreCase(uuid.toString()))
			{
				return user;
			}
		}
		return null;
	}

}
