package me.dylanmullen.bingo.game.user;

import java.util.HashSet;
import java.util.UUID;

import me.dylanmullen.bingo.game.user.callbacks.UserLoginCallback;
import me.dylanmullen.bingo.mysql.SQLFactory;
import me.dylanmullen.bingo.mysql.sql_util.SQLTicket;
import me.dylanmullen.bingo.net.Client;

public class UserManager
{

	private static UserManager instance;

	private HashSet<User> users;

	public UserManager()
	{
		if (instance == null)
			instance = this;
		this.users = new HashSet<User>();
	}

	public void login(Client client, UUID packetUUID, String username, String password)
	{
		UserLoginCallback callback = new UserLoginCallback(client, packetUUID);
		SQLTicket ticket = SQLFactory.selectData("b_userlogin", "uuid", new String[] { "username", "password" },
				new String[] { username, password }, callback);
		SQLFactory.sendTicket(ticket);
	}

	public static UserManager getInstance()
	{
		return instance;
	}

	public void addUser(Client client, UUID uuid)
	{
		synchronized(users)
		{
			User u = new User(client, uuid);
			if (users.contains(u))
				return;
			users.add(u);
			System.err.println("Added to Users");
		}
	}

	public User getUser(UUID uuid)
	{
		for (User u : users)
			if (u.getUUID().equals(uuid))
				return u;
		return null;
	}

	public User getUser(Client client)
	{
		synchronized(users)
		{
			for (User u : users)
				if (u.getClient().equals(client))
					return u;
			return null;
		}
	}

}
