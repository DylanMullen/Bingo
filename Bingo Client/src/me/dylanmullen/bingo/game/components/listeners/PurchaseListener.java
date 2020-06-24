package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.callbacks.PurchaseCallback;
import me.dylanmullen.bingo.game.components.overlays.PurchaseOverlay;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;

public class PurchaseListener extends MouseAdapter
{

	private long SENT_DELAY = 2500;

	private PurchaseOverlay overlay;
	private long lastSent;

	public PurchaseListener(PurchaseOverlay overlay)
	{
		this.overlay = overlay;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (System.currentTimeMillis() - lastSent <= SENT_DELAY)
			return;
		if (overlay == null)
			return;
		if (overlay.getCard() == null)
			return;
		PacketHandler.sendPacket(constructPacket(overlay.getCard().getUUID()), new PurchaseCallback());
		lastSent = System.currentTimeMillis();
	}

	private Packet constructPacket(UUID uuid)
	{
		JSONObject message = new JSONObject();
		message.put("dropletUUID", overlay.getCard().getDropletUUID().toString());
		message.put("cardUUID", uuid.toString());
		return PacketHandler.createPacket(8, message);
	}
}
