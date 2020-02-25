package me.dylanmullen.bingo.window.login.comp;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import me.dylanmullen.bingo.window.ui.Panel;

public class LoginComp extends Panel
{

	public LoginComp(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	private JTextField userName;
	private JPasswordField password;
	
	@Override
	public void setup()
	{
		setBounds(x,y,width,height);
		setLayout(null);
		
	}

	@Override
	public void create()
	{
		
	}

}
