package me.dylanmullen.bingo.window.login.panels;

import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.LoginHandler;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.RoundedButton;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.input.UIPasswordField;
import me.dylanmullen.bingo.gfx.ui.input.UITextField;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.window.login.LoginWindow;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class LoginInformationPanel extends UIPanel
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
	public LoginInformationPanel(LoginPanel loginPanel, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.panel = loginPanel;
		this.buttons = new ArrayList<RoundedButton>();
	}

	@Override
	public void setup()
	{
		Grid grid = new Grid(new GridSettings(width, height, 3, 2, (width / 100)), 0, 0);

		UIColourSet set = BingoApp.getInstance().getColourManager().getSet("loginInput");
		this.username = new UITextField("Username", set);
		grid.addGridItem(new GridItem(getUsername(), 1, 2), 0, true);

		this.password = new UIPasswordField("Password", set);
		grid.addGridItem(new GridItem(getPassword(), 1, 2), 1, true);

		RoundedButton login = new RoundedButton("Login", new ButtonInformation(null, null, () ->
		{
			LoginHandler.getHandlerInstance().handleLoginRequest(getLoginPanel());
		}));
		getButtons().add(login);
		grid.addGridItem(new GridItem(login, 1, 1), 2, false);

		RoundedButton register = new RoundedButton("Register", new ButtonInformation(null, null, () ->
		{
			LoginHandler.getHandlerInstance().handleRegisterRequest(getLoginPanel());
		}));

		set = BingoApp.getInstance().getColourManager().getSet("buttons");
		register.updateColours(set.getColour("register-bg"), set.getColour("register-active"));
		login.updateColours(set.getColour("login-bg"), set.getColour("login-active"));

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
			add(button);
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
