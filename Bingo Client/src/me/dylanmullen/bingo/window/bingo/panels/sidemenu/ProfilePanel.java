package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.game.BingoGame;
import me.dylanmullen.bingo.window.ui.ImageComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

public class ProfilePanel extends Panel
{

	private static final long serialVersionUID = 3952772083758555561L;

	private Grid grid;
	private List<InfoComponent> infoComp = new ArrayList<>();

	public ProfilePanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBorder(new EmptyBorder(12, 12, 12, 12));
		setOpaque(false);
		grid = new Grid(new GridSettings(width - 10, height - 110, 4, 1, 5), 5, 105);

		ImageComponent ic = new ImageComponent(10, 10, width - 20, 90);
		try
		{
			ic.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setupFields();
		create();
		add(ic);
	}

	private void setupFields()
	{
		InfoComponent username = new InfoComponent("Username", "Loading...", 0, 0, 0, 0);
		InfoComponent money = new InfoComponent("Money", "Loading...", 0, 0, 0, 0);
		InfoComponent wins = new InfoComponent("Wins", "Loading...", 0, 0, 0, 0);
		InfoComponent ratio = new InfoComponent("Win/Lose %", "Loading...", 0, 0, 0, 0);
		grid.addGridItem(new GridItem(username, 1, 1), 0);
		grid.addGridItem(new GridItem(money, 1, 1), 1);
		grid.addGridItem(new GridItem(wins, 1, 1), 2);
		grid.addGridItem(new GridItem(ratio, 1, 1), 3);
		username.setup();
		ratio.setup();
		wins.setup();
		money.setup();
		infoComp.add(username);
		infoComp.add(money);
		infoComp.add(wins);
		infoComp.add(ratio);
	}

	@Override
	public void create()
	{
		updateItems();
		for (InfoComponent item : infoComp)
		{
			add(item);
		}
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(UIColour.BTN_FAILURE.toColor());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		super.paintComponent(g);

	}

	public void updateItems()
	{
		if (BingoGame.getInstance() != null)
		{
			infoComp.get(0).getInfo().setText(BingoGame.getInstance().getUserInformation().getDisplayName());
			infoComp.get(1).getInfo().setText(BingoGame.getInstance().getUserInformation().getCredits() + "");
			infoComp.get(2).getInfo().setText(BingoGame.getInstance().getUserInformation().getWins() + "");
			infoComp.get(3).getInfo().setText("N/A");
		}
	}

	public void updateCredits(double credits)
	{
		infoComp.get(1).getInfo().setText(credits + "");
	}
}
