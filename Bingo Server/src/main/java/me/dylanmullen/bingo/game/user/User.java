package me.dylanmullen.bingo.game.user;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.boot.orm.jpa.EntityScan;

import me.dylanmullen.bingo.game.droplet.BingoDroplet;
import me.dylanmullen.bingo.net.Client;

public class User
{

	private Client client;
	private UUID uuid;
	private UserInformation userInformation;

	private Set<BingoDroplet> bingoDroplets;

	public User(Client c, UUID uuid)
	{
		this.client = c;
		this.uuid = uuid;
		this.bingoDroplets = new HashSet<>();
	}

	public void loadInformation(UUID packetUUID)
	{
		userInformation = new UserInformation(uuid);
		userInformation.load(client, packetUUID);
	}

	public void createUserInformation()
	{
		userInformation = new UserInformation(uuid);
		userInformation.populateInformation("");
	}

	public void addDroplet(BingoDroplet droplet)
	{
		getBingoDroplets().add(droplet);
	}

	public Set<BingoDroplet> getBingoDroplets()
	{
		return bingoDroplets;
	}

	public BingoDroplet getDropletByUUID(UUID uuid)
	{
		for (BingoDroplet droplet : bingoDroplets)
			if (droplet.getUUID().toString().equalsIgnoreCase(uuid.toString()))
				return droplet;
		return null;
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public Client getClient()
	{
		return client;
	}

	public UserInformation getUserInformation()
	{
		return userInformation;
	}

}
