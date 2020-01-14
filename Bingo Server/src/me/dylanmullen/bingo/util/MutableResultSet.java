package me.dylanmullen.bingo.util;

import java.sql.ResultSet;

public class MutableResultSet
{
	
	private ResultSet set;

	public void setSet(ResultSet set)
	{
		this.set = set;
	}
	
	public ResultSet getSet()
	{
		return set;
	}

}
