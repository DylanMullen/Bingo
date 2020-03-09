package me.dylanmullen.bingo.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import me.dylanmullen.bingo.game.callbacks.CardRequestCallback;
import me.dylanmullen.bingo.game.components.listeners.BingoCardListener;
import me.dylanmullen.bingo.game.components.listeners.PurchaseListener;
import me.dylanmullen.bingo.gfx.ImageAtlas;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.window.ui.ImageComponent;
import me.dylanmullen.bingo.window.ui.Panel;

public class CardGroupComp extends Panel
{

	private static final long serialVersionUID = -2951295854163796573L;

	private BingoCard[] cards;
	private HashMap<BingoCard, ImageComponent> selectors = new HashMap<BingoCard, ImageComponent>();

	private final ImageAtlas ATLAS = new ImageAtlas("uiAtlas.png", 42);

	public CardGroupComp(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setOpaque(false);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		this.cards = new BingoCard[3];

		int indentY = 5;
		int contHeight = getHeight() - (5 * 5);
		int height = contHeight / 3 - 35;
		for (int i = 0; i < cards.length; i++)
		{
			cards[i] = new BingoCard(5, indentY, ((getWidth() - 10) / 4) * 3, height);
			cards[i].addMouseListener(new BingoCardListener(this));

			ImageComponent ic = new ImageComponent(cards[i].getX() + cards[i].getWidth() + 5, indentY,
					(getWidth() - cards[i].getWidth()) - 15, cards[i].getHeight());
			ic.setVisible(false);
			ic.setImage(ATLAS.getImage(0, 0));
			ic.addMouseListener(new PurchaseListener(cards[i]));
			indentY += height + 35 + 10;

			add(cards[i]);
			add(ic);
			selectors.put(cards[i], ic);
		}
		setBackground(Color.WHITE);
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
			index++;
		}
		for (int i = 0; i < cards.length; i++)
			cards[i].repaint();
	}

	public void updateCardNumbers(String[] data)
	{
		if (data == null)
		{
			for (BingoCard card : cards)
			{
				card.setVisible(false);
				card.setPurchased(false);
				card.repaint();
			}
			return;
		}

		ArrayList<UUID> cardsToUpdate = new ArrayList<>();
		for (String j : data)
		{
			UUID uuid = UUID.fromString(j);
			cardsToUpdate.add(uuid);
		}

		for (BingoCard card : cards)
		{
			UUID cardU = card.getUUID();
			if (!cardsToUpdate.contains(cardU))
			{
				card.setVisible(false);
				card.setPurchased(false);
				card.repaint();
			}
		}

	}

	@Override
	public void create()
	{

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

	public void showSelector(BingoCard card)
	{
		selectors.get(card).setVisible(true);
		repaint();
	}

	public void requestNumbers()
	{
		PacketHandler.sendPacket(PacketHandler.createPacket(007, ""), new CardRequestCallback());
	}

}
