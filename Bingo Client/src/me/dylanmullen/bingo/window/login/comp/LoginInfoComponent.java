package me.dylanmullen.bingo.window.login.comp;

import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.RoundedButton;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.UIPasswordField;
import me.dylanmullen.bingo.window.ui.UITextField;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;
import me.dylanmullen.bingo.window.ui.grid.IGridItem;

public class LoginInfoComponent extends Panel implements IGridItem
{

	private static final long serialVersionUID = -6138313483836658937L;

	private UITextField username;
	private UIPasswordField password;
	private List<RoundedButton> buttons = new ArrayList<>();
	private int indent;

	public LoginInfoComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setBounds(x, y, width, height);
		this.indent = (width / 100) * 5;
	}

	@Override
	public void setup()
	{
		setLayout(null);

		Grid grid = new Grid(new GridSettings(width, height, 3, 2, 5), 0, 0);

		username = new UITextField("Username");
		grid.addGridItem(new GridItem(username, 1, 2), 0);

		password = new UIPasswordField("Password");
		grid.addGridItem(new GridItem(password, 1, 2), 1);

		RoundedButton login = new RoundedButton("Login");
		login.setBackground(UIColour.BTN_LOGIN);
		buttons.add(login);
		grid.addGridItem(new GridItem(login, 1, 1), 2);

		RoundedButton register = new RoundedButton("Register");
		grid.addGridItem(new GridItem(register, 1, 1), 2);
		register.setBackground(UIColour.BTN_REGISTER);
		buttons.add(register);

		grid.updateItems();

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
			add(button);
		}
	}

	@Override
	public void resizeComponents()
	{
		// TODO Auto-generated method stub

	}

}
