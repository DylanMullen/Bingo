package me.dylanmullen.bingo.window.login;

import me.dylanmullen.bingo.gfx.components.shared.TopMenu;
import me.dylanmullen.bingo.window.Window;
import me.dylanmullen.bingo.window.login.panels.LoginContainer;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class LoginWindow extends Window
{

	private static final long serialVersionUID = -4105651394392717980L;

	private TopMenu topMenu;
	private LoginContainer loginPanel;

	/**
	 * Create the Login Window for the Bingo Application.<br>
	 * This window is default set at 450x400 and should not be changed.
	 */
	public LoginWindow()
	{
		super("Login Window", 768, 432);
		setUndecorated(true);
		createComponents();
	}

	/**
	 * This method setups the components being used inside of the Login Window and
	 * adds them to the Window.<br>
	 * <h1>Components</h1>
	 * <ul>
	 * <li>{@link #topMenu}</li>
	 * <li>{@link #loginPanel}</li>
	 * </ul>
	 */
	public void createComponents()
	{
		this.topMenu = new TopMenu(this, 0, 0, getWidth(), (int) ((getHeight() / 10) * 1.25));
		getTopMenu().setup();
		getTopMenu().create();

		this.loginPanel = new LoginContainer(0, getTopMenu().getHeight(), getWidth(),
				getHeight() - getTopMenu().getHeight());
		getLoginPanel().setup();
		getLoginPanel().create();

		getContentPanel().add(getTopMenu());
		getContentPanel().add(getLoginPanel());

		getContentPanel().requestFocusInWindow();

	}

	/**
	 * Returns the Top Menu of the Login Window.
	 * 
	 * @return {@link #topMenu}
	 */
	public TopMenu getTopMenu()
	{
		return this.topMenu;
	}

	/**
	 * Returns the Login Panel of the Window.
	 * 
	 * @return {@link #loginPanel}
	 */
	public LoginContainer getLoginPanel()
	{
		return this.loginPanel;
	}
}
