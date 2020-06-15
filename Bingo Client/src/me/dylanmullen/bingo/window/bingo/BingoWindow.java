package me.dylanmullen.bingo.window.bingo;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.game.UserInformation;
import me.dylanmullen.bingo.game.home.HomePanel;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SP_Bingo;
import me.dylanmullen.bingo.window.login.panels.TopMenu;

public class BingoWindow extends JFrame
{

	private static final long serialVersionUID = 2893540627273369010L;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public BingoWindow(UserInformation ui)
	{
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		TopMenu topMenu_1 = new TopMenu(this, getWidth() / 4, 0, getWidth() / 4 * 3, getHeight() / 10);
		topMenu_1.setup();
		topMenu_1.create();
		contentPane.add(topMenu_1);
		
		if(ui != null)
		{
			BingoGame game = new BingoGame();
			game.createPanel(getWidth() / 4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
			game.getGamePanel().setup();
			game.getGamePanel().create();
			game.setUserInformation(ui);
//		contentPane.add(game.getGamePanel());
		}
			

		HomePanel home = new HomePanel(getWidth() / 4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
		home.setup();
		
		contentPane.add(home);
		
		SP_Bingo sidePanel_1 = new SP_Bingo(0, 0, getWidth() / 4, getHeight());
		sidePanel_1.setup();
		sidePanel_1.create();
		contentPane.add(sidePanel_1);
		setLocationRelativeTo(null);
	}
}
