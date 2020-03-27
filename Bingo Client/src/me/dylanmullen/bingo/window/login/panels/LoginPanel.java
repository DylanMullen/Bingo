package me.dylanmullen.bingo.window.login.panels;

import me.dylanmullen.bingo.window.login.comp.LoginInfoComponent;
import me.dylanmullen.bingo.window.login.comp.ServerInfoComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class LoginPanel extends Panel
{

	private static final long serialVersionUID = 4140252095370157896L;

	private LoginInfoComponent loginInfoComponent;

	public LoginPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG.toColor());

		loginInfoComponent = new LoginInfoComponent(25, 25, getWidth() - 50, (height / 8 * 7) - 50);
		loginInfoComponent.create();
	}

	@Override
	public void create()
	{
		setup();
		add(loginInfoComponent);
	}

}
