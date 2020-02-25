package me.dylanmullen.bingo.window.bingo.panels.content;

import java.awt.Graphics;

import javax.swing.JPanel;

import me.dylanmullen.bingo.game.components.GameComponent;
import me.dylanmullen.bingo.game.components.NumbersCalledComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class PlayContent extends Panel
{

	private JPanel calledNumbers;
	private JPanel chat;
	private JPanel game;

	public PlayContent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		System.out.println(height);
		setBackground(UIColour.FRAME_BINGO_BG.toColor());
		setLayout(null);
		NumbersCalledComponent comp = new NumbersCalledComponent(0, 0, width, 110);
		comp.setup();
		comp.create();
		GameComponent gc = new GameComponent(0, comp.getHeight(), (int)(width / 3)*2, height-comp.getHeight());
		gc.setup();
		add(comp);
		add(gc);
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

}
