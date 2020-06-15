package me.dylanmullen.bingo.game.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JInternalFrame;

import me.dylanmullen.bingo.game.components.listeners.JoinButtonListener;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.RoundedButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class GameSelector extends JComponent
{

	private static final long serialVersionUID = 8498585054592414908L;

	private String name;
	private int players;
	private double price;

	private final int OFFSET = 10;
	private final int GAP = 5;
	private int bannerHeight;
	private int priceBubbleHeight;
	private int buttonHeight;

	private RoundedButton joinButton;

	public GameSelector(int x, int y, int w, int h)
	{
		setBounds(x, y, w, h);
		setLayout(null);
		setupInformation("", 15, 15);
	}

	public void setupInformation(String name, int players, double price)
	{
		this.name = name;
		this.players = players;
		this.price = price;
	}

	private void setup()
	{
		this.bannerHeight = (int) (((getHeight() - (OFFSET * 2)) / 3) * 2);
		this.priceBubbleHeight = (int) (((getHeight() - (OFFSET * 2)) / 3) - GAP * 2);
		this.buttonHeight = (getHeight() - ((OFFSET * 2) + GAP)) - bannerHeight;
	}

	public void create()
	{
		setup();
		joinButton = new RoundedButton("Click to Join!", new Font("Calbiri", Font.PLAIN, 20), UIColour.BINGO_BALL_4);
		joinButton.setBounds(OFFSET * 2, getHeight() - (OFFSET + buttonHeight), getWidth() - (OFFSET * 4),
				buttonHeight);
		joinButton.create();
		joinButton.addMouseListener(new JoinButtonListener(joinButton));
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
		g2.fillRoundRect(OFFSET, OFFSET, getWidth() - OFFSET * 2, bannerHeight, 15, 15);
		drawPriceButton(g2);
		super.paintComponent(g);
	}

	private void drawPriceButton(Graphics2D g2)
	{
		g2.setColor(UIColour.UI_PRICE_BUBBLE.toColor());
		g2.fillOval(getWidth() - (OFFSET + GAP) - priceBubbleHeight, OFFSET + GAP, priceBubbleHeight,
				priceBubbleHeight);

		g2.setColor(UIColour.UI_PRICE_BUBBLE.getTextColour());
		Font font = new Font("Calibri", Font.PLAIN, 20);
		g2.setFont(font);
		price+=5;
		String string = (int)price + "";
		System.out.println(string.length());
		Dimension dim = FontUtil.getFontSize(getFontMetrics(font), font, string, 0, 0);

		int fontSize = getWidth() - (OFFSET + GAP) - (priceBubbleHeight/2);
		g2.drawString(string, fontSize - (dim.width/2), OFFSET + GAP +(priceBubbleHeight/2)+(dim.height/4));
	}

}
