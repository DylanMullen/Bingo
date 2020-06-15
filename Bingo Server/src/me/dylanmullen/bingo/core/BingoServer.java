package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.configs.ConfigManager;
import me.dylanmullen.bingo.configs.IOController;
import me.dylanmullen.bingo.game.GameController;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.mysql.MySQLController;
import me.dylanmullen.bingo.net.handlers.ServerHandler;

public class BingoServer
{

	private static BingoServer instance;

	private ServerHandler serverHandler;
	private int port;

	private UserManager userManager;

	private GameController game;
	private MySQLController mysql;

	private boolean initialized = true;

	public BingoServer(int port)
	{
		if (instance == null)
			instance = this;
		this.port = port;
		start();
	}

	public static BingoServer getInstance()
	{
		return instance;
	}

	private void init()
	{
		if(IOController.getController().setup())
		{
			ConfigManager.getInstance();
			initialized=false;
			return;
		}
		this.mysql = new MySQLController(ConfigManager.getInstance().getConfig("mysql.json"));
		mysql.start();

		if (!mysql.isConnected())
		{
			initialized=false;
			return;
		}

		this.game = new GameController();
		this.userManager = new UserManager();
		this.serverHandler = new ServerHandler(port);

	}

	private void start()
	{
		init();
		if(!initialized)
		{
			System.err.println("Failed to start server.\nExiting");
			return;
		}
		serverHandler.start();
		System.out.println("Server Started");
	}

	public void stop()
	{
	}

	public UserManager getUserManager()
	{
		return userManager;
	}

	public MySQLController getMySQL()
	{
		return mysql;
	}
	
	public GameController getGame()
	{
		return game;
	}
}
