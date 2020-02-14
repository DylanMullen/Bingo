package me.dylanmullen.bingo.window.login;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class LoginButtonListener implements MouseListener
{

	private LoginWindow window;
	
	private boolean active = false;

	private Color button = new Color(0x3498db);
	private Color button_hovered = new Color(0x2980b9);

	public LoginButtonListener(LoginWindow lw)
	{
		this.window=lw;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		JLabel label = (JLabel) e.getComponent();
		if(label.getParent().getName().equalsIgnoreCase("l_side"))
		{
			String text = label.getText();
			System.out.println(text.toLowerCase());
			window.getLoginContent().getCl().show(window.getLoginContent(), text.toLowerCase());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		e.getComponent().setBackground((!active ? button_hovered : button));
		active = !active;
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		e.getComponent().setBackground((!active ? button_hovered : button));
		active = !active;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}
