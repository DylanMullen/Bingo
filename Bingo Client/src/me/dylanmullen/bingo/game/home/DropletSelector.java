package me.dylanmullen.bingo.game.home;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.UUID;

import javax.swing.JComponent;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.droplet.DropletJoinEvent;
import me.dylanmullen.bingo.gfx.ui.buttons.Button;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

public class DropletSelector extends JComponent
{

	private static final long serialVersionUID = -3204722496312091463L;

	private UUID cloudUUID, dropletUUID;
	private int players;
	private int max;
	private int pos;

	private UIColourSet set;

	private Button joinButton;

	public DropletSelector(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		setFont(new Font("Calibri", Font.PLAIN, 25));
	}

	public void setupInformation(UUID cloud, UUID dropletUUID, int pos, int players, int max)
	{
		this.cloudUUID = cloud;
		this.dropletUUID = dropletUUID;
		this.pos = pos;
		this.players = players;
		this.max = max;
		this.set = BingoApp.getInstance().getColourManager().getSet("droplets");
	}

	private Polygon body, buttonShape;

	private void setupBody()
	{
		if (joinButton != null)
		{
			body = new Polygon();
			body.addPoint(0, 0);
			body.addPoint(joinButton.getX() + 10, 0);
			body.addPoint(joinButton.getX(), getHeight());
			body.addPoint(0, getHeight());

			buttonShape = new Polygon();
			buttonShape.addPoint(10, 0);
			buttonShape.addPoint(getWidth(), 0);
			buttonShape.addPoint(getWidth(), getHeight());
			buttonShape.addPoint(0, getHeight());

			joinButton.getInformation().updateBounds(new Vector2I(getWidth() - 100, 0), new Vector2I(100, getHeight()));
			joinButton.setCustomShape(buttonShape);
		}
	}

	@SuppressWarnings("unchecked")
	public void setup()
	{
		int width = 100;
		joinButton = new Button("Join", null,
				new ButtonInformation(new Vector2I(getWidth() - width, 0), new Vector2I(width, getHeight()), () ->
				{
					JSONObject message = new JSONObject();
					message.put("cloudUUID", cloudUUID.toString());
					message.put("dropletUUID", dropletUUID.toString());
					Packet packet = PacketHandler.createPacket(6, message);
					PacketHandler.sendPacket(packet, new PacketCallback()
					{
						@Override
						public boolean callback()
						{
							EventHandler.getHandler().fire(new DropletJoinEvent(getDropletUUID(), getMessage()));
							return false;
						}
					});
				}));
		joinButton.updateColours(set.getColour("join-bg"), set.getColour("join-bg").darken(0.05));
		add(joinButton);
		setupBody();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		drawBody(g2);
		drawInstanceString(g2);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paintChildren(g);
		paintComponent(g);
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		setupBody();
	}

	private void drawBody(Graphics2D g2)
	{
		g2.setColor(set.getColour("body-bg").darken(0.05).toColour());
		g2.fillPolygon(body);
//		g2.setColor(set.getColour("body-bg").darken(0.20).toColour());
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(2));
		g2.drawLine(body.xpoints[1], 0, body.xpoints[2], getHeight());
	}

	private void drawPills(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
	}

	private void drawInstanceString(Graphics2D g2)
	{
		String instance = "Instance " + pos + " (" + players + "/" + max + ")";
		g2.setFont(getFont());
		g2.setColor(Color.white);
		Dimension dim = FontUtil.getFontSize(getFontMetrics(getFont()), instance, 0, 0);
		int indent = 15;
		g2.drawString(instance, indent, getHeight() / 2 + (dim.height / 4));
	}
}
