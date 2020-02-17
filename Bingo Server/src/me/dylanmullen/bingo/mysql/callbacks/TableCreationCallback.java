package me.dylanmullen.bingo.mysql.callbacks;

public class TableCreationCallback extends SQLCallback
{

	@Override
	public boolean callback()
	{
		System.out.println("Table created. Time Taken: " + (System.currentTimeMillis() - timeExecuted) + "ms");
		return true;
	}

}
