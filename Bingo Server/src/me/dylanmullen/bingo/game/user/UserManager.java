package me.dylanmullen.bingo.game.user;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.net.Client;

public class UserManager
{

	private static UserManager instance;
	private List<BUser> users;
	
	public static UserManager getInstance()
	{
		return instance;
	}
	
	public UserManager()
	{
		if(instance == null)
			instance = this;
		
		this.users = new ArrayList<BUser>();
	}
	
	public void add(BUser user)
	{
		if(users.contains(user))
			return;
		this.users.add(user);
	}
	
	public void remove(BUser user)
	{
		if(!users.contains(user))
			return;
		this.users.remove(user);
	}
	
	public Client getUserClient(InetAddress add, int port)
	{
		for(BUser user : users)
		{
			if(user.getClient().getAddress() == add && user.getClient().getPort()==port)
				return user.getClient();
		}
		return null;
	}
	
}
