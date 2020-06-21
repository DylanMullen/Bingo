package me.dylanmullen.bingo.game;

import java.awt.Color;
import java.util.UUID;

import me.dylanmullen.bingo.game.callbacks.CardRequestCallback;
import me.dylanmullen.bingo.game.components.listeners.BingoCardListener;
import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.window.ui.Panel;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class BingoCardContainer extends Panel
{

	private static final long serialVersionUID = -2951295854163796573L;

	private BingoCard[] cards;

	/**
	 * This is the Bingo Card Container.<br>
	 * This contains all the Bingo Card that the Player has. This will handle
	 * updating the card numbers and managing cards being marked.
	 * 
	 * @param x      X-Position of the container
	 * @param y      Y-Position of the container
	 * @param width  The width of the container
	 * @param height The height of the container
	 */
	public BingoCardContainer(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.cards = new BingoCard[3];
		setOpaque(false);
	}

	@Override
	public void setup()
	{
		setBounds(this.x, this.y, this.width, this.height);
		setLayout(null);
		setBackground(Color.WHITE);

		for (int i = 0; i < getCards().length; i++)
		{
			getCards()[i] = new BingoCard(25, getCardIndentY(i), (getWidth() - 50), getCardHeight());
			getCards()[i].addMouseListener(new BingoCardListener(this));
			add(getCards()[i]);
		}
	}

	/**
	 * Returns the indent y-position value of the card.
	 * 
	 * @param cardIndex
	 * @return
	 */
	private int getCardIndentY(int cardIndex)
	{
		return (getCardHeight() * cardIndex) + (36 * cardIndex) + (10 * cardIndex) + 5;
	}

	/**
	 * Returns the height of the card.
	 * 
	 * @return
	 */
	private int getCardHeight()
	{
		int contHeight = getHeight() - (5 * 5);
		return contHeight / 3 - 36;
	}

	/**
	 * Sets the Bingo Card information for all the cards of the container.
	 * 
	 * @param numbersData
	 */
	public void setCardInformation(String[] numbersData)
	{
		int index = 0;
		for (int i = 1; i < numbersData.length; i++)
		{
			if (index > getCards().length)
				break;
			if (getCards()[index] == null)
				break;

			String[] temp = numbersData[i].split("/u/");
			UUID uuid = UUID.fromString(temp[1]);

			getCards()[index].updateCardInformation(temp[0], uuid);
			getCards()[index].setVisible(true);
			getCards()[index].setY(getCardIndentY(index));
			getCards()[index].repaint();

			index++;
		}
	}

	/**
	 * Updates the cards based on if they were purchased or not. If the cards are
	 * purchased they are set visible and moved to the top of the container.
	 * 
	 * @param cardUUIDs UUIDs of the cards that were purchased.
	 */
	public void updatePurchasedCards(String[] cardUUIDs)
	{
		if (cardUUIDs == null)
			return;
		
		for (int i = 0; i < cardUUIDs.length; i++)
		{
			UUID uuid = UUID.fromString(cardUUIDs[i]);
			BingoCard card = getCard(uuid);

			card.setPurchased(true);
			card.setY(getCardIndentY(i));
			card.repaint();
		}

		for (BingoCard card : getCards())
			if (!card.isPurchased())
			{
				card.setVisible(false);
				card.hidePurchaseOverlay();
			}

		repaint();
	}

	@Override
	public void create()
	{

	}

	/**
	 * Returns a Bingo Card that has the smae UUID.
	 * 
	 * @param uuid The UUID to search for.
	 * @return {@link BingoCard}
	 */
	public BingoCard getCard(UUID uuid)
	{
		for (BingoCard card : getCards())
			if (card.getUUID().toString().equals(uuid.toString()))
				return card;
		return null;
	}

	/**
	 * Marks a number on the cards.
	 * 
	 * @param numberToMark The number to mark
	 */
	public void markNumber(int numberToMark)
	{
		for (BingoCard card : getCards())
		{
			if (!card.isPurchased())
				continue;
			card.markNumber(numberToMark);
		}
	}

	/**
	 * Selects the Bingo Card.
	 * 
	 * @param card Bingo Card to select
	 */
	public void selectCard(BingoCard card)
	{
		card.showPurchaseOverlay();
		repaint();
	}

	/**
	 * Deselects the Bingo Card
	 * 
	 * @param card Bingo Card to deselect.s
	 */
	public void deselectCard(BingoCard card)
	{
		card.hidePurchaseOverlay();
		repaint();
	}

	/**
	 * Deselect all Bingo Cards
	 */
	public void disableSelectors()
	{
		for (int i = 0; i < getCards().length; i++)
		{
			getCards()[i].hidePurchaseOverlay();
		}
	}

	/**
	 * Request Bingo Cards from the server.
	 */
	public void requestBingoCards()
	{
		PacketHandler.sendPacket(PacketHandler.createPacket(007, ""), new CardRequestCallback());
	}

	/**
	 * Returns the Bingo Cards.
	 * 
	 * @return {@link #cards}
	 */
	public BingoCard[] getCards()
	{
		return this.cards;
	}
}
