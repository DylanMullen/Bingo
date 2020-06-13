package me.dylanmullen.bingo.window.login;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.window.login.panels.LoginPanel;
import me.dylanmullen.bingo.window.login.panels.TopMenu;

public class LoginWindow extends JFrame
{

	private static final long serialVersionUID = -4105651394392717980L;
	
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public LoginWindow()
	{
		setup();
		
		setupComponents();
		
		contentPane.requestFocusInWindow();
		
	}
	
	public void setup()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setLayout(null);
	}
	
	public void setupComponents()
	{
		TopMenu menu = new TopMenu(this, 0, 0, getWidth(), (int)((getHeight() / 10)*1.25));
		menu.setup();
		
		LoginPanel comp = new LoginPanel(0, menu.getHeight(), getWidth(), getHeight()-menu.getHeight());
		comp.setup();
		
		comp.create();
		menu.create();

		contentPane.add(comp);
		contentPane.add(menu);
	}
}
