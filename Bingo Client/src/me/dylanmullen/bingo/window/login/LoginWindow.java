package me.dylanmullen.bingo.window.login;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.window.login.comps.LoginContent;
import me.dylanmullen.bingo.window.login.comps.LoginSideMenu;
import me.dylanmullen.bingo.window.login.comps.LoginTopMenu;

public class LoginWindow extends JFrame
{

	private JPanel contentPane;

	private Color background = new Color(0x6a89cc);
	private Color background_dark = new Color(0x4a69bd);
	private Color background_darkened = new Color(0x1e3799);

	private LoginTopMenu ltm;
	private LoginSideMenu lsm;
	private LoginContent lc;
	
	private Client client;
	
	/**
	 * Create the frame.
	 */
	public LoginWindow()
	{
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		generatePanels();
		createClient();
		setVisible(true);
	}

	private void generatePanels()
	{
		contentPane.setLayout(null);

		ltm= new LoginTopMenu();
		ltm.setBounds(0, 0, 750, 50);
		contentPane.add(ltm);
		ltm.setBackground(background_dark);

		lc = new LoginContent(this);
		lc.setBounds(220, 50, 530, 330);
		contentPane.add(lc);
		
		lsm = new LoginSideMenu(this);
		lsm.setBounds(0, 50, 220, 330);
		contentPane.add(lsm);
		lsm.setBackground(background_darkened);

	}
	
	public void createClient()
	{
		this.client = new Client("localhost", 4585);
	}
	
	public Client getClient()
	{
		return client;
	}
	
	public LoginContent getLoginContent()
	{
		return lc;
	}
	
}
