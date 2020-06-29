package me.dylanmullen.bingo.window.bingo;

import java.util.UUID;

import javax.swing.UIManager;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;
import me.dylanmullen.bingo.game.GamePanel;
import me.dylanmullen.bingo.game.UserInformation;
import me.dylanmullen.bingo.game.droplet.BingoCloud;
import me.dylanmullen.bingo.game.droplet.BingoDroplet;
import me.dylanmullen.bingo.game.droplet.DropletManager;
import me.dylanmullen.bingo.game.home.HomePanel;
import me.dylanmullen.bingo.gfx.components.shared.TopMenu;
import me.dylanmullen.bingo.window.Window;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SideMenu;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class BingoWindow extends Window
{

	private static final long serialVersionUID = 2893540627273369010L;

	private static BingoWindow window;

	public static BingoWindow getWindow()
	{
		return window;
	}

	private TopMenu topMenu;
	private SideMenu sideBar;

	private Container container;

	private HomePanel home;
	private UserInformation userInfo;

	/**
	 * Creates the Bingo Window using the default sizing inherited from
	 * {@link Window}.
	 * 
	 * @param userInformation The user information received from the Bingo Server
	 *                        during the login phase.
	 */
	public BingoWindow(UserInformationChangeEvent infoEvent)
	{
		super("Bingo Window");
		DropletManager.getManager();
		if (window == null)
			window = this;
		this.userInfo = new UserInformation();
		setUndecorated(true);
		showEssentials();
		if (infoEvent != null)
			EventHandler.getHandler().fire(infoEvent);
		this.container = new Container(getSideBar().getWidth(), getTopMenu().getHeight(),
				getWidth() - getSideBar().getWidth(), getHeight() - getTopMenu().getHeight());
		add(container);
		showHomePanel();
//		showBingoCloud();
	}

	/**
	 * Shows the essential components that are required to be shown. <br>
	 * These components are:
	 * <ul>
	 * <li>The Sidebar navigation</li>
	 * <li>The top menu</li>
	 * </ul>
	 */
	public void showEssentials()
	{
		this.sideBar = new SideMenu(this, 0, 0, getWidth() / 4, getHeight());
		getSideBar().setup();
		getSideBar().create();
		getContentPanel().add(getSideBar());

		this.topMenu = new TopMenu(this, getWidth() / 4, 0, getWidth() / 4 * 3, getHeight() / 10);
		getTopMenu().setup();
		getTopMenu().create();
		getContentPanel().add(getTopMenu());
	}

	/**
	 * Sets the Home Panel as the current viewing pane and shows it to the Player.
	 */
	public void showHomePanel()
	{
		if (getHomePanel() == null)
		{
			this.home = new HomePanel(this, getWidth() / 4, getHeight() / 10,
					(getWidth() / 4 * 3) - ((Integer) UIManager.get("ScrollBar.width")).intValue(),
					getHeight() - topMenu.getHeight());
		}
//		getSideBar().getHomeButton().setActive(true);
		container.setScrollCurrentPanel(getHomePanel());
	}

	public void showBingoCloud(UUID cloudUUID, JSONObject object)
	{
		BingoCloud cloud = new BingoCloud(cloudUUID, object, container.getWidth(), container.getHeight());
		container.setScrollCurrentPanel(cloud);
	}

	public void showDroplet(BingoDroplet droplet)
	{
		GamePanel panel = droplet.getGamePanel();
		panel.setBounds(0, 0, container.getWidth(), container.getHeight());
		panel.setup();
		panel.create();
		panel.repaint();
		container.setCurrentPanel(panel);
	}

	/**
	 * Hides the Home Panel from view.
	 */
	public void hideHomePanel()
	{
		getSideBar().getHomeButton().setActive(false);
		container.removeCurrent();
	}

	/**
	 * @return Returns the side navigation bar.
	 */
	public SideMenu getSideBar()
	{
		return this.sideBar;
	}

	/**
	 * @return Returns the top menu bar.
	 */
	public TopMenu getTopMenu()
	{
		return this.topMenu;
	}

	/**
	 * @return Returns the Home Panel.
	 */
	public HomePanel getHomePanel()
	{
		return this.home;
	}

	/**
	 * @return Returns the User Information that was retrieved from the Bingo
	 *         Server.
	 */
	public UserInformation getUserInformation()
	{
		return this.userInfo;
	}

}
