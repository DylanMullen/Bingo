package me.dylanmullen.bingo.game.home;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.UUID;

import javax.swing.JComponent;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.events.EventHandler;
import me.dylanmullen.bingo.events.events.droplet.DropletJoinEvent;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.RoundedButton;
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

	private RoundedButton joinButton;

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
		this.set = BingoApp.getInstance().getColours().getSet("droplets");
	}

	public void setup()
	{
		int width = getWidth() / 4;
		joinButton = new RoundedButton("Join Dropet",
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
		joinButton.updateColours(set.getColour("join-bg"), set.getColour("join-active"));
		add(joinButton);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		drawInstanceString(g2);
	}

	private void drawInstanceString(Graphics2D g2)
	{
		String instance = "Instance #" + pos + " (" + players + "/" + max + ")";
		g2.setFont(getFont());
		g2.setColor(Color.white);
		Dimension dim = FontUtil.getFontSize(getFontMetrics(getFont()), instance, 0, 0);
		int indent = 15;
		g2.drawString(instance, indent, getHeight() / 2 + (dim.height / 4));
	}
}
