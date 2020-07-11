package me.dylanmullen.bingo.net.callbacks;

import java.util.UUID;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.events.events.user.UserInformationChangeEvent;
import me.dylanmullen.bingo.gfx.components.login.InformationPanel;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.window.login.panels.LoginInformationContainer;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class LoginCallback extends PacketCallback
{

	private LoginInformationContainer warningComponent;

	/**
	 * A callback to the Login Packet. <br>
	 * This will take the data received from the server and create a User
	 * Information object and open the Bingo Window using this information.
	 * 
	 * @param warning
	 */
	public LoginCallback(LoginInformationContainer warning)
	{
		this.warningComponent = warning;
	}

	@Override
	public boolean callback()
	{
		if (getResponseType() != 200)
		{
			synchronized (this.warningComponent)
			{
				this.warningComponent.constructMessage(getErrorMessage());
			}
			return false;
		}

		UserInformationChangeEvent userInformation = new UserInformationChangeEvent(
				UUID.fromString(getString("userUUID")), getString("userDisplayName"), getDouble("userCredits"),
				getInteger("userWins"));
		BingoApp.getInstance().openBingoWindow(userInformation);
		return true;
	}

	private String getErrorMessage()
	{
		return getString("errorMessage");
	}

	private String getString(String key)
	{
		return (String) getMessage().get(key);
	}

	private double getDouble(String key)
	{
		return ((Number) getMessage().get(key)).doubleValue();
	}

	private int getInteger(String key)
	{
		return ((Number) getMessage().get(key)).intValue();
	}

}
