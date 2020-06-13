package me.dylanmullen.bingo.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.dylanmullen.bingo.game.callbacks.CardRequestCallback;
import me.dylanmullen.bingo.game.components.listeners.BingoCardListener;
import me.dylanmullen.bingo.game.components.overlays.PurchaseOverlay;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.window.ui.Panel;

public class CardGroupComp extends Panel
{

	private static final long serialVersionUID = -2951295854163796573L;

	private BingoCard[] cards;

	private List<PurchaseOverlay> overlays = new ArrayList<PurchaseOverlay>();

	public CardGroupComp(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.cards = new BingoCard[3];
		setOpaque(false);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);

		for (int i = 0; i < cards.length; i++)
		{
			cards[i] = new BingoCard(25, getIndentY(i), (getWidth() - 50), getContainerHeight());
			cards[i].addMouseListener(new BingoCardListener(this));
			add(cards[i]);
		}
		setBackground(Color.WHITE);
	}

	private int getIndentY(int pos)
	{
		return (getContainerHeight() * pos) + (36 * pos) + (10 * pos) + 5;
	}

	private int getContainerHeight()
	{
		int contHeight = getHeight() - (5 * 5);
		return height = contHeight / 3 - 36;
	}

	public void setCardNumbers(String[] data)
	{
		int index = 0;
		for (int i = 1; i < data.length; i++)
		{
			if (index > cards.length)
				break;
			if (cards[index] == null)
				break;

			String[] temp = data[i].split("/u/");
			UUID uuid = UUID.fromString(temp[1]);
			cards[index].setCardNumbers(temp[0], uuid);
			cards[index].setVisible(true);
			cards[index].setY(getIndentY(index));
			index++;
		}
		for (int i = 0; i < cards.length; i++)
			cards[i].repaint();
	}

	public void updateCardNumbers(String[] data)
	{
		if (data == null)
			return;

		for (int i = 0; i < data.length; i++)
		{
			UUID uuid = UUID.fromString(data[i]);
			BingoCard card = getCard(uuid);
			card.setPurchased(true);
			card.setY(getIndentY(i));
			card.repaint();

		}

		for (BingoCard card : cards)
			if (!card.isPurchased())
			{
				card.setVisible(false);
				card.setSelected(false);
			}

		repaint();
	}

	@Override
	public void create()
	{

	}

	public BingoCard getCard(UUID uuid)
	{
		for (BingoCard card : cards)
			if (card.getUUID().toString().equals(uuid.toString()))
				return card;
		return null;
	}

	public void markNumber(int num)
	{
		for (BingoCard card : cards)
		{
			if (!card.isPurchased())
				continue;
			card.markNumber(num);
		}
	}

	public boolean hasCards()
	{
		for (BingoCard card : cards)
		{
			if (card.isVisible())
				return true;
		}
		return false;
	}

	public PurchaseOverlay getOverlay(BingoCard card)
	{
		for (PurchaseOverlay overlay : overlays)
			if (overlay.getCard().equals(card))
			{
				System.out.println("RET");
				return overlay;
			}

		PurchaseOverlay overlay = new PurchaseOverlay(card, Color.red, 100, 0, card.BUFFER, card.getWidth(),
				card.getHeight() - card.BUFFER);
		overlays.add(overlay);
		card.add(overlay);
		card.repaint();
		return overlay;
	}

	public void showSelector(BingoCard card)
	{
		getOverlay(card).setVisible(true);
		card.setSelected(true);
		repaint();
	}

	public void disableSelector(BingoCard card)
	{
//		getOverlay(card).setVisible(false);
		card.setSelected(false);
		repaint();
	}

	public void disableSelectors()
	{
		for (int i = 0; i < cards.length; i++)
		{
			getOverlay(cards[i]);
			repaint();
		}
	}

	public void requestNumbers()
	{
		PacketHandler.sendPacket(PacketHandler.createPacket(007, ""), new CardRequestCallback());
	}

}
