package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.image.ImageAtlas;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonListener;
import me.dylanmullen.bingo.gfx.ui.buttons.SidePanelButton;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.util.Vector2I;
import me.dylanmullen.bingo.window.bingo.BingoWindow;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class SideMenu extends UIPanel
{

	private static final long serialVersionUID = 7856700131153385897L;

	private BingoWindow bingoWindow;

	private ProfilePanel profilePanel;
	private SidePanelButton homeButton;
	private SidePanelButton playButton;
	private SidePanelButton settingsButton;

	private UIColourSet set;
	private ImageAtlas uiIcons;

	/**
	 * This the the side navigation bar for the application.<br>
	 * This contains the profile panel which displays the information of the player
	 * from the server.<br>
	 * This also contains the buttons for the navigation of the application.
	 * 
	 * @param bingoWindow The Bingo Window of the Application.
	 * @param x           X-Position of the Side Menu.
	 * @param y           Y-Position of the Side Menu.
	 * @param width       The width of the Side Menu.
	 * @param height      The height of the Side Menu.
	 */
	public SideMenu(BingoWindow bingoWindow, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.bingoWindow = bingoWindow;
		this.set = BingoApp.getInstance().getColourManager().getSet("frame");
		this.uiIcons = BingoApp.getInstance().getAtlastManager().getAtlas("uiAtlas", 64);
		setBackground(set.getColour("side-primary").toColour());
		setForeground(set.getColour("side-primary").lighten(0.25).toColour());
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setOpaque(false);
	}

	@Override
	public void create()
	{
		this.profilePanel = new ProfilePanel(set.getColour("profile"), 30, 30, getWidth() - 60,
				(int) (getHeight() / 2.75));
		getProfilePanel().setup();
		add(getProfilePanel());
		createButtons();
	}

	private Grid grid;

	/**
	 * Creates the side panel buttons and adds them to the Button Container.
	 */
	private void createButtons()
	{
		grid = new Grid(new GridSettings(getWidth(), getHeight() / 2, -1, 1, 0), 0, getHeight() / 2);
		grid.getGridSettings().setFixedRowHeight(58);

		grid.addGridItem(new GridItem(createButton("Home", new Vector2I(0, 0), () ->
		{
			System.out.println("Not implemented");
		}), 1, 1), 0);
		grid.addGridItem(new GridItem(createButton("Bingo", new Vector2I(1, 0), () ->
		{
			System.out.println("Not implemented");
		}), 1, 1), 1);
		grid.addGridItem(new GridItem(createButton("Friends", new Vector2I(3, 0), () ->
		{
			System.out.println("Not implemented");
		}), 1, 1), 2);
		grid.addGridItem(new GridItem(createButton("Settings", new Vector2I(2, 0), () ->
		{
			System.out.println("Not implemented");
		}), 1, 1), 3);
		grid.addGridItem(new GridItem(createButton("Logout", new Vector2I(4, 1), () ->
		{
			System.out.println("Not implemented");
		}), 1, 1), 4);

		grid.updatePositions();
		for (List<GridItem> item : grid.getGridItems().values())
			item.stream().forEach(e -> {
				SidePanelButton button = (SidePanelButton)e.getComponent();
				button.setup();
				add(button);	
			});
	}

	private SidePanelButton createButton(String name, Vector2I textureCoords, ButtonListener listener)
	{
		Color color = BingoApp.getInstance().getColourManager().getSet("buttons").getColour("sidepanel-active")
				.toColour();
		return new SidePanelButton(name,
				uiIcons.getImage(textureCoords.getX(), textureCoords.getY(), Color.WHITE, color),
				new ButtonInformation(null, null, listener));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), (int) (getHeight() / 2));
		g.setColor(getForeground());
		g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);
		g.setColor(set.getColour("side-primary").lighten(0.15).toColour());
		g.fillRect(0, getHeight() / 2 - 10, getWidth(), 10);
		super.paintComponent(g);
	}

	/**
	 * @return Returns the Profile Panel of the Side Menu.
	 */
	public ProfilePanel getProfilePanel()
	{
		return this.profilePanel;
	}

	/**
	 * @return Returns the Home Button.
	 */
	public SidePanelButton getHomeButton()
	{
		return this.homeButton;
	}

	/**
	 * @return Returns the Play Button.
	 */
	public SidePanelButton getPlayButton()
	{
		return this.playButton;
	}

	/**
	 * @return Returns the Settings Button.
	 */
	public SidePanelButton getSettingsButton()
	{
		return this.settingsButton;
	}

	/**
	 * @return Returns the Bingo Window of the Application.
	 */
	public BingoWindow getBingoWindow()
	{
		return this.bingoWindow;
	}

}
