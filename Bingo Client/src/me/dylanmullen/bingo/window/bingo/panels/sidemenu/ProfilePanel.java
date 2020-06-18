package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.window.ui.ImageComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class ProfilePanel extends Panel
{

	private static final long serialVersionUID = 3952772083758555561L;

	private Grid grid;
	private List<InfoComponent> infoComponents = new ArrayList<>();

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
			// TODO Auto-generated catch block
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
		InfoComponent username = new InfoComponent("Username", "Loading...", 0, 0, 0, 0);
		InfoComponent money = new InfoComponent("Money", "Loading...", 0, 0, 0, 0);
		InfoComponent wins = new InfoComponent("Wins", "Loading...", 0, 0, 0, 0);
		InfoComponent ratio = new InfoComponent("Win/Lose %", "Loading...", 0, 0, 0, 0);

		getGrid().addGridItem(new GridItem(username, 1, 1), 0);
		getGrid().addGridItem(new GridItem(money, 1, 1), 1);
		getGrid().addGridItem(new GridItem(wins, 1, 1), 2);
		getGrid().addGridItem(new GridItem(ratio, 1, 1), 3);
		username.setup();
		ratio.setup();
		wins.setup();
		money.setup();
		getInformationComponents().add(username);
		getInformationComponents().add(money);
		getInformationComponents().add(wins);
		getInformationComponents().add(ratio);
	}

	@Override
	public void create()
	{
		updateItems();
		for (InfoComponent item : getInformationComponents())
		{
			add(item);
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(UIColour.BTN_FAILURE.toColor());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		super.paintComponent(g);

	}

	/**
	 * Updates the items of the Information Components to have the most up to date
	 * information that was received from the server.
	 */
	public void updateItems()
	{
		if (BingoGame.getInstance() != null)
		{
			getInformationComponents().get(0).getInfo()
					.setText(BingoGame.getInstance().getUserInformation().getDisplayName());
			getInformationComponents().get(1).getInfo()
					.setText(BingoGame.getInstance().getUserInformation().getCredits() + "");
			getInformationComponents().get(2).getInfo()
					.setText(BingoGame.getInstance().getUserInformation().getWins() + "");
			getInformationComponents().get(3).getInfo().setText("N/A");
		}
	}

	/**
	 * Updates the credits information component.
	 * 
	 * @param credits The new credits value.
	 */
	public void updateCredits(double credits)
	{
		getInformationComponents().get(1).getInfo().setText(credits + "");
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
	public List<InfoComponent> getInformationComponents()
	{
		return this.infoComponents;
	}
}
