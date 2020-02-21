package me.dylanmullen.bingo.window.bingo;

import java.awt.EventQueue;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.window.bingo.panels.TM_Bingo;
import me.dylanmullen.bingo.window.bingo.panels.content.MainContent;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.SP_Bingo;
import me.dylanmullen.bingo.window.ui.UIColour;

public class BingoWindow extends JFrame
{

	private static final long serialVersionUID = 2893540627273369010L;

	private JPanel contentPane;

	private boolean debug = true;

	private ArrayList<JPanel> panels;
	private JPanel topMenu;
	private JPanel sidePanel;
	private JPanel content;

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
					BingoWindow frame = new BingoWindow();
					frame.setVisible(true);
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

		MainContent content = new MainContent(getWidth()/4, getHeight() / 10, getWidth() / 4 * 3, getHeight() / 10 * 9);
		content.setup();
		content.create();
		contentPane.add(content);

		setLocationRelativeTo(null);
	}

	public void create()
	{
	}
}
