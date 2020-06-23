package me.dylanmullen.bingo.events;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class EventHandler
{

	/*
	 * TO-DO Make each event have their own set of listeners. Speeds up the firing
	 * of an event
	 */
	
	private static List<EventListener> listeners = new ArrayList<>();

	/**
	 * Registers an Event Listener to listen for events.<br>
	 * If the Event Listener already exists and is in the cache, it will not be
	 * registered.
	 * 
	 * @param listener The listener to be registered.
	 */
	public static void registerListener(EventListener listener)
	{
		if (listeners.contains(listener))
			return;
		listeners.add(listener);
	}

	/**
	 * Unregisters an Event Listener that current listens for Events. <br>
	 * If the Event Listener does not exist, it cannot be unregistered and will not
	 * be unregistered.
	 * 
	 * @param listener The listener to be unregistered
	 */
	public static void unregisterListener(EventListener listener)
	{
		if (!listeners.contains(listener))
			return;
		listeners.remove(listener);
	}

	/**
	 * Fires an event to all the listeners currently registered to listening to
	 * events.
	 * 
	 * @param event The event that is to be fired.
	 */
	public static void fireEvent(Event event)
	{
		for (int i = 0; i < listeners.size(); i++)
		{
			EventListener listener = listeners.get(i);
			listener.receive(event);
		}
	}
}
