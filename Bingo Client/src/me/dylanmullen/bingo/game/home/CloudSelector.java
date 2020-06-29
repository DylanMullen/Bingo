package me.dylanmullen.bingo.game.home;

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
	private int players;
	private int instances;
	private double price;

	private UIColourSet set;

	private RoundedButton joinButton;
	Font font = new Font("Calibri", Font.PLAIN, 25);

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
		this.set = BingoApp.getInstance().getColours().getSet("droplets");
		setBackground(set.getColour("body-bg").toColour());
		setForeground(set.getColour("text-colour").toColour());
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
		this.instances = ((Number) object.get("dropletInstances")).intValue();
		this.players = ((Number) object.get("totalPlayers")).intValue();
		this.price = ((Number) object.get("ticketPrice")).doubleValue();
	}

	/**
	 * Sets up the contents of the Game Selector.
	 */
	private void setup()
	{
		this.bannerHeight = (int) (((getHeight() - (this.OFFSET * 2)) / 3) * 1.5);
		this.priceBubbleHeight = (int) (((getWidth() - (this.OFFSET * 2)) / 4) - this.GAP * 2);
		this.buttonHeight = getHeight() / 6;

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

		getJoinButton().updateColours(set.getColour("join-bg"), set.getColour("join-active"));
	}

	/**
	 * Creates the Game Selector.
	 */
	public void create()
	{
		setup();
		add(getJoinButton());
	}

	/**
	 * Updates the bounds of the Game Selector and the contents within the Game
	 * Selector.
	 */
	public void updateBounds()
	{
		setup();
		getJoinButton().setBounds(this.OFFSET * 2, this.OFFSET + this.GAP * 2 + this.bannerHeight,
				getWidth() - (this.OFFSET * 4), this.buttonHeight);
		getJoinButton().updateBounds();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		drawImageBanner(g2);
		drawNameBanner(g2);
		drawPriceBubble(g2);
		super.paintComponent(g);
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

		g2.setColor(getForeground());
		Font font = new Font("Calibri", Font.PLAIN, 20);
		g2.setFont(font);
		String string = (int) price + "";
		Dimension dim = FontUtil.getFontSize(getFontMetrics(font), string, 0, 0);

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
		g2.setColor(set.getColour("banner-bg").applyTransparency(220));
		g2.fillRoundRect(this.OFFSET, this.OFFSET + this.bannerHeight - (int) ((this.bannerHeight / 4) * 1.15),
				getWidth() - this.OFFSET * 2, (int) ((this.bannerHeight / 4) * 1.15), 15, 15);

		Dimension dim = FontUtil.getFontSize(getFontMetrics(font), name, 0, 0);

		g2.setColor(Color.WHITE);
		g2.setFont(font);
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
