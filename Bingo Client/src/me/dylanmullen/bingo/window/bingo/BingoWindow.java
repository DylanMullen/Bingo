package me.dylanmullen.bingo.window.bingo;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.game.GamePanel;
import me.dylanmullen.bingo.window.bingo.panels.TM_Bingo;
import me.dylanmullen.bingo.window.bingo.panels.content.PlayContent;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SP_Bingo;

public class BingoWindow extends JFrame
{

	private static final long serialVersionUID = 2893540627273369010L;

	private JPanel contentPane;

	private boolean debug = true;

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
					long now = System.currentTimeMillis();
					BingoWindow frame = new BingoWindow();
					frame.setVisible(true);
					System.out.println(System.currentTimeMillis() - now);
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
	public BingoWindow()
	{
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		create();

		SP_Bingo sidePanel_1 = new SP_Bingo(0, 0, getWidth() / 4, getHeight());
		sidePanel_1.setup();
		sidePanel_1.create();
		contentPane.add(sidePanel_1);

		TM_Bingo topMenu_1 = new TM_Bingo(this, getWidth() / 4, 0, getWidth() / 4 * 3, getHeight() / 10);
		topMenu_1.setup();
		topMenu_1.create();
		contentPane.add(topMenu_1);

		BingoGame game = new BingoGame();
		game.createPanel(getWidth() / 4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
		game.getGamePanel().setup();
		game.getGamePanel().create();

		// GamePanel gp = new GamePanel(getWidth()/4, getHeight() / 10, getWidth() / 4 *
		// 3, getHeight() / 10 * 9);
//		gp.setup();
//		gp.create();
		contentPane.add(game.getGamePanel());

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void create()
	{
	}
}
