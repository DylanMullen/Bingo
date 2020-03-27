package me.dylanmullen.bingo.events;

import java.util.ArrayList;
import java.util.List;

public class EventHandler
{

	/*
	 * TO-DO Make each event have their own set of listeners. Speeds up the firing
	 * of an event
	 */

	private static List<EventListener> listeners = new ArrayList<>();

	public static void registerListener(EventListener listener)
	{
		if (listeners.contains(listener))
			return;
		listeners.add(listener);
	}

	public static void unregisterListener(EventListener listener)
	{
		if (!listeners.contains(listener))
			return;
		listeners.remove(listener);
	}

	/**
	 * Fires an event. Big-O complexity of O(n)
	 * 
	 * @param e
	 */
	public static void fireEvent(Event e)
	{
		for (int i = 0; i < listeners.size(); i++)
		{
			EventListener listener = listeners.get(i);
			listener.fire(e);
		}
	}
}
