package me.dylanmullen.bingo.game;

import java.util.Random;

import me.dylanmullen.bingo.configs.Config;
import me.dylanmullen.bingo.game.BingoGame.LineState;

public class GameSettings
{

	private String name;

	private int maxNumbers;
	private int maxPlayers;
	private int minPlayers;

	private double minPrize;
	private double maxPrize;

	private double prize;
	private double ticketPrice;
	private double increment;

	private boolean chat;
	private boolean debug;

	public GameSettings(Config properties)
	{
		this.name = (String) properties.getValue("properties", "name");

		this.chat = (boolean) properties.getValue("properties", "chat");
		this.debug = (boolean) properties.getValue("properties", "debug");

		this.maxNumbers = ((Long) properties.getValue("properties", "numbers")).intValue();
		this.minPlayers = ((Long) properties.getValue("properties.players", "min")).intValue();
		this.maxPlayers = ((Long) properties.getValue("properties.players", "max")).intValue();

		this.ticketPrice = ((Number) properties.getValue("properties.money", "ticketPrice")).doubleValue();
		this.increment = ((Number) properties.getValue("properties.money", "incrementMultiplier")).doubleValue();

		this.minPrize = ((Number) properties.getValue("properties.money.basePrize", "min")).doubleValue();
		this.maxPrize = ((Number) properties.getValue("properties.money.basePrize", "max")).doubleValue();
	}

	public double getPot()
	{
		return prize;
	}

	public void setupPot()
	{
		this.prize = getBasePrizePool();
		System.out.println(prize);
	}

	public void incrementPot()
	{
		this.prize += Math.round((ticketPrice * increment) * 100) / 100;
	}

	public double getWinningPrize(LineState state, int winners)
	{
		switch (state)
		{
			case ONE:
				return getFirstLine() / winners;
			case TWO:
				return getSecondLine() / winners;
			case FULLHOUSE:
				return getHousePrize() / winners;
			default:
				return -1;
		}
	}

	private double getFirstLine()
	{
		return prize * 0.2;
	}

	private double getSecondLine()
	{
		return prize * 0.3;
	}

	private double getHousePrize()
	{
		return prize * 0.5;
	}

	/**
	 * @return Returns a random base prize
	 */
	public double getBasePrizePool()
	{
		Random random = new Random();
		return Math.round((minPrize + (maxPrize - minPrize) * random.nextDouble()) * 100) / 100;
	}

	/* Getters */

	public String getName()
	{
		return name;
	}

	public int getMaxNumbers()
	{
		return maxNumbers;
	}

	public int getMaxPlayers()
	{
		return maxPlayers;
	}

	public int getMinPlayers()
	{
		return minPlayers;
	}

	public double getTicketPrice()
	{
		return ticketPrice;
	}

	public boolean isChat()
	{
		return chat;
	}

	public boolean isDebugMode()
	{
		return debug;
	}

}
