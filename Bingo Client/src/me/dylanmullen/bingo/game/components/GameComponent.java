package me.dylanmullen.bingo.game.components;

import java.awt.Graphics;
import java.util.List;
import java.util.UUID;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.cards.BingoCardContainer;
import me.dylanmullen.bingo.game.cards.CardInformation;
import me.dylanmullen.bingo.game.components.overlays.WinnerOverlay;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class GameComponent extends UIPanel
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
		setBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("content").toColour());
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

	public void createCardGroup(UUID dropletUUID, List<CardInformation> cards)
	{
		comp = new BingoCardContainer(dropletUUID, 15, 15, getWidth() - 30, getHeight() - 30);
		comp.setup();
		comp.createCards(cards);
		add(comp);
		comp.setVisible(false);
		comp.setVisible(true);
		repaint();
	}

	public void createWinnerOverlay()
	{
		winner = new WinnerOverlay(15, 15, width - 30, height - 30);
		winner.setVisible(true);
		add(winner);
		setComponentZOrder(winner, 0);
	}

	public void showWinners(List<String> list)
	{
		if (winner == null)
			createWinnerOverlay();
		winner.setWinners(list);
		winner.setVisible(true);
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
