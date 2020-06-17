package me.dylanmullen.bingo.game.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import me.dylanmullen.bingo.game.components.listeners.JoinButtonListener;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.RoundedButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class GameSelector extends JComponent
{

	private static final long serialVersionUID = 8498585054592414908L;

	private String name;
//	private int players;
	private double price;
	
	Font font = new Font("Calibri", Font.PLAIN, 25);

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
		setupInformation("Charlies Angels", 15, 15);
	}

	public void setupInformation(String name, int players, double price)
	{
		this.name = name;
//		this.players = players;
		this.price = price;
	}

	private void setup()
	{
		this.bannerHeight = (int) (((getHeight() - (OFFSET * 2)) / 3) * 1.5);
		this.priceBubbleHeight = (int) (((getWidth() - (OFFSET * 2)) / 4) - GAP * 2);
		this.buttonHeight = getHeight() / 6;
	}

	public void create()
	{
		setup();
		joinButton = new RoundedButton("Click to Join!", new Font("Calbiri", Font.PLAIN, 20), UIColour.BINGO_BALL_4);
		joinButton.setBounds(OFFSET * 2, OFFSET + GAP * 2 + bannerHeight, getWidth() - (OFFSET * 4), buttonHeight);
		joinButton.create();
		joinButton.addMouseListener(new JoinButtonListener(joinButton));
		add(joinButton);
	}
	
	public void updateBounds()
	{
		setup();
		joinButton.setBounds(OFFSET * 2, OFFSET + GAP * 2 + bannerHeight, getWidth() - (OFFSET * 4), buttonHeight);
		joinButton.updateBounds();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(UIColour.BTN_BINGO_ACTIVE.toColor());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		drawImageBanner(g2);
		drawNameBanner(g2);
		drawPriceButton(g2);
		super.paintComponent(g);
	}

	private void drawImageBanner(Graphics2D g2)
	{
		g2.setClip(new RoundRectangle2D.Float(OFFSET, OFFSET, getWidth() - OFFSET * 2, bannerHeight, 15, 15));
		try
		{
			g2.drawImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")), OFFSET,
					OFFSET, getWidth() - OFFSET * 2, bannerHeight, null);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		g2.setClip(null);
	}

	private void drawPriceButton(Graphics2D g2)
	{
		g2.setColor(UIColour.UI_PRICE_BUBBLE.toColor());
		g2.fillOval(getWidth() - (OFFSET + GAP) - priceBubbleHeight, OFFSET + GAP, priceBubbleHeight,
				priceBubbleHeight);

		g2.setColor(UIColour.UI_PRICE_BUBBLE.getTextColour());
		Font font = new Font("Calibri", Font.PLAIN, 20);
		g2.setFont(font);
		String string = (int) price + "";
		Dimension dim = FontUtil.getFontSize(getFontMetrics(font), font, string, 0, 0);

		int fontSize = getWidth() - (OFFSET + GAP) - (priceBubbleHeight / 2);
		g2.drawString(string, fontSize - (dim.width / 2), OFFSET + GAP + (priceBubbleHeight / 2) + (dim.height / 4));
	}

	private void drawNameBanner(Graphics2D g2)
	{
		Color color = UIColour.FRAME_BINGO_BG_TOP.toColor();
		g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 220));
		g2.fillRoundRect(OFFSET, OFFSET + bannerHeight - (int) ((bannerHeight / 4) * 1.15), getWidth() - OFFSET * 2,
				(int) ((bannerHeight / 4) * 1.15), 15, 15);

		Dimension dim = FontUtil.getFontSize(getFontMetrics(font), font, name, 0, 0);

		g2.setColor(Color.WHITE);
		g2.setFont(font);
		g2.drawString(name, (getWidth()) / 2 - (dim.width / 2),
				OFFSET + bannerHeight - ((int) ((bannerHeight / 4) * 1.15) / 2) + (dim.height / 4));
	}

}
