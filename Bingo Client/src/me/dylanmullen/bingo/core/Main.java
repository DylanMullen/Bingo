package me.dylanmullen.bingo.core;

import java.util.HashMap;
import java.util.Map;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.events.user.CurrencyChangeEvent;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class Main
{

	/**
	 * Main method for launching the application.
	 * 
	 * @param args Arguments passed through the console.
	 */
	public static void main(String[] args)
	{
//		new BingoApp();
		Map<? super Event, EventListener> test = new HashMap<>();
		UserInformationChangeEvent event = new UserInformationChangeEvent("", 0, 0);
		EventListener listener = new EventListener()
		{

			@Override
			public void receive(Event event)
			{
				// TODO Auto-generated method stub

			}
		};
		test.put(event, listener);
		for (Object bj : test.keySet())
		{
			System.out.println(bj.getClass().getName());
		}
	}
}
