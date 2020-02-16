package me.dylanmullen.bingo.window.login.comps;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.window.login.LoginButtonListener;
import me.dylanmullen.bingo.window.login.LoginWindow;

public class LoginSideMenu extends JPanel
{

	private Color button = new Color(0x3498db);
	private Color button_hovered = new Color(0x2980b9);

	private ArrayList<JLabel> buttons = new ArrayList<>();

	public LoginSideMenu(LoginWindow lw)
	{
		super();
		setName("l_side");
		setLayout(null);
		generateLabels();
		styleLabels(lw);
	}

	private void generateLabels()
	{
		JLabel lblHome = new JLabel("Home");
		JLabel lblLogin = new JLabel("Login");
		JLabel lblRegister = new JLabel("Register");
		lblLogin.setBounds(0, 140, 220, 50);
//		lblRegister.setBounds(0, 235, 220, 50);
//		lblHome.setBounds(0, 45, 220, 50);

		buttons.add(lblHome);
		buttons.add(lblLogin);
		buttons.add(lblRegister);
	}

	private void styleLabels(LoginWindow lw)
	{
		for (int i = 0; i < buttons.size(); i++)
		{
			JLabel label = buttons.get(i);
			label.setOpaque(true);
			label.setFont(new Font("Tahoma", Font.PLAIN, 20));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBackground(button);
			label.setForeground(Color.WHITE);
			label.addMouseListener(new LoginButtonListener(lw));
			add(label);
		}
	}
	
}
