package me.dylanmullen.bingo.game.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import me.dylanmullen.bingo.game.components.listeners.JoinButtonListener;
import me.dylanmullen.bingo.window.ui.RoundedButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class GameSelector extends JComponent
{

	private static final long serialVersionUID = 8498585054592414908L;

	private String name;
	private int players;
	private double price;

	private RoundedButton joinButton;

	public GameSelector(int x, int y, int w, int h)
	{
		setBounds(x, y, w, h);
		setLayout(null);
	}

	public void setupInformation(String name, int players, double price)
	{
		this.name = name;
		this.players = players;
		this.price = price;
	}

	public void create()
	{
		joinButton = new RoundedButton("Click to Join!", new Font("Calbiri", Font.PLAIN, 20), UIColour.BINGO_BALL_4);
		joinButton.setBounds(getHeight(), getHeight() - 15 - ((getHeight() + 15) / 4), getWidth() - getHeight() - 15,
				(getHeight() + 15) / 4);
		joinButton.create();
		joinButton.addMouseListener(new JoinButtonListener());
		add(joinButton);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(UIColour.BTN_BINGO_ACTIVE.toColor());

		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		g2.setColor(Color.black);
		g2.fillRoundRect(15, 15, getHeight() - 30, getHeight() - 30, 15, 15);

		g2.setColor(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		g2.fillRoundRect(getHeight(), 15, getWidth() - (getHeight() + 15), (getHeight() - 30), 15, 15);

		g2.setFont(new Font("Calibri", Font.PLAIN, 25));
		g2.setColor(Color.white);
		g2.drawString("Players: ", getHeight() + 15, 30 + (25 / 2));
		g2.drawString("Ticket Price:", getHeight() + 15, 30 + (25 / 2) * 2 + 15);
		g2.drawString("House Prize:", getHeight() + 15, 30 + (25 / 2) * 3 + 30);
		super.paintComponent(g);
	}

}
