package me.dylanmullen.bingo.core;

import java.util.UUID;

import me.dylanmullen.bingo.net.Client;

public class Main
{

	public Main()
	{
		Client c = new Client("127.0.0.1", 4585);
		UUID u = UUID.fromString("3886c3ea-bc75-4d89-bf34-a18559aea089");
		c.sendPacket("/id/001/m/" + u + "/t/" + System.currentTimeMillis());
		c.sendPacket("/id/003/m/bingo-card/nl/"+ u + "/t/" + System.currentTimeMillis());
		
		boolean recieved=false;
		while(!recieved)
		{
			String s = c.recieve();
			System.out.println(s);
		}
		
		c.sendPacket("/id/002/m/" + u + "/t/");
	}

	public static void main(String args[]) throws Exception
	{
		new Main();
	}

}
