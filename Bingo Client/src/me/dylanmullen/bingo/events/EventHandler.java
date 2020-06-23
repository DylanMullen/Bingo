package me.dylanmullen.bingo.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class EventHandler
{

	private static EventHandler handler;

	private Map<Class<? extends Event>, List<EventListener>> registeredListeners;

	public static EventHandler getHandler()
	{
		if (handler == null)
			handler = new EventHandler();
		return handler;
	}

	public void registerListener(Class<? extends Event> toListen, EventListener listener)
	{
		List<EventListener> listeners = getListeners(toListen);
		listeners.add(listener);
	}

	public void registerListener(EventListener listener, List<Class<? extends Event>> events)
	{
		for (Class<? extends Event> event : events)
			registerListener(event, listener);
	}

	public void fire(Event event)
	{
		List<EventListener> listeners = getListeners(event.getClass());
		for (EventListener listener : listeners)
			listener.receive(event);
	}

	public List<EventListener> getListeners(Class<? extends Event> event)
	{
		if (registeredListeners.get(event) == null)
		{
			registeredListeners.put(event, new ArrayList<EventListener>());
		}
		return registeredListeners.get(event);
	}

}
