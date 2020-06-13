package me.dylanmullen.bingo.window.login.comp;

import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.window.login.panels.LoginPanel;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.RoundedButton;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.UIPasswordField;
import me.dylanmullen.bingo.window.ui.UITextField;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;
import me.dylanmullen.bingo.window.ui.grid.IGridItem;
import me.dylanmullen.bingo.window.ui.listeners.LoginButtonListener;

public class LoginInfoComponent extends Panel implements IGridItem
{

	private static final long serialVersionUID = -6138313483836658937L;

	private LoginPanel panel;
	private UITextField username;
	private UIPasswordField password;
	private List<RoundedButton> buttons = new ArrayList<>();

	public LoginInfoComponent(LoginPanel panel, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.panel = panel;
		setBounds(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setLayout(null);

		Grid grid = new Grid(new GridSettings(width, height, 3, 2, (width / 100)), 0, 0);

		username = new UITextField("Username");
		grid.addGridItem(new GridItem(username, 1, 2), 0, true);

		password = new UIPasswordField("Password");
		grid.addGridItem(new GridItem(password, 1, 2), 1, true);

		RoundedButton login = new RoundedButton("Login", UIColour.BTN_LOGIN);
		login.addMouseListener(new LoginButtonListener(panel));
		buttons.add(login);
		grid.addGridItem(new GridItem(login, 1, 1), 2, false);

		RoundedButton register = new RoundedButton("Register", UIColour.BTN_REGISTER);
		grid.addGridItem(new GridItem(register, 1, 1), 2, true);
		buttons.add(register);

//		grid.updateItems();

		setOpaque(false);
	}

	@Override
	public void create()
	{
		setup();
		add(username);
		add(password);

		for (RoundedButton button : buttons)
		{
			button.create();
			button.setFocusable(true);
			add(button);
		}
	}

	@Override
	public void resizeComponents()
	{
	}

	public UIPasswordField getPassword()
	{
		return password;
	}

	public UITextField getUsername()
	{
		return username;
	}
}
