package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.window.bingo.BingoWindow;
import me.dylanmullen.bingo.window.bingo.ui.buttons.ButtonContainer;
import me.dylanmullen.bingo.window.bingo.ui.buttons.SidePanelButton;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class SideMenu extends Panel
{

	private static final long serialVersionUID = 7856700131153385897L;

	private BingoWindow bingoWindow;

	private ProfilePanel profilePanel;
	private SidePanelButton homeButton;
	private SidePanelButton playButton;
	private SidePanelButton settingsButton;

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
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG_TOP.toColor());
	}

	@Override
	public void create()
	{
		this.profilePanel = new ProfilePanel(30, 30, getWidth() - 60, (int) (getHeight() / 2.75));
		getProfilePanel().setup();
		add(getProfilePanel());
		createButtons();
	}

	/**
	 * Creates the side panel buttons and adds them to the Button Container.
	 */
	private void createButtons()
	{
		ButtonContainer buttons = new ButtonContainer(0, getHeight() / 2, getWidth(), getHeight() / 2)
				.setButtonHeight(60);

		this.homeButton = new SidePanelButton("Home", new Dimension(1, 0));
		getHomeButton().addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (!getHomeButton().isActive())
				{
					getBingoWindow().showHomePanel();
				}
			}
		});
		this.playButton = new SidePanelButton("Play", new Dimension(2, 0));
		getPlayButton().addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (!getPlayButton().isActive())
				{
					getBingoWindow().showBingoPanel();
				}
			}
		});
		this.settingsButton = new SidePanelButton("Settings", new Dimension(3, 0));

		buttons.addButton(getHomeButton());
		buttons.addButton(getPlayButton());
		buttons.addButton(getSettingsButton());

		buttons.populate();
		buttons.setBackground(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		add(buttons);
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
