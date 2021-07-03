package me.dylanmullen.bingo.net.callbacks;

import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.window.login.panels.LoginInformationContainer;

public class RegisterCallback extends PacketCallback
{

	private LoginInformationContainer warningComponent;

	public RegisterCallback(LoginInformationContainer warning)
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
		
		warningComponent.constructMessage("Registration Complete");
		return false;
	}

	private String getErrorMessage()
	{
		return getString("errorMessage");
	}
	
	private String getString(String key)
	{
		return (String) getMessage().get(key);
	}
}
