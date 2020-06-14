package me.dylanmullen.bingo.net.callbacks;

import java.util.UUID;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.UserInformation;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.window.login.comp.WarningInfoComponent;

public class LoginCallback extends PacketCallback
{

	private WarningInfoComponent warning;

	public LoginCallback(WarningInfoComponent warning)
	{
		this.warning = warning;
	}

	@Override
	public boolean callback()
	{
		if (getReponseValue() == -1)
		{
			synchronized (warning)
			{
				warning.updateText(getMessage().split("/nl/")[2]);
			}
			return false;
		}
		System.out.println(getMessage());
		String[] data = getMessage().split("/nl/");
		UserInformation ui = new UserInformation();
		ui.setUuid(UUID.fromString(data[2]));
		ui.setDisplayName(data[3]);
		ui.setCredits(Double.parseDouble(data[4]));
		ui.setWins(Integer.parseInt(data[5]));
		ui.setLoses(Integer.parseInt(data[6]));
		
		BingoApp.getInstance().openBingoWindow(ui);
		return true;
	}

	private int getReponseValue()
	{
		return Integer.parseInt(getMessage().split("/nl/")[0]);
	}

}
