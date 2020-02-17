package me.dylanmullen.bingo.mysql.sql_util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLQuery
{

	public enum ExecutionType
	{
		QUERY, DATA_MANIPULATION;
	}
	
	private String query;
	private String[] variables;

	private PreparedStatement statement;

	private ExecutionType type;
	
	public SQLQuery(String string, String... placeholders)
	{
		this.query = string;
		this.variables = new String[placeholders.length];

		for (int i = 0; i < placeholders.length; i++)
		{
			variables[i] = placeholders[i];
		}
	}

	public boolean createStatement(Connection connection)
	{
		try
		{
			statement = connection.prepareStatement(query);
			fill();
			setExecutionType();
			return true;
		} catch (SQLException e)
		{
			System.err.println("Failed to create prepared statement: " + e.getMessage());
		}
		return false;
	}

	private void fill()
	{
		for (int i = 0; i < variables.length; i++)
		{
			try
			{
				statement.setObject(i + 1, variables[i]);
			} catch (SQLException e)
			{
				return;
			}
		}
	}
	
	private void setExecutionType()
	{
		if(query.startsWith("SELECT"))
		{
			type = ExecutionType.QUERY;
			return;
		}
		type = ExecutionType.DATA_MANIPULATION;
	}
	
	public ExecutionType getExecutionMethod()
	{
		return type;
	}
	
	public PreparedStatement getStatement()
	{
		return statement;
	}
	
}
