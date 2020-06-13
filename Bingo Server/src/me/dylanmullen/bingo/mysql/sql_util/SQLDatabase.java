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
		this.databaseName = (String) config.getValue("database", "databaseName");
		this.address = (String) config.getValue("hostAddress", "ip");
		this.port = ((Long) config.getValue("hostAddress", "port")).intValue();
		this.username = (String) config.getValue("credentials", "username");
		this.password = (String) config.getValue("credentials", "password");
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
			System.out.println("MySQL Connected. Time taken: " + (System.currentTimeMillis() - last) + "ms");
			return connected;
		} catch (SQLException e)
		{
			System.err.println("Error connecting to MySQL database: " + e.getMessage());
			System.err.println("Time Taken: " + (System.currentTimeMillis() - last) + "ms");
			return false;
		}
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
