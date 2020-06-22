package me.dylanmullen.bingo.core;

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
//		new BingoApp();
		int number = 75;
		int numbers = 75;
		int cols = 5;

		int column = number / (numbers / cols);
		column -= (number == numbers ? 1 : 0);
		System.out.println(column);
	}
}
