package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.handlers.ClientHandler;
import me.dylanmullen.bingo.net.runnables.PingTask;
import me.dylanmullen.bingo.window.login.LoginWindow;

public class Main
{

	public static void main(String[] args)
	{
		new Main();
	}

	public Main()
	{
		new ClientHandler("localhost", 4585);
		PacketHandler.sendPacket(PacketHandler.createPacket(001, "dylan/nl/test"), null);

		new PingTask(0.25).start();
		new LoginWindow();
//		new BingoWindow();
	}

}
