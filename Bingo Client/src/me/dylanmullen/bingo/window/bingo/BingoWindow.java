package me.dylanmullen.bingo.window.bingo;

import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SP_Bingo;
import me.dylanmullen.bingo.window.login.panels.TopMenu;

public class BingoWindow extends JFrame
{

	private static final long serialVersionUID = 2893540627273369010L;

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public BingoWindow(UUID uuid)
	{
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		SP_Bingo sidePanel_1 = new SP_Bingo(0, 0, getWidth() / 4, getHeight());
		sidePanel_1.setup();
		sidePanel_1.create();
		contentPane.add(sidePanel_1);

		TopMenu topMenu_1 = new TopMenu(this, getWidth() / 4, 0, getWidth() / 4 * 3, getHeight() / 10);
		topMenu_1.setup();
		topMenu_1.create();
		contentPane.add(topMenu_1);

		BingoGame game = new BingoGame();
		game.createPanel(getWidth() / 4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
		game.setPlayerUUID(uuid);
		game.getGamePanel().setup();
		game.getGamePanel().create();
		contentPane.add(game.getGamePanel());

		setLocationRelativeTo(null);
	}
}
