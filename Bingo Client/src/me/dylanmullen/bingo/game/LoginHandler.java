package me.dylanmullen.bingo.game;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.callbacks.LoginCallback;
import me.dylanmullen.bingo.window.login.comp.WarningInfoComponent;
import me.dylanmullen.bingo.window.login.panels.LoginPanel;
import me.dylanmullen.bingo.window.ui.UIPasswordField;
import me.dylanmullen.bingo.window.ui.UITextField;

public class LoginHandler
{

	private static LoginHandler handler;

	public static LoginHandler getHandler()
	{
		if (handler == null)
			handler = new LoginHandler();
		return handler;
	}

	public void handleLoginRequest(LoginPanel login)
	{
		UITextField username = login.getLoginInfoComponent().getUsername();
		UIPasswordField password = login.getLoginInfoComponent().getPassword();

		if (!checkFields(login, username, password))
			return;

		String data = username.getText() + "/nl/" + new String(password.getPassword());
		PacketHandler.sendPacket(PacketHandler.createPacket(001, data),
				new LoginCallback(login.getWarningInfoComponent()));
	}

	private boolean checkFields(LoginPanel login, UITextField user, UIPasswordField pw)
	{
		boolean valid = true;
		valid = checkUsername(login.getWarningInfoComponent(), user);
		valid = (!valid ? false : checkPassword(login.getWarningInfoComponent(), pw));
		return valid;
	}

	private boolean checkUsername(WarningInfoComponent warning, UITextField user)
	{
		boolean valid = true;
		if (user.isPlaceholder())
		{
			warning.updateText("Invalid Username\nUsername cannot be Username");
			valid = false;
		} else if (user.getText().length() < 3)
		{
			warning.updateText("Invalid Username\nUsername must be more than 3 characters");
			valid = false;
		} else if (user.getText().contains(" "))
		{
			warning.updateText("Invalid Username\nUsername cannot contain a space");
			valid = false;
		}
		return valid;
	}

	private boolean checkPassword(WarningInfoComponent warning, UIPasswordField pw)
	{
		boolean valid = true;
		String password = new String(pw.getPassword());
		if (pw.isPlaceHolder())
		{
			warning.updateText("Invalid Password\nPassword cannot be Password");
			valid = false;
		} else if (password.length() < 3)
		{
			warning.updateText("Invalid Password\nPassword must be more than 3 characters");
			valid = false;
		} else if (password.contains(" "))
		{
			warning.updateText("Invalid Password\nPassword cannot contain a space");
			valid = false;
		}
		return valid;
	}

	public void handleRegisterRequest(LoginPanel login)
	{
		UITextField username = login.getLoginInfoComponent().getUsername();
		UIPasswordField password = login.getLoginInfoComponent().getPassword();

		if (!checkFields(login, username, password))
			return;
		String data = username.getText() + "/nl/" + new String(password.getPassword());
		PacketHandler.sendPacket(PacketHandler.createPacket(002, data), null);
	}
}
