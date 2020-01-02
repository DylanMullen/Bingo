package me.dylanmullen.bingo.core;

import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

public class Main
{

	
	public static void main(String[] args)
	{
		Client c = new Client("localhost", 4585);
		c.sendPacket("001/id/" + UUID.randomUUID() + "/nl/" + System.currentTimeMillis());
		
		byte[] recieve = new byte[1024];
		while(true)
		{
			String s = c.recieve();
			System.out.println(s);
		}
	}

}
