package me.dylanmullen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.dylanmullen.bingo.core.BingoServer;

@SpringBootApplication
public class Runner
{

	public static void main(String[] args)
	{
		new BingoServer(4585);
		SpringApplication.run(Runner.class, args);
	}
	
}
