package me.dylanmullen.bingo.window.login.comps.cards;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.window.login.LoginButtonListener;
import me.dylanmullen.bingo.window.login.LoginWindow;

public class LoginCard extends Card
{

	private Color button = new Color(0x3498db);

	private JTextField userName;
	private JPasswordField password;

	public LoginCard(LoginWindow lw, String name)
	{
		super(lw, name);
		generateContent();
		styleInput();
		generateLabels();
		styleLabels();
	}

	@Override
	public void generateContent()
	{
		JPanel message = new JPanel();
		message.setBounds(12, 13, 506, 46);
		add(message);
		message.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		message.add(lblNewLabel);

		userName = new JTextField();
		userName.setName("username");
		userName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		userName.setBounds(185, 89, 333, 51);
		add(userName);

		password = new JPasswordField();
		password.setName("password");
		password.setFont(new Font("Tahoma", Font.PLAIN, 20));
		password.setBounds(185, 158, 333, 51);
		add(password);
		
	}

	public void generateLabels()
	{
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(12, 89, 161, 51);
		add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 158, 161, 51);
		add(lblPassword);

		JLabel lblSubmit = new JLabel("Submit");
		lblSubmit.setBounds(12, 227, 506, 59);
		add(lblSubmit);
		buttons.add(lblSubmit);
	}

	public void styleLabels()
	{
		for (Component comp : getComponents())
		{
			JComponent jc = (JComponent) comp;
			if (jc instanceof JLabel)
			{
				JLabel label = (JLabel) jc;
				if (buttons.contains(label))
				{
					styleButton(label);
				}
				label.setFont(new Font("Tahoma", Font.PLAIN, 20));
				label.setForeground(Color.white);
				label.setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
	}

	private void styleInput()
	{
		userName.setMargin(new Insets(10,15,10,15));
		password.setMargin(new Insets(10,15,10,15));
	}
	
	private void styleButton(JLabel label)
	{
		label.setBackground(button);
		label.setOpaque(true);
		label.addMouseListener(new LoginButtonListener(window));
	}

}
