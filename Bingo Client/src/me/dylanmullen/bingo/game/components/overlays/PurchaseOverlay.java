package me.dylanmullen.bingo.game.components.overlays;

import java.awt.Color;
import java.awt.Font;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.callbacks.PurchaseCallback;
import me.dylanmullen.bingo.game.cards.BingoCard;
import me.dylanmullen.bingo.gfx.ui.buttons.ButtonInformation;
import me.dylanmullen.bingo.gfx.ui.buttons.RoundedButton;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.packet.Packet;

public class PurchaseOverlay extends Overlay
{

	private static final long serialVersionUID = 1L;

	private BingoCard card;

	public PurchaseOverlay(BingoCard card, Color bgColour, int x, int y, int width, int height)
	{
		super(bgColour, x, y, width, height);
		this.card = card;
	}

	public void setup()
	{
		Font font = new Font("Calibri", Font.PLAIN, 35);
		UIColourSet set = BingoApp.getInstance().getColourManager().getSet("buttons");
		RoundedButton purchased = new RoundedButton("Purchase", new ButtonInformation(null, null, () ->
		{
			PacketHandler.sendPacket(constructPacket(getCard().getUUID()), new PurchaseCallback());
		}));
		RoundedButton cancel = new RoundedButton("Cancel", new ButtonInformation(null, null, () ->
		{
			card.hidePurchaseOverlay();
		}));

		int width = (getWidth() - (30 * 3)) / 2;
		purchased.setBounds(30, 30, width, getHeight() - 60);
		cancel.setBounds(getWidth() - 30 - width, 30, width, getHeight() - 60);

		add(purchased);
		add(cancel);
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
