package me.dylanmullen.bingo.game.droplet;

import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.game.GameSettings;
import me.dylanmullen.bingo.game.user.User;

public class BingoCloud
{

	private GameSettings settings;
	private List<BingoDroplet> droplets;

	public BingoCloud(GameSettings settings)
	{
		this.settings = settings;
		this.droplets = new ArrayList<>();
	}

	public BingoDroplet createDroplet()
	{
		BingoDroplet droplet = new BingoDroplet(settings);
		return droplet;
	}
	
	public BingoDroplet getAvailableDroplet()
	{
		for(BingoDroplet droplet : droplets)
			if(droplet.hasSpace())
				return droplet;
		return createDroplet();
	}

	public void placeUser(User user)
	{
		if(isUserInCloud(user))
			return;
		
		BingoDroplet droplet = getAvailableDroplet();
		droplet.addUser(user);
	}

	public boolean isUserInCloud(User user)
	{
		for (BingoDroplet droplet : droplets)
			if (droplet.hasPlayer(user))
				return true;
		return false;
	}

}
