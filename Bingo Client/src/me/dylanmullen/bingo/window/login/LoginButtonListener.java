package me.dylanmullen.bingo.window.login;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.handlers.ClientHandler;

public class LoginButtonListener implements MouseListener
{

	private LoginWindow window;

	private boolean active = false;

	private Color button = new Color(0x3498db);
	private Color button_hovered = new Color(0x2980b9);

	public LoginButtonListener(LoginWindow lw)
	{
		this.window = lw;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		JLabel label = (JLabel) e.getComponent();

		if (label.getParent().getName() == null)
			return;

		if (label.getParent().getName().startsWith("l_c_"))
		{
			handleCardButtons(label.getParent());
			return;
		}
		if (label.getParent().getName().equalsIgnoreCase("l_side"))
		{
			String text = label.getText();
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
	}

	private void handleCardButtons(Container c)
	{
		String card = c.getName();
		System.out.println("test");
		if (card.contains("login"))
		{
			String format = "?username/nl/?password";
			for (Component comp : c.getComponents())
			{
				if (comp instanceof JTextField)
				{
					JTextField tx = (JTextField) comp;
					format = format.replace("?username", tx.getText());
				}
				if (comp instanceof JPasswordField)
				{
					JPasswordField tx = (JPasswordField) comp;
					format = format.replace("?password", new String(tx.getPassword()));
				}
			}
			PacketHandler.sendPacket(PacketHandler.createPacket(001, format));
		}
	}

}
