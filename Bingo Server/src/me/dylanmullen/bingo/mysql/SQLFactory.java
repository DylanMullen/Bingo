package me.dylanmullen.bingo.mysql;

import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;
import me.dylanmullen.bingo.mysql.sql_util.SQLQuery;
import me.dylanmullen.bingo.mysql.sql_util.SQLTicket;

public class SQLFactory
{

	public enum SQLTypes
	{
		VARCHAR("varchar"), INT("int"),;

		private String type;

		private SQLTypes(String type)
		{
			this.type = type;
		}

		public String getType()
		{
			return type;
		}
	}

	private static MySQLController mysql;

	public SQLFactory(MySQLController mySQL)
	{
		if (mysql == null)
			mysql = mySQL;
	}

	public static MySQLController getController()
	{
		return mysql;
	}

	public static void sendTicket(SQLTicket ticket)
	{
		mysql.submitTicket(ticket);
	}

	// columnName;type;length
	public static SQLTicket createTable(String tableName, String[] columns, String primaryKey, SQLCallback callback)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE IF NOT EXISTS " + tableName + " (");
		for (int i = 0; i < columns.length; i++)
		{
			String[] data = columns[i].split(";");
			String name = data[0];
			String type = data[1];
			String length = "";
			if (!data[2].equals("-1"))
				length = "(" + data[2] + ")";

			sb.append(name + " " + type + length + ",");
		}

		sb.append("PRIMARY KEY (" + primaryKey + "))");
		return new SQLTicket(new SQLQuery(sb.toString()), callback);
	}

	// SELECT <selection> FROM <table> WHERE
	public static SQLTicket selectData(String table, String selection, String[] conditions, String[] placeHolders,
			SQLCallback callback)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT " + selection + " FROM " + table + " WHERE ");

		for (int i = 0; i < conditions.length; i++)
		{
			sb.append(conditions[i] + " = ?" + (i == conditions.length - 1 ? "" : " AND "));
		}
		
		return new SQLTicket(new SQLQuery(sb.toString(), placeHolders), callback);
	}

	// INSERT INTO <table> (<columns>) VALUES (
	public static SQLTicket insertData(String table, String[] columns, String[] data, SQLCallback callback)
	{
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO " + table + " (");
		for (int i = 0; i < columns.length; i++)
		{
			query.append(columns[i] + (i == columns.length - 1 ? ")" : ","));
		}

		query.append(" VALUES (");
		for (int i = 0; i < columns.length; i++)
		{
			query.append("?" + (i == columns.length - 1 ? ")" : ","));
		}

		System.out.println(query.toString());

		return new SQLTicket(new SQLQuery(query.toString(), data), callback);
	}

	// UPDATE <table> SET (<column = ?...) WHERE condition
	public static SQLTicket updateData(String table, String condition, String[] columns, String[] placeHod,
			SQLCallback callback)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + table + " SET ");
		for (int i = 0; i < columns.length; i++)
		{
			String col = columns[i];
			sb.append(col + " = ?" + (i == columns.length - 1 ? "" : ","));
		}
		sb.append(" WHERE " + condition);
		return new SQLTicket(new SQLQuery(sb.toString(), placeHod), callback);
	}
}
