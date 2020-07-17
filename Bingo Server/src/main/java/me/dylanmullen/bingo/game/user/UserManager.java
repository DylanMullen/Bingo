package me.dylanmullen.bingo.game.user;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import me.dylanmullen.bingo.game.user.callbacks.UserLoginCallback;
import me.dylanmullen.bingo.game.user.callbacks.UserRegisterCallback;
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
		SQLTicket ticket = SQLFactory.selectData(SQLFactory.getController().getDatabase().getLoginTableName(), "uuid",
				new String[] { "email", "password" }, new String[] { username, password }, callback);
		SQLFactory.sendTicket(ticket);
	}

	public void register(Client client, UUID packetUUID, String username, String password)
	{
		UserRegisterCallback callback = new UserRegisterCallback(client, packetUUID, username, password);
		SQLTicket ticket = SQLFactory.selectData(SQLFactory.getController().getDatabase().getLoginTableName(), "uuid",
				new String[] { "email" }, new String[] { username }, callback);
		SQLFactory.sendTicket(ticket);
	}

	public static UserManager getInstance()
	{
		return instance;
	}

	public void addUser(Client client, UUID uuid)
	{
		synchronized (users)
		{
			if (getUser(uuid) != null)
				users.remove(getUser(uuid));

			User u = new User(client, uuid);
			users.add(u);
			System.err.println("Added to Users");
		}
	}

	@Deprecated
	public User getUser(Client client)
	{
		synchronized (users)
		{
			for (User u : users)
				if (u.getClient().equals(client))
					return u;
			return null;
		}
	}

	public User getUser(UUID client)
	{
		synchronized (users)
		{
			for (User u : users)
				if (u.getUUID().toString().equalsIgnoreCase(client.toString()))
					return u;
			return null;
		}
	}
	
	public List<UserInformation> getActiveUsersInformation()
	{
		return users.stream().map(e->e.getUserInformation()).collect(Collectors.toList());
	}
	
	public HashSet<User> getUsers()
	{
		return users;
	}

}
