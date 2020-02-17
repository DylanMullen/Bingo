package me.dylanmullen.bingo.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import me.dylanmullen.bingo.configs.Config;
import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;
import me.dylanmullen.bingo.mysql.sql_util.SQLDatabase;
import me.dylanmullen.bingo.mysql.sql_util.SQLQuery;
import me.dylanmullen.bingo.mysql.sql_util.SQLQuery.ExecutionType;
import me.dylanmullen.bingo.mysql.sql_util.SQLTicket;

public class MySQLController implements Runnable
{

	private ArrayList<SQLTicket> queue;

	private Thread thread;
	private boolean running;

	private Config config;
	private SQLDatabase database;

	public MySQLController(Config properties)
	{
		this.queue = new ArrayList<SQLTicket>();
		this.config = properties;
		new SQLFactory(this);
	}

	public synchronized void start()
	{
		if (running)
			return;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run()
	{
		database = new SQLDatabase(config);
		database.connect();

		while (database.isConnected())
		{
			handleRequests();
		}
	}

	private void handleRequests()
	{
		synchronized (queue)
		{
			for (int i = 0; i < queue.size(); i++)
			{
				SQLTicket ticket = queue.get(i);
				SQLQuery query = ticket.getQuery();

				if (!query.createStatement(database.getConnection()))
					continue;
				
				if(query.getExecutionMethod()==null)
					continue;
				
				if(query.getExecutionMethod().equals(ExecutionType.QUERY))
					sendSQLQueryRequest(query.getStatement(), ticket.getCallback());
				else
					sendSQLDataManipRequest(query.getStatement(), ticket.getCallback());
				
				queue.remove(i);
			}
		}
	}

	private void sendSQLQueryRequest(PreparedStatement statement, SQLCallback callback)
	{
		try
		{
			callback.setTimeExecuted(System.currentTimeMillis());
			ResultSet rs = statement.executeQuery();
			callback.setResult(rs);
			callback.callback();
		} catch (SQLException e)
		{
			System.err.println("Failed to execute query: " + e.getMessage());
		}
	}
	
	private void sendSQLDataManipRequest(PreparedStatement statement, SQLCallback callback)
	{
		try
		{
			callback.setTimeExecuted(System.currentTimeMillis());
			statement.execute();
			callback.callback();
		} catch (SQLException e)
		{
			System.err.println("Failed to execute query: " + e.getMessage());
		}
	}

	public void submitTicket(SQLTicket ticket)
	{
		if (queue.contains(ticket))
			return;
		System.out.println("Ticket added");
		queue.add(ticket);
	}

	public synchronized void dispose()
	{
		if(!running)
			return;
		try
		{
			running = false;
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
