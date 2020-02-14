package me.dylanmullen.bingo.core;

import javax.swing.JTextField;

import me.dylanmullen.bingo.window.Window;
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
		lw.setVisible(true);
	}

}
