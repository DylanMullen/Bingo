package me.dylanmullen.bingo.game;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.gfx.components.login.WarningInfoComponent;
import me.dylanmullen.bingo.gfx.ui.input.UIPasswordField;
import me.dylanmullen.bingo.gfx.ui.input.UITextField;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.callbacks.LoginCallback;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.window.login.panels.LoginPanel;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class LoginHandler
{

	private static LoginHandler handler;

	/**
	 * Returns the LoginHandler instance
	 * 
	 * @return {@link #handler}
	 */
	public static LoginHandler getHandlerInstance()
	{
		if (LoginHandler.handler == null)
			LoginHandler.handler = new LoginHandler();
		return LoginHandler.handler;
	}

	/**
	 * Handles the login request made by the user. <br>
	 * This will make a client side validation check before sending the request to
	 * the server.
	 * 
	 * @param loginPanel The login panel which contains the fields.
	 */
	public void handleLoginRequest(LoginPanel loginPanel)
	{
		UITextField username = loginPanel.getLoginInfoComponent().getUsername();
		UIPasswordField password = loginPanel.getLoginInfoComponent().getPassword();

		if (!checkFields(loginPanel, username, password))
			return;

		PacketHandler.sendPacket(constructPacket(1,username.getText(), new String(password.getPassword())),
				new LoginCallback(loginPanel.getWarningInfoComponent()));
	}

	@SuppressWarnings("unchecked")
	private Packet constructPacket(int id, String username, String password)
	{
		JSONObject message = new JSONObject();
		message.put("email", username);
		message.put("password", password);

		Packet packet = PacketHandler.createPacket(id, message);
		return packet;
	}

	/**
	 * Handles the register request made by the user. <br>
	 * This will make a client side validation check before sending the request to
	 * the server.
	 * 
	 * @param loginPanel The login panel which contains the fields.
	 */
	public void handleRegisterRequest(LoginPanel loginPanel)
	{
		UITextField username = loginPanel.getLoginInfoComponent().getUsername();
		UIPasswordField password = loginPanel.getLoginInfoComponent().getPassword();

		if (!checkFields(loginPanel, username, password))
			return;
		PacketHandler.sendPacket(constructPacket(2,username.getText(), new String(password.getPassword())), null);
	}

	/**
	 * Checks if the field values are valid for the request.
	 * 
	 * @param loginPanel    The login panel to update the information component.
	 * @param usernameField The username field to validate.
	 * @param passwordField The password field to validate.
	 * @return Returns true if the fields contain valid inputs.
	 */
	private boolean checkFields(LoginPanel loginPanel, UITextField usernameField, UIPasswordField passwordField)
	{
		boolean valid = true;
		valid = checkUsername(loginPanel.getWarningInfoComponent(), usernameField);
		valid = (!valid ? false : checkPassword(loginPanel.getWarningInfoComponent(), passwordField));
		return valid;
	}

	/**
	 * Checks if the username is valid.<br>
	 * The validation checks:
	 * <ul>
	 * <li>Username field is the placeholder value</li>
	 * <li>Username field is below 3 characters</li>
	 * <li>Username field contains a space</li>
	 * </ul>
	 * 
	 * @param warningComponent The warning component to update if invalid
	 * @param usernameField    The username field to validate
	 * @return Returns true if the username is valid.
	 */
	private boolean checkUsername(WarningInfoComponent warningComponent, UITextField usernameField)
	{
		if (usernameField.isPlaceholder())
		{
			warningComponent.updateText("Invalid Username\nUsername cannot be Username");
			return false;
		}
		if (usernameField.getText().length() < 3)
		{
			warningComponent.updateText("Invalid Username\nUsername must be more than 3 characters");
			return false;
		}
		if (usernameField.getText().contains(" "))
		{
			warningComponent.updateText("Invalid Username\nUsername cannot contain a space");
			return false;
		}
		return true;
	}

	/**
	 * Checks if the password is valid.<br>
	 * The validation checks:
	 * <ul>
	 * <li>Password field is the placeholder value</li>
	 * <li>Password field is below 3 characters</li>
	 * <li>Password field contains a space</li>
	 * </ul>
	 * 
	 * @param warningComponent The warrning component to update if invalid.
	 * @param passwordField    The password field to validate.
	 * @return Returns true if the password is valid.
	 */
	private boolean checkPassword(WarningInfoComponent warningComponent, UIPasswordField passwordField)
	{
		String password = new String(passwordField.getPassword());
		if (passwordField.isPlaceHolder())
		{
			warningComponent.updateText("Invalid Password\nPassword cannot be Password");
			return false;
		}
		if (password.length() < 3)
		{
			warningComponent.updateText("Invalid Password\nPassword must be more than 3 characters");
			return false;
		}
		if (password.contains(" "))
		{
			warningComponent.updateText("Invalid Password\nPassword cannot contain a space");
			return false;
		}
		return true;
	}

}
