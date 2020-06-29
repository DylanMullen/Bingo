package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.events.Event;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.EventListener;
import me.dylanmullen.bingo.events.events.user.CurrencyChangeEvent;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.RoundedButton;
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
		setBackground(Color.black);
		setForeground(Color.white);
	}

	@Override
	public void setup()
	{
		setBounds(this.x, this.y, this.width, this.height);
		setBorder(new EmptyBorder(12, 12, 12, 12));
		setOpaque(false);
		this.grid = new Grid(new GridSettings(getWidth() - 10, getHeight() - 110, 4, 2, 5), 5, 105);

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
		InformationComponent username = new InformationComponent("Username", "", 0, 0, 0, 0);
		InformationComponent money = new InformationComponent("Money", "", 0, 0, 0, 0);
		InformationComponent wins = new InformationComponent("Wins", "", 0, 0, 0, 0);
		InformationComponent currentGame = new InformationComponent("Playing in", "", 0, 0, 0, 0);
		RoundedButton topUp = new RoundedButton("Top Up", new ButtonInformation(null, null, () ->
		{
		}));
		topUp.updateColours(BingoApp.getInstance().getColourManager().getSet("buttons").getColour("purchase-bg"),
				BingoApp.getInstance().getColourManager().getSet("buttons").getColour("purchase-bg"));

		getGrid().addGridItem(new GridItem(username, 1, 2), 0);
		getGrid().addGridItem(new GridItem(money, 1, 1), 1);
		getGrid().addGridItem(new GridItem(wins, 1, 1), 1);
		getGrid().addGridItem(new GridItem(currentGame, 1, 2), 2);
		getGrid().addGridItem(new GridItem(topUp, 1, 2), 3);
//		username.create();
//		wins.create();
//		money.create();

		getInformationComponents().add(username);
		getInformationComponents().add(money);
		getInformationComponents().add(wins);
		getInformationComponents().add(currentGame);
		add(topUp);
	}

	@Override
	public void create()
	{
		getInformationComponents().stream().forEach(e -> add(e));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);
		g2.setColor(getForeground());
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 15, 15);
		g2.setStroke(new BasicStroke());
	}

	/**
	 * Updates the items of the Information Components to have the most up to date
	 * information that was received from the server.
	 */
	public void updateItems(UserInformationChangeEvent event)
	{
		getInformationComponents().get(0).setText(event.getUsername());
		getInformationComponents().get(1).setText(event.getCredits() + "");
		getInformationComponents().get(2).setText(event.getWins() + "");
	}

	/**
	 * Updates the credits information component.
	 * 
	 * @param credits The new credits value.
	 */
	public void updateCredits(double credits)
	{
		getInformationComponents().get(1).setText(credits + "");
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
