package me.dylanmullen.bingo.controllers;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.dylanmullen.bingo.game.user.BUserInformation;
import me.dylanmullen.bingo.util.MutableResultSet;

public class MySQLController
{

	private String database;
	private String address;
	private int port;
	private boolean connected;

	private Connection connection;

	public MySQLController(String database, String address, int port)
	{
		this.database = database;
		this.address = address;
		this.port = port;
		this.connected = connect();
		create();
		System.out.println(System.currentTimeMillis() + ": Started");
		ResultSet rs = getData("b_userinfo", "uuid", "test");
		System.out.println(System.currentTimeMillis() + ": " + rs);
	}

	private boolean connect()
	{
		try
		{
			this.connection = DriverManager
					.getConnection("jdbc:mysql://" + this.address + ":" + this.port + "/" + this.database, "root", "");
			create();
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public void create()
	{
		Thread thread = new Thread(() ->
		{
			synchronized (this)
			{
				try
				{
					DatabaseMetaData db = connection.getMetaData();
					ResultSet rs = db.getTables(null, null, "b_userinfo", null);
					PreparedStatement statement = null;
					if (!rs.next())
					{
						statement = connection
								.prepareStatement("CREATE TABLE `bingo`.`b_userinfo` ( `uuid` VARCHAR(32) NOT NULL ,"
										+ " `display_name` VARCHAR(16) NOT NULL , `credit` INT NOT NULL ,"
										+ " `wins` INT NOT NULL , `losses` INT NOT NULL , PRIMARY KEY (`uuid`));");
						statement.execute();
					}
					rs = db.getTables(null, null, "b_userlogin", null);
					if (!rs.next())
					{
						statement = connection
								.prepareStatement("CREATE TABLE `bingo`.`b_userlogin` (`email` VARCHAR(255) NOT NULL , "
										+ "`password` CHAR(128) NOT NULL, `uuid` VARCHAR(32) NOT NULL);");
						statement.execute();
					}
					notify();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		thread.start();

		synchronized (thread)
		{
			try
			{
				wait();
				thread.join();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void setPlayerInformation(BUserInformation info)
	{
		try
		{
			ResultSet set = getData("b_userinfo", "uuid", info.getUUID().toString().replaceAll("-", ""));
			if(set == null)
				return;
			
			info.setUsername(set.getString("display_name"));
			info.setCredit(set.getDouble("credit"));
			info.setWins(set.getInt("wins"));
			info.setLosses(set.getInt("losses"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * Through testing, using a separate thread, it causes ~43ms to complete from
	 * the start of the method to the end. Whereas ignoring using another thread,
	 * gives around 30ms. Using wait() and notify() will still cause stoppage on the
	 * thread that it is waiting for
	 * 
	 * There is problem a better way to do this. To-do: > Look into a better way of
	 * separate thread MySQL calls
	 */
	private synchronized ResultSet getData(String table, String key, Object value)
	{
		if(!isConnected())
			return null;
		
		MutableResultSet mrs = new MutableResultSet();
		Thread thread = new Thread(() ->
		{
			synchronized (this)
			{
				try
				{
					PreparedStatement statement = connection
							.prepareStatement("SELECT * FROM " + table + " WHERE ? = ?");
					statement.setString(1, key);
					statement.setObject(2, value);
					mrs.setSet(statement.executeQuery());
					System.out.println(System.currentTimeMillis() + ": Retrieved");
					notify();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		});
		thread.start();

		synchronized (thread)
		{
			try
			{
				System.out.println(System.currentTimeMillis() + ": Test1");
				wait();
				thread.join();
				System.out.println(System.currentTimeMillis() + ": Test2");
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println(System.currentTimeMillis() + ": Test");
		return mrs.getSet();
	}

	public boolean isConnected()
	{
		return connected;
	}

}
