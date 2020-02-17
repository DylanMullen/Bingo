package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.configs.Config;
import me.dylanmullen.bingo.game.user.UserManager;
import me.dylanmullen.bingo.mysql.MySQLController;
import me.dylanmullen.bingo.net.handlers.ServerHandler;

public class Main
{

	public Main()
	{
		new MySQLController(new Config(getClass().getClassLoader().getResourceAsStream("mysql.config"))).start();;
		new UserManager();
		new ServerHandler(4585).start();
	}

	public static void main(String[] args)
	{
		new Main();
	}

}
