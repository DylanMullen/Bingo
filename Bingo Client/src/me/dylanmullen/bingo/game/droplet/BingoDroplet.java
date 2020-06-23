package me.dylanmullen.bingo.game.droplet;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.GamePanel;
import me.dylanmullen.bingo.game.cards.CardInformation;

public class BingoDroplet
{

	private UUID uuid;
	private GamePanel gamePanel;

	private GameState gameState;
	private LineState lineState;

	public BingoDroplet(UUID uuid, GamePanel game)
	{
		this.uuid = uuid;
		this.gamePanel = game;
	}

	public void startDropletGame(List<UUID> purchasedCards)
	{
		deselectCards();
		showCalledNumberComponent();
		getGamePanel().getGameComponent().getCardGroup().updatePurchasedCards(purchasedCards);
	}

	public void restartDropletGame(List<CardInformation> newCards)
	{
		getGamePanel().getGameComponent().hideWinnerOverlay();
		getGamePanel().getGameComponent().getCardGroup().setCardInformation(newCards);
		getGamePanel().getHeaderComponent().getNumbersComp().restart();
		getGamePanel().getGameComponent().repaint();
	}

	/**
	 * Updates the Winners Panel with the list of Winners<br>
	 * The Winner Panel is then shown.
	 * 
	 * @param list The packet data to decode.
	 */
	public void showWinners(List<String> list)
	{
		getGamePanel().getGameComponent().getWinner().setWinners(list);
		getGamePanel().getGameComponent().repaint();
	}

	/**
	 * Updates the Bingo Cards values based on the new data.
	 * 
	 * @param data The packet data to decode.
	 */
	public void updateCards(List<UUID> cardUUIDs)
	{
		getGamePanel().getGameComponent().getCardGroup().updatePurchasedCards(cardUUIDs);
	}

	/**
	 * Shows the called numbers component.<br>
	 * This will also disable any cards that are selected.
	 */
	public void showCalledNumberComponent()
	{
		getGamePanel().getHeaderComponent().showNumberComp();
	}

	private void deselectCards()
	{
		if (getGamePanel().getGameComponent().getCardGroup() != null)
			getGamePanel().getGameComponent().getCardGroup().disableSelectors();
	}

	/**
	 * Sets the next number of the current Bingo Game. <br>
	 * This will then update the numbers being shown and mark off the number if the
	 * player has it.
	 * 
	 * @param number The number to set to.
	 */
	public void setNextNumber(int number)
	{
		getGamePanel().getHeaderComponent().getNumbersComp().update(number);
		getGamePanel().getGameComponent().getCardGroup().markNumber(number);
	}

	/**
	 * Sets the Game State of the Bingo Game
	 * 
	 * @param stateValue The state value to check for.
	 */
	public void setGameState(GameState state)
	{
		this.gameState = state;
	}

	/**
	 * Sets the new Line State of the game.
	 * 
	 * @param lineState The new Line State.
	 */
	public void setLineState(LineState lineState)
	{
		this.lineState = lineState;
	}

	/**
	 * Updates the line state after receiving the new state from the packet.
	 * 
	 * @param data The packet data to decode.
	 */
	public void updateLineState(int state)
	{
		lineState = LineState.get(state);
	}

	public UUID getUUID()
	{
		return uuid;
	}

	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public void setCardPuchased(UUID cardUUID)
	{
		getGamePanel().getGameComponent().getCardGroup().setCardPurchased(cardUUID);
	}

}