package me.dylanmullen.bingo.window.bingo;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;
import me.dylanmullen.bingo.game.UserInformation;
import me.dylanmullen.bingo.game.home.HomePanel;
import me.dylanmullen.bingo.window.Window;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SideMenu;
import me.dylanmullen.bingo.window.ui.TopMenu;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class BingoWindow extends Window
{

	private static final long serialVersionUID = 2893540627273369010L;

	private TopMenu topMenu;
	private SideMenu sideBar;

	private JComponent currentPanel;

	private JScrollPane scrollHomePanel;
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
		this.userInfo = new UserInformation();
		setUndecorated(true);
		showEssentials();
		EventHandler.getHandler().fire(infoEvent);
		showHomePanel();
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
		hideCurrentPanel();

		if (getScrollHomePanel() == null)
		{
			this.scrollHomePanel = new JScrollPane();
			getScrollHomePanel().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			getScrollHomePanel().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			getScrollHomePanel().setPreferredSize(new Dimension(getWidth() / 4 * 3, getHeight() / 10 * 9));
			getScrollHomePanel().setBounds(getWidth() / 4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
			getScrollHomePanel().setBorder(null);
		}
		if (getHomePanel() == null)
		{
			this.home = new HomePanel(this, getWidth() / 4, getHeight() / 10,
					(getWidth() / 4 * 3) - ((Integer) UIManager.get("ScrollBar.width")).intValue(),
					getHeight() - topMenu.getHeight());
		}

		getScrollHomePanel().setViewportView(getHomePanel());
		getSideBar().getHomeButton().setActive(true);

		setCurrentViewPanel(getScrollHomePanel());
		getContentPanel().add(getScrollHomePanel());
	}

	/**
	 * Hides the Home Panel from view.
	 */
	public void hideHomePanel()
	{
		getContentPanel().remove(getScrollHomePanel());
		getSideBar().getHomeButton().setActive(false);
	}

	/**
	 * Hides the current Panel being viewed.<br>
	 * If this value is null, it returns and does nothing.
	 */
	public void hideCurrentPanel()
	{
		if (getCurrentPanel() == null)
			return;

		if (getCurrentPanel() instanceof JScrollPane)
			hideHomePanel();
		getContentPanel().repaint();
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
	 * @return Returns the scrolling pane for the Home Panel.
	 */
	public JScrollPane getScrollHomePanel()
	{
		return this.scrollHomePanel;
	}

	/**
	 * @return Returns the current panel being viewed to the Player.
	 */
	public JComponent getCurrentPanel()
	{
		return this.currentPanel;
	}

	/**
	 * @return Returns the User Information that was retrieved from the Bingo
	 *         Server.
	 */
	public UserInformation getUserInformation()
	{
		return this.userInfo;
	}

	/**
	 * Updates the current viewing panel to the latests one.
	 * 
	 * @param currentPanel The panel to show.
	 */
	public void setCurrentViewPanel(JComponent currentPanel)
	{
		this.currentPanel = currentPanel;
	}
}
