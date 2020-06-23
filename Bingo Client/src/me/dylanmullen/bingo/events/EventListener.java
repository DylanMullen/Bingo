package me.dylanmullen.bingo.events;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public interface EventListener
{

	/**
	 * Receives an event fired to the Listener.
	 * 
	 * @param event The event that was fired to the listener
	 */
	void receive(Event event);
}
