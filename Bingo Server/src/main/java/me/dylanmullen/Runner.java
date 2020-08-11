package me.dylanmullen;

import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner
{

	public static void main(String[] args)
	{
		System.out.println(UUID.randomUUID().toString());
//		new BingoServer(4585);
		SpringApplication.run(Runner.class, args);
	}
	
}
