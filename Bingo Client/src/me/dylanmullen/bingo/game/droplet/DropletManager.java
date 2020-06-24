package me.dylanmullen.bingo.game.droplet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import me.dylanmullen.bingo.events.DropletEvent;
import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.events.droplet.ChatMessageEvent;
import me.dylanmullen.bingo.events.events.droplet.DropletJoinEvent;
import me.dylanmullen.bingo.events.events.droplet.DropletRestartEvent;
import me.dylanmullen.bingo.events.events.droplet.DropletStartingEvent;
import me.dylanmullen.bingo.events.events.droplet.DropletsRetrievedEvent;
import me.dylanmullen.bingo.events.events.droplet.GameStateChangeEvent;
import me.dylanmullen.bingo.events.events.droplet.LineStateChangeEvent;
import me.dylanmullen.bingo.events.events.droplet.NextNumberChangeEvent;
import me.dylanmullen.bingo.events.events.droplet.RecieveWinnerEvent;
import me.dylanmullen.bingo.events.events.droplet.cardevents.CardPurchasedEvent;
import me.dylanmullen.bingo.events.events.droplet.cardevents.CardsRecievedEvent;
import me.dylanmullen.bingo.game.GamePanel;
import me.dylanmullen.bingo.window.bingo.BingoWindow;

public class DropletManager implements EventListener
{

	private BingoDroplet activeDroplet;
	private Set<BingoDroplet> droplets;

	private static DropletManager manager;

	public static DropletManager getManager()
	{
		if (manager == null)
			manager = new DropletManager();
		return manager;
	}

	public DropletManager()
	{
		this.droplets = new HashSet<BingoDroplet>();
		registerListener();
	}

	private void registerListener()
	{
		List<Class<? extends Event>> events = new ArrayList<>();
		events.add(NextNumberChangeEvent.class);
		events.add(GameStateChangeEvent.class);
		events.add(LineStateChangeEvent.class);
		events.add(DropletStartingEvent.class);
		events.add(RecieveWinnerEvent.class);
		events.add(DropletRestartEvent.class);
		events.add(ChatMessageEvent.class);
		events.add(DropletJoinEvent.class);
		events.add(CardPurchasedEvent.class);
		events.add(CardsRecievedEvent.class);
		events.add(DropletsRetrievedEvent.class);
		EventHandler.getHandler().registerListener(this, events);
	}

	@Override
	public void receive(Event event)
	{
		if (!(event instanceof DropletEvent))
			return;

		BingoDroplet droplet = getDroplet(((DropletEvent) event).getDropletUUID());

		if (droplet == null)
		{
			System.out.println("droplet null");
			if (event instanceof DropletJoinEvent)
			{
				joinDroplet(((DropletJoinEvent) event).getDropletUUID(), ((DropletJoinEvent) event).getNewState());
			}
			return;
		}

		if (event instanceof NextNumberChangeEvent)
		{
			droplet.setNextNumber(((NextNumberChangeEvent) event).getNumberToChange());
			return;
		} else if (event instanceof GameStateChangeEvent)
		{
			droplet.setGameState(((GameStateChangeEvent) event).getNewState());
			return;
		} else if (event instanceof LineStateChangeEvent)
		{
			droplet.setLineState((((LineStateChangeEvent) event).getNewState()));
			return;
		} else if (event instanceof DropletStartingEvent)
		{
			droplet.startDropletGame(((DropletStartingEvent) event).getPurchasedCards());
			return;
		} else if (event instanceof RecieveWinnerEvent)
		{
			droplet.showWinners(((RecieveWinnerEvent) event).getWinners());
			return;
		} else if (event instanceof DropletRestartEvent)
		{
			droplet.restartDropletGame(((DropletRestartEvent) event).getCards());
			return;
		} else if (event instanceof ChatMessageEvent)
		{
			ChatMessageEvent ce = (ChatMessageEvent) event;
			droplet.recieveChatMessage(ce.getTimestamp(), ce.getUsername(), ce.getUserGroup(), ce.getMessage());
			return;
		} else if (event instanceof CardPurchasedEvent)
		{
			droplet.setCardPuchased(((CardPurchasedEvent) event).getCardUUID());
			return;
		} else if (event instanceof CardsRecievedEvent)
		{
			droplet.getGamePanel().getGameComponent().createCardGroup(droplet.getUUID(),
					((CardsRecievedEvent) event).getCards());
			return;
		}
	}

	public void joinDroplet(UUID uuid, GameState state)
	{
		BingoDroplet droplet = createDroplet(uuid, state);
		setActiveDroplet(droplet);
		this.droplets.add(droplet);
		BingoWindow.getWindow().showDroplet(droplet);
		if (droplet.getGameState() == GameState.LOBBY)
			droplet.requestCards();
	}

	public void switchDroplet(UUID uuid)
	{
		BingoDroplet droplet = getDroplet(uuid);
		if (droplet == null)
			return;
		setActiveDroplet(droplet);
		// TODO set the droplet in the container
	}

	public void leaveDroplet(UUID uuid)
	{
		BingoDroplet droplet = getDroplet(uuid);
		if (droplet == null)
			return;
	}

	private BingoDroplet createDroplet(UUID uuid, GameState state)
	{
		GamePanel panel = new GamePanel(uuid, 0, 0, 0, 0); // TODO get the width and height of the container
		BingoDroplet droplet = new BingoDroplet(uuid, panel);
		droplet.setGameState(state);
		return droplet;
	}

	public BingoDroplet getDroplet(UUID uuid)
	{
		for (BingoDroplet droplet : this.droplets)
			if (droplet.getUUID().toString().equalsIgnoreCase(uuid.toString()))
				return droplet;
		return null;
	}

	public void setActiveDroplet(BingoDroplet droplet)
	{
		this.activeDroplet = droplet;
	}

	public BingoDroplet getActiveDroplet()
	{
		return activeDroplet;
	}
}
