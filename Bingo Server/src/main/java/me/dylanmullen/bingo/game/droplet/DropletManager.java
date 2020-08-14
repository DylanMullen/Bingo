package me.dylanmullen.bingo.game.droplet;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class DropletManager
{

	private static DropletManager manager;

	private List<BingoDroplet> droplets;

	public DropletManager()
	{
		this.droplets = new ArrayList<BingoDroplet>();
	}

	public static DropletManager getManager()
	{
		if (manager == null)
			manager = new DropletManager();
		return manager;
	}

	public void addDroplets(BingoDroplet droplet)
	{
		if (droplets.contains(droplet))
			return;
		droplets.add(droplet);
	}
	
	public BingoDroplet getDropletFromUUID(UUID uuid)
	{
		try
		{
			return droplets.stream().filter(e -> e.getUUID().equals(uuid)).findFirst().get();
		} catch (NoSuchElementException e)
		{
			return null;
		}
	}
	
	public List<BingoDroplet> getDroplets()
	{
		return droplets;
	}

}
