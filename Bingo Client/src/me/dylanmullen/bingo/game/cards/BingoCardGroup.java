package me.dylanmullen.bingo.game.cards;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.dylanmullen.bingo.game.components.listeners.BingoCardListener;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class BingoCardGroup extends UIPanel
{

	private static final long serialVersionUID = -2951295854163796573L;

	private UUID dropletUUID;
	private List<BingoCard> cards;

	private CardPagnationPanel pagnation;

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
	public BingoCardGroup(UUID dropletUUID, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.dropletUUID = dropletUUID;
	}

	public void createCards(List<CardInformation> information)
	{
		int row = 0;
		for (int i = 0; i < information.size(); i++)
		{
			if (i % 2 == 0 && i != 0)
				row++;
			CardInformation info = information.get(i);
			BingoCard card = new BingoCard(dropletUUID, getCardIndentX(i + 1), getCardIndentY(row, i),
					getCardWidth(getWidth() / 2), getCardHeight(), info);
			card.addMouseListener(new BingoCardListener(this));
			cards.add(card);
			add(card);

		}
		repaint();
	}

	private int getCardWidth(int width)
	{
		int widthSize = width / 9;
		if ((double) (width / 9) > 0)
			widthSize = widthSize * 9;
		return widthSize;
	}

	private int getCardIndentX(int i)
	{
		if (i % 2 == 0)
		{
			return getWidth() / 2 + getCardXOffset() / 2;
		} else
			return getCardXOffset() / 2;
	}

	private int getCardXOffset()
	{
		return getWidth() / 2 - getCardWidth(getWidth() / 2);
	}

	@Override
	public void setup()
	{
		setBounds(this.x, this.y, this.width, this.height);
		setLayout(null);
		setBackground(Color.WHITE);

		this.cards = new ArrayList<BingoCard>();
		this.pagnation = new CardPagnationPanel(getWidth() / 4, getHeight() - getHeight() / 10, getWidth() / 2,
				getHeight() / 10);

		add(pagnation);
		setOpaque(false);
	}

	/**
	 * Returns the indent y-position value of the card.
	 * 
	 * @param cardIndex
	 * @return
	 */
	private int getCardIndentY(int row, int cardIndex)
	{
		return row * (getCardHeight() * 2) + 5 + (row * 5) + getCardYOffset() / 2;
	}

	private int getCardYOffset()
	{
		return (getHeight() - this.pagnation.getHeight()) - getMaxCardHeight();
	}

	private int getMaxCardHeight()
	{
		return 3 * (getCardHeight() * 2) + 5 + (3 * 5);
	}

	/**
	 * Returns the height of the card.
	 * 
	 * @return
	 */
	private int getCardHeight()
	{
		return (getCardWidth(getWidth() / 2) / 9) + 36;
	}

	/**
	 * Sets the Bingo Card information for all the cards of the container.
	 * 
	 * @param numbersData
	 */
	public void setCardInformation(List<CardInformation> newCards)
	{
		int index = 0;
		for (int i = 0; i < newCards.size(); i++)
		{
			if (index > getCards().size())
				break;
			getCards().get(index).updateCardInformation(newCards.get(i));
			getCards().get(index).setVisible(true);
			getCards().get(index).setY(getCardIndentY(i / 2, index));
			getCards().get(index).repaint();
			index++;
		}
	}

	/**
	 * Updates the cards based on if they were purchased or not. If the cards are
	 * purchased they are set visible and moved to the top of the container.
	 * 
	 * @param purchasedCards UUIDs of the cards that were purchased.
	 */
	public void updatePurchasedCards(List<UUID> purchasedCards)
	{
		if (purchasedCards == null)
			return;
		
		int index = 0;
		for (int i = 0; i < purchasedCards.size(); i++)
		{
			UUID uuid = purchasedCards.get(i);
			BingoCard card = getCard(uuid);

			card.setPurchased(true);
			card.setY(getCardIndentY(i / 2, index));
			card.repaint();
			index++;
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
		for (BingoCard card : getCards())
			if (!card.isPurchased())
			{
				card.setVisible(false);
				card.hidePurchaseOverlay();
			}
	}

	/**
	 * Returns the Bingo Cards.
	 * 
	 * @return {@link #cards}
	 */
	public List<BingoCard> getCards()
	{
		return this.cards;
	}

	public void setCardPurchased(UUID cardUUID)
	{
		getCard(cardUUID).setPurchased(true);
		getCard(cardUUID).hidePurchaseOverlay();
		repaint();
	}

	public void deselectNonPurchased()
	{
		for (BingoCard card : cards)
			if (!card.isPurchased())
				deselectCard(card);
	}
}
