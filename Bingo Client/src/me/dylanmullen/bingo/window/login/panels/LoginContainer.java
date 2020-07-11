package me.dylanmullen.bingo.window.login.panels;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class LoginContainer extends UIPanel
{

	private static final long serialVersionUID = 4140252095370157896L;

	private LoginInformationContainer loginInfoComponent;

	public LoginContainer(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("content").toColour());
		loginInfoComponent = new LoginInformationContainer(this, getWidth() - getWidth() / 3, 0, (int) (getWidth() / 3),
				getHeight());
		loginInfoComponent.create();
	}

	@Override
	public void create()
	{
		add(loginInfoComponent);
	}

	public LoginInformationContainer getLoginInfoComponent()
	{
		return loginInfoComponent;
	}
}
