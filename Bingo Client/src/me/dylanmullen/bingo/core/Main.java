package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.window.login.LoginWindow;

public class Main
{

	public static void main(String args[]) throws Exception
	{
		new Main();
	}

	public Main()
	{
		LoginWindow lw = new LoginWindow();
		lw.setLocationRelativeTo(null);
		lw.setVisible(true);
	}

}
