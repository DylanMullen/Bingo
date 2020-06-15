package me.dylanmullen.bingo.game.home;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JScrollPane;

import me.dylanmullen.bingo.window.bingo.BingoWindow;
import me.dylanmullen.bingo.window.ui.ImageComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

public class HomePanel extends Panel
{

	private static final long serialVersionUID = 1L;

	private BingoWindow window;

	private static HomePanel instance;

	public HomePanel(BingoWindow window, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		if (instance == null)
			instance = this;
		this.window = window;
	}

	public static HomePanel getInstance()
	{
		return instance;
	}

	@Override
	public void setup()
	{
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				repaint();
			}
		});
		
		setBackground(UIColour.FRAME_BINGO_BG.toColor());
		int width = (getWidth() - (10 * 3)) / 2;

		ImageComponent ic = new ImageComponent(15, 15, getWidth() - 30, (int) (height / 8) * 2);
		try
		{
			ic.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		add(ic);
		Grid grid = new Grid(new GridSettings(getWidth() - 100, (int) (height / 8) * 6 - 50, 2, 3, 15), 50,
				height + 25 - (int) (height / 8) * 6);

		for (int i = 0; i < 6; i++)
		{
			GameSelector selector = new GameSelector(10, 10, width, 100);
			grid.addGridItem(new GridItem(selector, 1, 1), i % 2);
			selector.create();
			add(selector);
		}
	}

	@Override
	public void create()
	{

	}

	public BingoWindow getWindow()
	{
		return window;
	}

}
