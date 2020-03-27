package me.dylanmullen.bingo.window.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.window.login.panels.LoginPanel;
import me.dylanmullen.bingo.window.login.panels.TopMenu;

public class LoginWindow extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					LoginWindow frame = new LoginWindow();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		setLayout(null);
		TopMenu menu = new TopMenu(this, 0, 0, getWidth(), getHeight() / 10);
		menu.setup();
		menu.create();
		contentPane.add(menu);

		LoginPanel comp = new LoginPanel(0, menu.getHeight(), getWidth(), getHeight()-menu.getHeight());
		comp.create();
		contentPane.add(comp);
		setVisible(true);
		
		contentPane.requestFocusInWindow();
	}

}
