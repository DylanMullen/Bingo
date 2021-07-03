package me.dylanmullen.bingo.game;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.gfx.components.login.InformationPanel;
import me.dylanmullen.bingo.gfx.ui.input.InputGroup;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.callbacks.LoginCallback;
import me.dylanmullen.bingo.net.callbacks.RegisterCallback;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.window.login.panels.LoginInformationContainer;
import me.dylanmullen.bingo.window.login.panels.LoginContainer;

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
	 * @param login The login panel which contains the fields.
	 */
	public void handleLoginRequest(LoginInformationContainer login)
	{
		InputGroup username = login.getUsername();
		InputGroup password = login.getPassword();

		if (!checkFields(login.getLoginPanel(), username, password))
			return;

		PacketHandler.sendPacket(constructPacket(1, username.getText(), password.getText()), new LoginCallback(login));
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
	public void handleRegisterRequest(LoginInformationContainer login)
	{
		InputGroup username = login.getUsername();
		InputGroup password = login.getPassword();

		if (!checkFields(login.getLoginPanel(), username, password))
			return;

		PacketHandler.sendPacket(constructPacket(2, username.getText(), password.getText()), new RegisterCallback(login));
	}

	/**
	 * Checks if the field values are valid for the request.
	 * 
	 * @param loginPanel The login panel to update the information component.
	 * @param username   The username field to validate.
	 * @param password   The password field to validate.
	 * @return Returns true if the fields contain valid inputs.
	 */
	private boolean checkFields(LoginContainer loginPanel, InputGroup username, InputGroup password)
	{
		boolean valid = true;
		valid = checkUsername(loginPanel.getLoginInfoComponent(), username);
		valid = (!valid ? false : checkPassword(loginPanel.getLoginInfoComponent(), password));
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
	 * @param username         The username field to validate
	 * @return Returns true if the username is valid.
	 */
	private boolean checkUsername(LoginInformationContainer warningComponent, InputGroup username)
	{
//		if (username.isPlaceholder())
//		{
//			warningComponent.updateText("Invalid Username\nUsername cannot be Username");
//			return false;
//		}
		if (username.getText().length() < 3)
		{
			warningComponent.constructMessage("Invalid Username\nUsername must be more than 3 characters");
			return false;
		}
		if (username.getText().contains(" "))
		{
			warningComponent.constructMessage("Invalid Username\nUsername cannot contain a space");
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
	 * @param password2        The password field to validate.
	 * @return Returns true if the password is valid.
	 */
	private boolean checkPassword(LoginInformationContainer warningComponent, InputGroup password2)
	{
		String password = new String(password2.getText());
//		if (password2.isPlaceHolder())
//		{
//			warningComponent.updateText("Invalid Password\nPassword cannot be Password");
//			return false;
//		}
		if (password.length() < 3)
		{
			warningComponent.constructMessage("Invalid Password\nPassword must be more than 3 characters");
			return false;
		}
		if (password.contains(" "))
		{
			warningComponent.constructMessage("Invalid Password\nPassword cannot contain a space");
			return false;
		}
		return true;
	}

}
