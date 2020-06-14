package me.dylanmullen.bingo.window.ui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import me.dylanmullen.bingo.game.LoginHandler;
import me.dylanmullen.bingo.window.login.panels.LoginPanel;
import me.dylanmullen.bingo.window.ui.RoundedButton;

public class LoginButtonListener extends MouseAdapter
{

	private LoginPanel login;

	public LoginButtonListener(LoginPanel loginPanel)
	{
		this.login = loginPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		JComponent comp = (JComponent) e.getComponent();
		if (!(comp instanceof RoundedButton))
		{
			return;
		}

		RoundedButton button = (RoundedButton) comp;
		String text = button.getText();
		switch (text)
		{
			case "Login":
				LoginHandler.getHandler().handleLoginRequest(login);
				break;
			case "Register":
				LoginHandler.getHandler().handleRegisterRequest(login);
				break;
			default:
				break;
		}
	}

}
