package me.dylanmullen.bingo.core;

import java.awt.EventQueue;

import me.dylanmullen.bingo.game.UserInformation;
import me.dylanmullen.bingo.net.handlers.ClientHandler;
import me.dylanmullen.bingo.window.bingo.BingoWindow;
import me.dylanmullen.bingo.window.login.LoginWindow;

public class BingoApp
{

	private static BingoApp instance;

	private ClientHandler clientHandler;

	/* Windows */
	private LoginWindow login;
	private BingoWindow bingo;

	public BingoApp()
	{
		if (instance == null)
			instance = this;
		init();
		openLoginWindow();
	}

	public static BingoApp getInstance()
	{
		return instance;
	}

	public void init()
	{
		clientHandler = new ClientHandler("localhost", 4585);
	}

	public void openLoginWindow()
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				login = new LoginWindow();
				login.setVisible(true);
			}
		});
	}

	public void openBingoWindow(UserInformation ui)
	{
		login.dispose();
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				bingo = new BingoWindow(ui);
				bingo.setVisible(true);
			}
		});
	}

}
