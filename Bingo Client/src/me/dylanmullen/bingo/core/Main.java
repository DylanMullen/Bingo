package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.handlers.ClientHandler;
import me.dylanmullen.bingo.window.login.LoginWindow;

public class Main
{

	public static void main(String args[]) throws Exception
	{
		new Main();
	}

	public Main()
	{
		new ClientHandler("localhost", 4585);
		LoginWindow lw = new LoginWindow();
	}

}
