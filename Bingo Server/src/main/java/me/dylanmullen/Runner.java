package me.dylanmullen;

import me.dylanmullen.bingo.core.BingoServer;

public class Runner
{

	public static void main(String[] args)
	{
		new BingoServer(4585);
//		SpringApplication.run(Runner.class, args);
	}
	
}
