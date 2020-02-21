package me.dylanmullen.bingo.window.login;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.window.login.comps.LoginContent;
import me.dylanmullen.bingo.window.login.comps.LoginSideMenu;
import me.dylanmullen.bingo.window.login.comps.LoginTopMenu;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
	private Point initialClick;

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

		JFrame frame = this;
		contentPane.addMouseMotionListener(new MouseMotionListener()
		{

			public void mouseDragged(MouseEvent e)
			{
				int thisX = frame.getLocation().x;
				int thisY = frame.getLocation().y;

				// Determine how much the mouse moved since the initial click
				int xMoved = e.getX() - initialClick.x;
				int yMoved = e.getY() - initialClick.y;

				// Move window to this position
				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				frame.setLocation(X, Y);
			}

			@Override
			public void mouseMoved(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}
		});
		contentPane.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});

		setVisible(true);
	}

	private void generatePanels()
	{
		contentPane.setLayout(null);

		ltm = new LoginTopMenu();
		ltm.setVisible(false);
		ltm.setBounds(0, 0, 750, 50);
		contentPane.add(ltm);
		
		lc = new LoginContent(this);
		lc.setVisible(false);
		lc.setBounds(220, 50, 530, 330);
		contentPane.add(lc);

		lsm = new LoginSideMenu(this);
		lsm.setVisible(false);
		lsm.setBounds(0, 50, 220, 330);
		contentPane.add(lsm);

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
