package me.dylanmullen.bingo.game.components.overlays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.callbacks.PurchaseCallback;
import me.dylanmullen.bingo.game.cards.BingoCard;
import me.dylanmullen.bingo.gfx.ui.buttons.Button;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

public class PurchaseOverlay extends Overlay
{

	private static final long serialVersionUID = 1L;

	private BingoCard card;
	private String message;
	private UIColourSet set;

	public PurchaseOverlay(BingoCard card, Color bgColour, int x, int y, int width, int height)
	{
		super(bgColour, x, y, width, height);
		this.card = card;
		this.set = BingoApp.getInstance().getColourManager().getSet("overlays");
	}

	public void setup()
	{
		this.message = "Are you sure?";
		setFont(FontUtil.getFont(message, this,
				new Vector2I((int) (getWidth() / 1.5), (getHeight() - getHeight() / 4 + 5) - 10)));

		UIColourSet set = BingoApp.getInstance().getColourManager().getSet("buttons");
		Button purchased = new Button("Purchase", new ButtonInformation(null, null, () ->
		{
			PacketHandler.sendPacket(constructPacket(getCard().getUUID()), new PurchaseCallback());
		}));
		Button cancel = new Button("Cancel", new ButtonInformation(null, null, () ->
		{
			card.hidePurchaseOverlay();
		}));

		purchased.updateColours(set.getColour("purchase-bg"), set.getColour("purchase-bg").darken(0.15));
		cancel.updateColours(set.getColour("cancel-bg"), set.getColour("cancel-bg").darken(0.15));

		setCustomShapes(purchased, cancel);
		add(cancel);
		add(purchased);
	}

	private void setCustomShapes(Button purchase, Button cancel)
	{
		int width = (getWidth() / 2) - 5;
		int height = getHeight() / 4 + 5;
		purchase.setBounds(5, getHeight() - height - 5, width, height);

		Polygon shape = new Polygon();
		shape.addPoint(0, 0);
		shape.addPoint(width, 0);
		shape.addPoint(width - 10, height);
		shape.addPoint(0, height);
		purchase.setCustomShape(shape);

		shape = new Polygon();
		shape.addPoint(10, 0);
		shape.addPoint(width + 10, 0);
		shape.addPoint(width + 10, height);
		shape.addPoint(0, height);
		cancel.setBounds(getWidth() / 2 - 10, getHeight() - height - 5, width + 10, height);
		cancel.setCustomShape(shape);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(getFont());
		Dimension dim = FontUtil.getFontSize(getFontMetrics(getFont()), message, 0, 0);
		drawMessageBubble(g2, dim);
		g2.setColor(getForeground());
		g2.drawString(message, getWidth() / 2 - dim.width / 2, getHeight() / 2 + (dim.height / 4) - 20);
	}

	private void drawMessageBubble(Graphics2D g2, Dimension textDim)
	{
		g2.setColor(set.getColour("purchase-bg").toColour());
		int height = textDim.height + 10;
		int width = textDim.width + 10;
		int xPos = getHeight() / 2 - height / 2 - 20;
		g2.fillRoundRect(getWidth() / 2 - width / 2 - 10 / 4, xPos, width, height, 15, 15);
		g2.setStroke(new BasicStroke(2));
		g2.setColor(set.getColour("purchase-bg").darken(0.5).toColour());
		g2.drawRoundRect(getWidth() / 2 - width / 2 - 10 / 4, xPos, width, height, 15, 15);
	}

	private Packet constructPacket(UUID uuid)
	{
		JSONObject message = new JSONObject();
		message.put("dropletUUID", getCard().getDropletUUID().toString());
		message.put("cardUUID", uuid.toString());
		return PacketHandler.createPacket(8, message);
	}

	public BingoCard getCard()
	{
		return card;
	}
}
