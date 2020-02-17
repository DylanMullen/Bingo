package me.dylanmullen.bingo.mysql.sql_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.dylanmullen.bingo.configs.Config;

public class SQLDatabase
{

	private String databaseName;

	private String address;
	private int port;

	private String username;
	private String password;

	private Connection connection;
	private boolean connected;

	public SQLDatabase(Config config)
	{
		this.databaseName = config.getValue("dbName");
		this.address =config.getValue("host");
		this.port = Integer.parseInt(config.getValue("port"));
		this.username = config.getValue("username");
		this.password = config.getValue("password");
	}

	public boolean connect()
	{
		if (connected)
			return false;
		System.out.println("Connecting to MySQL Database...");
		long last = System.currentTimeMillis();
		try
		{
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://" + this.address + ":" + this.port + "/" + this.databaseName, username, password);
			connected = true;
			System.out.println("MySQL Connected. Time taken: " + (System.currentTimeMillis() - last) / 1000 + "ms");
		} catch (SQLException e)
		{
			System.err.println("Error connecting to MySQL database: " + e.getMessage());
		}
		return false;
	}
	
	public Connection getConnection()
	{
		return connection;
	}

	public void close()
	{
		try
		{
			this.connection.close();
		} catch (SQLException e)
		{
			System.err.println("Error disconnecting to MySQL database: " + e.getMessage());
		}
	}

	public boolean isConnected()
	{
		return connected;
	}

}
