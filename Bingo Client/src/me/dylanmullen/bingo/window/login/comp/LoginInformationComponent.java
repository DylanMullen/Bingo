package me.dylanmullen.bingo.window.login.comp;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.window.login.LoginWindow;
import me.dylanmullen.bingo.window.login.panels.LoginPanel;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.RoundedButton;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.UIPasswordField;
import me.dylanmullen.bingo.window.ui.UITextField;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;
import me.dylanmullen.bingo.window.ui.listeners.LoginButtonListener;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class LoginInformationComponent extends Panel
{

	private static final long serialVersionUID = -6138313483836658937L;

	private LoginPanel panel;
	private UITextField username;
	private UIPasswordField password;
	private List<RoundedButton> buttons;

	/**
	 * Creates the Login Information panel used for logging in users to the Bingo
	 * Server. <br>
	 * This class is commonly used in the {@link LoginWindow} of the Application.
	 * 
	 * @param loginPanel The login panel which houses this Component.
	 * @param x          X-Position of the Component.
	 * @param y          Y-Position of the Component.
	 * @param width      The width of the Component.
	 * @param height     The height of the Component.
	 */
	public LoginInformationComponent(LoginPanel loginPanel, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.panel = loginPanel;
		this.buttons = new ArrayList<RoundedButton>();
	}

	@Override
	public void setup()
	{
		Grid grid = new Grid(new GridSettings(width, height, 3, 2, (width / 100)), 0, 0);

		this.username = new UITextField("Username");
		grid.addGridItem(new GridItem(getUsername(), 1, 2), 0, true);

		this.password = new UIPasswordField("Password");
		grid.addGridItem(new GridItem(getPassword(), 1, 2), 1, true);

		Font font = new Font("Calibri", Font.PLAIN, 25);
		RoundedButton login = new RoundedButton("Login", font, UIColour.BTN_LOGIN);
		login.addMouseListener(new LoginButtonListener(getLoginPanel()));
		getButtons().add(login);
		grid.addGridItem(new GridItem(login, 1, 1), 2, false);

		RoundedButton register = new RoundedButton("Register", font, UIColour.BTN_REGISTER);
		register.addMouseListener(new LoginButtonListener(getLoginPanel()));

		grid.addGridItem(new GridItem(register, 1, 1), 2, true);
		getButtons().add(register);

		setOpaque(false);
	}

	@Override
	public void create()
	{
		setup();
		add(getUsername());
		add(getPassword());

		for (RoundedButton button : getButtons())
		{
			button.create();
			button.setFocusable(true);
			add(button);
		}
	}

	/**
	 * Returns the Username field of the Component.
	 * 
	 * @return {@link #username}
	 */
	public UITextField getUsername()
	{
		return username;
	}

	/**
	 * Returns the Password field of the Component.
	 * 
	 * @return {@link #password}
	 */
	public UIPasswordField getPassword()
	{
		return this.password;
	}

	/**
	 * Returns the Login Panel that this Component belongs to.
	 * 
	 * @return {@link #panel}
	 */
	public LoginPanel getLoginPanel()
	{
		return panel;
	}

	/**
	 * Returns the buttons being used in this Component.
	 * 
	 * @return {@link #buttons}
	 */
	public List<RoundedButton> getButtons()
	{
		return buttons;
	}
}
