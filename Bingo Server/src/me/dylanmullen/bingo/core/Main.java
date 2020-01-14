package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.controllers.MySQLController;

public class Main
{
	
	public static void main(String[] args)
	{
		new MySQLController("bingo", "127.0.0.1", 3306);
	
	}
	
}
