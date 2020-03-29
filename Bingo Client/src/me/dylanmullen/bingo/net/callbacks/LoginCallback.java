package me.dylanmullen.bingo.net.callbacks;

import java.util.UUID;

import me.dylanmullen.bingo.core.BingoApp;
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
		UUID uuid = UUID.fromString(getMessage().split("/nl/")[1]);
		BingoApp.getInstance().openBingoWindow(uuid);
		return true;
	}

	private int getReponseValue()
	{
		return Integer.parseInt(getMessage().split("/nl/")[0]);
	}

}
