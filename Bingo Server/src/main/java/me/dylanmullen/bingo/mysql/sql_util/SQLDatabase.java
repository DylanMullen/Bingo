package me.dylanmullen.bingo.mysql.sql_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import me.dylanmullen.bingo.configs.Config;
import me.dylanmullen.bingo.mysql.SQLFactory;

public class SQLDatabase
{

	private Config config;

	private String databaseName;
	private String address;
	private String username;
	private String password;
	private int port;

	private String loginTable;
	private String userInfoTable;

	private Connection connection;
	private boolean connected;

	public SQLDatabase(Config config)
	{
		this.config = config;
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

	public void createTables()
	{
		this.loginTable = (String) config.getValue("database.tables", "loginTable");
		this.userInfoTable = (String) config.getValue("database.tables", "userInfo");
		
		SQLFactory.sendTicket(SQLFactory.createTable(loginTable,
				new String[] { "uuid;varchar;32", "username;varchar;40", "password;varchar;36" }, "uuid", null));
		SQLFactory.sendTicket(SQLFactory.createTable(userInfoTable, new String[] { "uuid;varchar;32",
				"username;varchar;16", "credits;double;-1", "wins;int;-1", "losses;int;-1" }, "uuid", null));
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
	
	public String getLoginTableName()
	{
		return loginTable;
	}
	
	public String getUserInfoTableName()
	{
		return userInfoTable;
	}

}
