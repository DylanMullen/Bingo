package me.dylanmullen.bingo.core;

import java.util.UUID;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class Main
{

	/**
	 * Main method for launching the application.
	 * 
	 * @param args Arguments passed through the console.
	 */
	public static void main(String[] args)
	{
		new BingoApp();
		System.out.println(UUID.randomUUID());
	}
}
