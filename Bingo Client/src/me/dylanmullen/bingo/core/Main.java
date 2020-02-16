package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.handlers.ClientHandler;

public class Main
{

	public static void main(String args[]) throws Exception
	{
		new Main();
	}

	public Main()
	{
//		LoginWindow lw = new LoginWindow();
//		lw.setLocationRelativeTo(null);
//		lw.setVisible(true);
		ClientHandler handler = new ClientHandler("localhost", 4585);
		PacketHandler.sendPacket(PacketHandler.createPacket(1, "test"));
	}

}
