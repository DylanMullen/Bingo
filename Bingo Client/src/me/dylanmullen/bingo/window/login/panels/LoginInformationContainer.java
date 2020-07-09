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
import me.dylanmullen.bingo.gfx.ui.input.InputGroup;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.util.Vector2I;
import me.dylanmullen.bingo.window.login.LoginWindow;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class LoginInformationContainer extends UIPanel
{

	private static final long serialVersionUID = -6138313483836658937L;

	private LoginContainer panel;

	private Grid grid;
	private InputGroup username, password;
	private List<RoundedButton> buttons;
	private UIColourSet set;

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
	public LoginInformationContainer(LoginContainer loginPanel, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.panel = loginPanel;
		this.buttons = new ArrayList<RoundedButton>();
		this.set = BingoApp.getInstance().getColourManager().getSet("buttons");
		setBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("side-primary").toColour());
	}

	@Override
	public void setup()
	{
		showInput();
//		setOpaque(false);
	}

	@Override
	public void create()
	{
		setup();
		for (RoundedButton button : getButtons())
			add(button);
	}

	public void showInput()
	{
		grid = new Grid(new GridSettings(getWidth(), getHeight() / 4 * 2 - 10, 2, 2, (width / 100)), 0, 5);
		
		username=createInput("Username", true);
		password = createInput("Password", true);
		grid.addGridItem(new GridItem(username, 1, 2), 0, false);
		grid.addGridItem(new GridItem(password, 1, 2), 1, false);
		grid.updatePositions();

		grid.getItems().stream().forEach(e ->
		{
			add(e.getComponent());
			InputGroup input = (InputGroup) e.getComponent();
			input.setupShapes();
		});
//
//		RoundedButton login = new RoundedButton("Login",
//				new ButtonInformation(new Vector2I(5, getHeight() / 2 + (getHeight() / 4) / 2),
//						new Vector2I(getWidth() / 2 - 10, getHeight() / 4), () ->
//						{
//							LoginHandler.getHandlerInstance().handleLoginRequest(this);
//						}));
//		RoundedButton register = new RoundedButton("Register",
//				new ButtonInformation(new Vector2I(15 + getWidth() / 2 - 10, getHeight() / 2 + (getHeight() / 4) / 2),
//						new Vector2I(getWidth() / 2 - 10, getHeight() / 4), () ->
//						{
//
//						}));
//		login.updateColours(set.getColour("login-bg"), set.getColour("login-bg").darken(0.15));
//		register.updateColours(set.getColour("register-bg"), set.getColour("register-bg").darken(0.15));
//		add(login);
//		add(register);
	}

	private InputGroup createInput(String span, boolean hidden)
	{
		return new InputGroup(span, null, null,
				BingoApp.getInstance().getColourManager().getSet("frame").getColour("content").darken(0.15), hidden);
	}

	public InputGroup getPassword()
	{
		return password;
	}
	
	public InputGroup getUsername()
	{
		return username;
	}
	
	/**
	 * Returns the Login Panel that this Component belongs to.
	 * 
	 * @return {@link #panel}
	 */
	public LoginContainer getLoginPanel()
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
