package me.dylanmullen.bingo.game.home;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.RoundedButton;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;
import me.dylanmullen.bingo.window.bingo.BingoWindow;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class CloudSelector extends JComponent
{

	private static final long serialVersionUID = 8498585054592414908L;
	private final int OFFSET = 10;
	private final int GAP = 5;

	private UUID uuid;
	private String name;
	private double price;

	private UIColourSet set;
	private Color textColor;

	private RoundedButton joinButton;
	private Font textFont;
	private Font priceFont;

	private int bannerHeight;
	private int priceBubbleHeight;
	private int buttonHeight;

	/**
	 * This is the Game Selector for each Bingo Game. <br>
	 * The player will click the join button to join that specific game. This will
	 * also contain relevant information such as the name of the Bingo Name and the
	 * price of each ticket.
	 * 
	 * @param x      X-Position of the Game Selector.
	 * @param y      Y-Position of the Game Selector.
	 * @param width  The width of the Game Selector.
	 * @param height The height of the Game Selector.
	 */
	public CloudSelector(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		setLayout(null);
		this.set = BingoApp.getInstance().getColourManager().getSet("droplets");
		setBackground(set.getColour("body-bg").toColour());
		setForeground(set.getColour("body-bg").darken(0.35).toColour());
		this.textColor = set.getColour("price-tag").getTextColour();
	}

	/**
	 * Sets the information of the Game Selector.
	 * 
	 * @param name    The name of the Bingo Game.
	 * @param players The players currently playing.
	 * @param price   The price of each ticket.
	 */
	public void setupInformation(UUID uuid, JSONObject object)
	{
		this.uuid = uuid;
		this.name = (String) object.get("cloudName");
		this.price = ((Number) object.get("ticketPrice")).doubleValue();
	}

	public void setupInformation(UUID uuid)
	{
		this.uuid = uuid;
		this.name = "Charlies Angels";
		this.price = 0.15;
	}

	/**
	 * Sets up the contents of the Game Selector.
	 */
	@SuppressWarnings("unchecked")
	private void setup()
	{
		setupBoundaries();
		this.joinButton = new RoundedButton("Join Now!",
				new ButtonInformation(new Vector2I(this.OFFSET * 2, this.OFFSET + this.GAP * 2 + this.bannerHeight),
						new Vector2I(getWidth() - (this.OFFSET * 4), this.buttonHeight), () ->
						{
							JSONObject message = new JSONObject();
							message.put("cloudUUID", uuid.toString());
							Packet packet = PacketHandler.createPacket(18, message);
							PacketHandler.sendPacket(packet, new PacketCallback()
							{
								@Override
								public boolean callback()
								{
									BingoWindow.getWindow().showBingoCloud(uuid, getMessage());
									return false;
								}
							});
						}));

		getJoinButton().updateColours(set.getColour("join-bg"), set.getColour("join-bg").darken(0.05));
	}

	private void setupBoundaries()
	{
		this.bannerHeight = (int) (((getHeight() - (this.OFFSET * 2)) / 3) * 2);
		this.priceBubbleHeight = (int) (((getWidth() - (this.OFFSET * 2)) / 4.5) - this.GAP * 2);
		this.buttonHeight = getHeight() - bannerHeight - (this.OFFSET * 3);

		this.textFont = new Font("Calibri", Font.PLAIN, 25);
		this.priceFont = FontUtil.getFont((int) (price * 100) + "", this,
				new Vector2I(this.priceBubbleHeight - 15, this.priceBubbleHeight - 15));

		if (joinButton != null)
			updateButtonBounds();
	}

	private void updateButtonBounds()
	{
		joinButton.getInformation().updateBounds(
				new Vector2I(this.OFFSET * 2, this.OFFSET + this.GAP * 2 + this.bannerHeight),
				new Vector2I(getWidth() - (this.OFFSET * 4), this.buttonHeight));
		joinButton.updateBounds();
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		setupBoundaries();
		repaint();
	}

	/**
	 * Creates the Game Selector.
	 */
	public void create()
	{
		setup();
		add(getJoinButton());
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawBody(g2);
		drawImageBanner(g2);
		drawNameBanner(g2);
		drawPriceBubble(g2);
		super.paintComponent(g);

	}

	private void drawBody(Graphics2D g2)
	{
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		g2.setColor(getForeground());
		g2.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
		g2.setStroke(new BasicStroke());
	}

	/**
	 * Draws a rounded image banner for the Game Selector.
	 * 
	 * @param g2 Graphics2D object passed from {@link #paintComponent(Graphics)}
	 */
	private void drawImageBanner(Graphics2D g2)
	{
		g2.setClip(new RoundRectangle2D.Float(this.OFFSET, this.OFFSET, getWidth() - this.OFFSET * 2, this.bannerHeight,
				15, 15));
		try
		{
			g2.drawImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("placeholder.png")), this.OFFSET,
					this.OFFSET, getWidth() - this.OFFSET * 2, this.bannerHeight, null);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		g2.setClip(null);
	}

	/**
	 * Draws the Price Bubble for the Game Selector.
	 * 
	 * @param g2 Graphics2D object passed from {@link #paintComponent(Graphics)}
	 */
	private void drawPriceBubble(Graphics2D g2)
	{
		g2.setColor(set.getColour("price-tag").toColour());
		g2.fillOval(getWidth() - (this.OFFSET + this.GAP) - this.priceBubbleHeight, this.OFFSET + this.GAP,
				this.priceBubbleHeight, this.priceBubbleHeight);

		g2.setColor(textColor);
		g2.setFont(priceFont);
		String string = (int) (price * 100) + "";
		Dimension dim = FontUtil.getFontSize(getFontMetrics(priceFont), string, 0, 0);

		int fontSize = getWidth() - (this.OFFSET + this.GAP) - (this.priceBubbleHeight / 2);
		g2.drawString(string, fontSize - (dim.width / 2),
				this.OFFSET + this.GAP + (this.priceBubbleHeight / 2) + (dim.height / 4));
	}

	/**
	 * Draws the Name Banner for the Game Selector.
	 * 
	 * @param g2 Graphics2D object passed from {@link #paintComponent(Graphics)}
	 */
	private void drawNameBanner(Graphics2D g2)
	{
		g2.setColor(set.getColour("banner-bg").applyTransparency(200));
		g2.fillRoundRect(this.OFFSET, this.OFFSET + this.bannerHeight - (int) ((this.bannerHeight / 4) * 1.15),
				getWidth() - this.OFFSET * 2, (int) ((this.bannerHeight / 4) * 1.15), 15, 15);

		Dimension dim = FontUtil.getFontSize(getFontMetrics(textFont), name, 0, 0);

		g2.setColor(Color.WHITE);
		g2.setFont(textFont);
		g2.drawString(name, (getWidth()) / 2 - (dim.width / 2),
				this.OFFSET + this.bannerHeight - ((int) ((this.bannerHeight / 4) * 1.15) / 2) + (dim.height / 4));
	}

	/**
	 * Returns the join button for the Game Selector
	 * 
	 * @return {@link #joinButton}
	 */
	public RoundedButton getJoinButton()
	{
		return this.joinButton;
	}

}
