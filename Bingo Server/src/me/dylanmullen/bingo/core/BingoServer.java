package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.game.GameController;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.mysql.MySQLController;
import me.dylanmullen.bingo.net.Server;

public class BingoServer
{

	private static BingoServer instance;

	private Server server;
	private int port;

	private UserManager userManager;

	private GameController game;
	private MySQLController mysql;

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
		this.server = new Server(port);
		this.userManager = new UserManager();
		this.game=new GameController();
		
//		this.mysql = new MySQLController("bingo", "127.0.0.1", 3306);
	}

	private void start()
	{
		init();
		System.out.println("Server Started");
	}

	public void stop()
	{
	}

	public UserManager getUserManager()
	{
		return userManager;
	}

	public Server getServer()
	{
		return server;
	}
	
	public MySQLController getMySQL()
	{
		return mysql;
	}
}
