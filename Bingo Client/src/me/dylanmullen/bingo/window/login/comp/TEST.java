package me.dylanmullen.bingo.window.login.comp;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.window.ui.UIColour;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class TEST extends JPanel
{
	private JTextField username;
	private JPasswordField passwordField;

	/**
	 * Create the panel.
	 */
	public TEST()
	{
		setBounds(0, 60, 450, 540);
		setLayout(null);
		
		setBackground(UIColour.FRAME_BINGO_BG_TOP.toColor());
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setFont(new Font("Calibri", Font.PLAIN, 24));
		usernameLabel.setBounds(12, 150, 213, 60);
		usernameLabel.setForeground(UIColour.BTN_BINGO_TEXT.toColor());
		add(usernameLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblPassword.setFont(new Font("Calibri", Font.PLAIN, 24));
		lblPassword.setForeground(UIColour.BTN_BINGO_TEXT.toColor());
		lblPassword.setBounds(12, 234, 213, 60);
		add(lblPassword);
		
		username = new JTextField();
		username.setBounds(225, 150, 213, 60);
		add(username);
		username.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(225, 234, 213, 60);
		add(passwordField);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 318, 426, 52);
		add(panel);
	}
}
