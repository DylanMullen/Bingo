package me.dylanmullen.bingo.core;

import java.util.UUID;

import me.dylanmullen.bingo.net.packet.Packet;

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
		Packet packet = new Packet(1);
		packet.setUserUUID(UUID.randomUUID());
		packet.setTimestamp();
		System.out.println(packet.toString());
	}
}
