package me.dylanmullen.bingo.game.components.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.game.callbacks.PurchaseCallback;
import me.dylanmullen.bingo.game.components.overlays.PurchaseOverlay;
import me.dylanmullen.bingo.net.PacketHandler;

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
		PacketHandler.sendPacket(PacketHandler.createPacket(8, overlay.getCard().getUUID().toString()),
				new PurchaseCallback(overlay.getCard()));
		lastSent = System.currentTimeMillis();
	}
}
