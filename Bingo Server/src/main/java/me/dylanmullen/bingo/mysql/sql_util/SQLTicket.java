package me.dylanmullen.bingo.mysql.sql_util;

import me.dylanmullen.bingo.mysql.callbacks.SQLCallback;

public class SQLTicket
{

	private SQLQuery query;
	private SQLCallback callback;

	public SQLTicket(SQLQuery query, SQLCallback callback)
	{
		this.query = query;
		this.callback = callback;
	}

	public SQLQuery getQuery()
	{
		return query;
	}

	public SQLCallback getCallback()
	{
		return callback;
	}

}
