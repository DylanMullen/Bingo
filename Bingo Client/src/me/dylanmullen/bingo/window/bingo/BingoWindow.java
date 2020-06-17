package me.dylanmullen.bingo.window.bingo;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.game.UserInformation;
import me.dylanmullen.bingo.game.home.HomePanel;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SideMenu;
import me.dylanmullen.bingo.window.login.panels.TopMenu;

public class BingoWindow extends JFrame
{

	private static final long serialVersionUID = 2893540627273369010L;

	private JPanel contentPane;

	private TopMenu topMenu;
	private SideMenu sideBar;

	private JComponent currentPanel;

	private BingoGame bingoGame;
	private JScrollPane scrollPanel;
	private HomePanel home;

	private UserInformation userInfo;

	/**
	 * Create the frame.
	 */
	public BingoWindow(UserInformation ui)
	{
		this.userInfo = ui;

		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		showEssentials();
		showHomePanel();

		setLocationRelativeTo(null);
	}

	public void showEssentials()
	{
		sideBar = new SideMenu(this, 0, 0, getWidth() / 4, getHeight());
		sideBar.setup();
		sideBar.create();
		contentPane.add(sideBar);

		topMenu = new TopMenu(this, getWidth() / 4, 0, getWidth() / 4 * 3, getHeight() / 10);
		topMenu.setup();
		topMenu.create();
		contentPane.add(topMenu);
	}

	public void showHomePanel()
	{
		hideCurrentPanel();

		if (scrollPanel == null)
		{
			scrollPanel = new JScrollPane();
			scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPanel.setPreferredSize(new Dimension(getWidth() / 4 * 3, getHeight() / 10 * 9));
			scrollPanel.setBounds(getWidth() / 4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
			scrollPanel.setBorder(null);
		}
		if (home == null)
		{
			home = new HomePanel(this, getWidth() / 4, getHeight() / 10,
					(getWidth() / 4 * 3) - ((Integer) UIManager.get("ScrollBar.width")).intValue(), getHeight() + 200);
			home.setup();
		}

		scrollPanel.setViewportView(home);
		sideBar.getHomeButton().setActive(true);
		currentPanel = scrollPanel;
		contentPane.add(scrollPanel);
	}

	public void hideHomePanel()
	{
		contentPane.remove(scrollPanel);
		sideBar.getHomeButton().setActive(false);
	}

	public void hideCurrentPanel()
	{
		if (currentPanel == null)
			return;

		if (currentPanel instanceof JScrollPane)
			hideHomePanel();
		else
			hideBingoPanel();
		contentPane.repaint();
	}

	public void showBingoPanel()
	{
		hideCurrentPanel();
		if (bingoGame == null)
		{
			bingoGame = new BingoGame();
			bingoGame.createPanel(getWidth() / 4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
			bingoGame.getGamePanel().setup();
		}
		bingoGame.getGamePanel().create();
		bingoGame.setUserInformation(userInfo);
		sideBar.getPlayButton().setActive(true);
		currentPanel = bingoGame.getGamePanel();

		contentPane.add(bingoGame.getGamePanel());
	}

	public void hideBingoPanel()
	{
		contentPane.remove(bingoGame.getGamePanel());
		sideBar.getPlayButton().setActive(false);
	}
}
