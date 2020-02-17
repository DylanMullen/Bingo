package me.dylanmullen.bingo.util;

public class DebugUtils
{
	
	private static boolean DEBUG = true;
	
	public static void sendThreadInformation(Thread thread)
	{
		if(DEBUG)
			System.out.println("Thread: " + thread.getId());
	}

}
