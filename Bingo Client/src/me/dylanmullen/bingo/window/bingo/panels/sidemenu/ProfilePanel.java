package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.events.user.CurrencyChangeEvent;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.window.ui.ImageComponent;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class ProfilePanel extends UIPanel implements EventListener
{

	private static final long serialVersionUID = 3952772083758555561L;

	private Grid grid;
	private List<InformationComponent> infoComponents = new ArrayList<>();

	/**
	 * The profile panel of the Side Menu. <br>
	 * This is the main source of information for the Player. This displays the
	 * Players stats whilst playing bingo and is always shown to the Player during
	 * their usage of the Application.
	 * 
	 * @param x      X-Position of the Profile Panel.
	 * @param y      Y-Position of the Profile Panel.
	 * @param width  The width of the Profile Panel.
	 * @param height The height of the Profile Panel.
	 */
	public ProfilePanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		EventHandler.getHandler().registerListener(UserInformationChangeEvent.class, this);
		EventHandler.getHandler().registerListener(CurrencyChangeEvent.class, this);
	}

	@Override
	public void setup()
	{
		setBounds(this.x, this.y, this.width, this.height);
		setBorder(new EmptyBorder(12, 12, 12, 12));
		setOpaque(false);
		this.grid = new Grid(new GridSettings(getWidth() - 10, getHeight() - 110, 4, 1, 5), 5, 105);

		ImageComponent imageComponent = new ImageComponent(10, 10, getWidth() - 20, 90);
		try
		{
			imageComponent.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		setupFields();
		create();
		add(imageComponent);
	}

	/**
	 * This setups the information components for the Profile Panel.
	 */
	private void setupFields()
	{
		InformationComponent username = new InformationComponent("Username", "Loading...", 0, 0, 0, 0);
		InformationComponent money = new InformationComponent("Money", "Loading...", 0, 0, 0, 0);
		InformationComponent wins = new InformationComponent("Wins", "Loading...", 0, 0, 0, 0);

		getGrid().addGridItem(new GridItem(username, 1, 1), 0);
		getGrid().addGridItem(new GridItem(money, 1, 1), 1);
		getGrid().addGridItem(new GridItem(wins, 1, 1), 2);
		username.create();
		wins.create();
		money.create();

		getInformationComponents().add(username);
		getInformationComponents().add(money);
		getInformationComponents().add(wins);
	}

	@Override
	public void create()
	{
		for (InformationComponent item : getInformationComponents())
		{
			add(item);
		}
	}

	/**
	 * Updates the items of the Information Components to have the most up to date
	 * information that was received from the server.
	 */
	public void updateItems(UserInformationChangeEvent event)
	{
		getInformationComponents().get(0).getInformation().setText(event.getUsername());
		getInformationComponents().get(1).getInformation().setText(event.getCredits() + "");
		getInformationComponents().get(2).getInformation().setText(event.getWins() + "");
	}

	/**
	 * Updates the credits information component.
	 * 
	 * @param credits The new credits value.
	 */
	public void updateCredits(double credits)
	{
		getInformationComponents().get(1).getInformation().setText(credits + "");
	}

	/**
	 * Returns the Grid being used to position the {@link #infoComponents}
	 * 
	 * @return {@link #grid}
	 */
	public Grid getGrid()
	{
		return this.grid;
	}

	/**
	 * Returns the Information Components of the Profile Panel.
	 * 
	 * @return {@link #infoComponents}
	 */
	public List<InformationComponent> getInformationComponents()
	{
		return this.infoComponents;
	}

	@Override
	public void receive(Event event)
	{
		if (event instanceof UserInformationChangeEvent)
			updateItems((UserInformationChangeEvent) event);
		else if (event instanceof CurrencyChangeEvent)
			updateCredits(((CurrencyChangeEvent) event).getCredits());
	}
}
