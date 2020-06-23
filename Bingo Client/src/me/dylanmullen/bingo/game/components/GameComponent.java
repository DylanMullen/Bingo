package me.dylanmullen.bingo.game.components;

import java.awt.Graphics;

import me.dylanmullen.bingo.game.cards.BingoCardContainer;
import me.dylanmullen.bingo.game.components.overlays.WinnerOverlay;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class GameComponent extends Panel
{

	private static final long serialVersionUID = 6557409370693226153L;

	private BingoCardContainer comp;
	private WinnerOverlay winner;

	public GameComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(UIColour.FRAME_BINGO_BG.toColor());
	}

	@Override
	public void create()
	{

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	public void restart()
	{
		comp.repaint();
		repaint();
	}

	public void createCardGroup()
	{
		comp = new BingoCardContainer(12, 12, getWidth() - 24, getHeight() - 24);
		comp.setup();
		add(comp);
		comp.setVisible(false);
		comp.setVisible(true);
		comp.requestBingoCards();
	}
	
	public void createWinnerOverlay()
	{
		winner = new WinnerOverlay(0, 0, width, height);
		winner.setVisible(false);
		add(winner);
		setComponentZOrder(winner, 1);
	}
	
	public void hideWinnerOverlay()
	{
		winner.setVisible(false);
		winner.setWinners(null);
	}

	public WinnerOverlay getWinner()
	{
		if (winner == null)
			createWinnerOverlay();
		return winner;
	}

	public BingoCardContainer getCardGroup()
	{
		return comp;
	}
}
