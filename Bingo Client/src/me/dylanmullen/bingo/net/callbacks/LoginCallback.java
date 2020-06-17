package me.dylanmullen.bingo.net.callbacks;

import java.util.UUID;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.UserInformation;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.window.login.comp.WarningInfoComponent;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class LoginCallback extends PacketCallback
{

	private WarningInfoComponent warningComponent;

	/**
	 * A callback to the Login Packet. <br>
	 * This will take the data received from the server and create a User
	 * Information object and open the Bingo Window using this information.
	 * 
	 * @param warning
	 */
	public LoginCallback(WarningInfoComponent warning)
	{
		this.warningComponent = warning;
	}

	@Override
	public boolean callback()
	{
		if (getReponseValue() == -1)
		{
			synchronized (this.warningComponent)
			{
				this.warningComponent.updateText(getMessage().split("/nl/")[2]);
			}
			return false;
		}
		String[] data = getMessage().split("/nl/");

		UserInformation ui = new UserInformation();
		ui.setUUID(UUID.fromString(data[2]));
		ui.setDisplayName(data[3]);
		ui.setCredits(Double.parseDouble(data[4]));
		ui.setWins(Integer.parseInt(data[5]));
		ui.setLoses(Integer.parseInt(data[6]));

		BingoApp.getInstance().openBingoWindow(ui);
		return true;
	}

	/**
	 * Returns the response value of the response packet.
	 * 
	 * @return Response value of the response packet.
	 */
	private int getReponseValue()
	{
		return Integer.parseInt(getMessage().split("/nl/")[0]);
	}

}
