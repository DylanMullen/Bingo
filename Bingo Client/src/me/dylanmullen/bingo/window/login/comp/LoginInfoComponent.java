package me.dylanmullen.bingo.window.login.comp;

import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.UIPasswordField;
import me.dylanmullen.bingo.window.ui.UITextField;

public class LoginInfoComponent extends Panel
{

	private static final long serialVersionUID = -6138313483836658937L;

	private UITextField username;
	private UIPasswordField password;
	private List<RoundedButton> buttons=new ArrayList<>();
	
	public LoginInfoComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);

		username = new UITextField("Username", new int[] { 0, 0, getWidth(), getHeight() / 6 });
		password = new UIPasswordField("Password",
				new int[] { 0, (getHeight() / 6) + 15, getWidth(), getHeight() / 6 });
		
		RoundedButton login = new RoundedButton("Login", 0, password.getHeight() + password.getY() + 15,
				getWidth() / 2 - 5, getHeight() / 8);
		login.create();
		login.setBackground(UIColour.BTN_LOGIN);
		buttons.add(login);
		
		RoundedButton register = new RoundedButton("Register", login.getWidth() + 5,
				password.getHeight() + password.getY() + 15, getWidth() / 2 - 5, getHeight() / 8);
		register.create();
		register.setBackground(UIColour.BTN_REGISTER);
		buttons.add(register);
		
		setOpaque(false);
	}

	@Override
	public void create()
	{
		setup();
		add(username);
		add(password);
		
		for(RoundedButton button : buttons)
			add(button);
	}

}
