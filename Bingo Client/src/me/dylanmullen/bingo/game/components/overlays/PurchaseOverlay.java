package me.dylanmullen.bingo.game.components.overlays;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.components.listeners.PurchaseListener;
import me.dylanmullen.bingo.window.ui.RoundedButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class PurchaseOverlay extends Overlay
{

	private static final long serialVersionUID = 1L;

	private BingoCard card;

	public PurchaseOverlay(BingoCard card, Color bgColour, int opacity, int x, int y, int width, int height)
	{
		super(bgColour, opacity, x, y, width, height);
		this.card = card;
	}

	public void setup()
	{
		Font font = new Font("Calibri", Font.PLAIN, 35);
		RoundedButton purchased = new RoundedButton("Purchase", font, UIColour.BTN_BINGO_ACTIVE);
		RoundedButton cancel = new RoundedButton("Cancel", font, UIColour.BINGO_BALL_1);

		purchased.addMouseListener(new PurchaseListener(this));
		cancel.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				card.hidePurchaseOverlay();
			}
		});

		int width = (getWidth() - (30 * 3)) / 2;
		purchased.setBounds(30, 30, width, getHeight() - 60);
		cancel.setBounds(getWidth() - 30 - width, 30, width, getHeight() - 60);

		purchased.create();
		cancel.create();

		add(purchased);
		add(cancel);
	}

	public BingoCard getCard()
	{
		return card;
	}
}
