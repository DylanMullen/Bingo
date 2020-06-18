package me.dylanmullen.bingo.game;

import me.dylanmullen.bingo.window.bingo.BingoWindow;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class BingoGame
{

	// TODO make this an object to allow multiple Bingo Games.

	private static BingoGame gameInstance;
	private GamePanel gamePanel;

	private UserInformation userInformation;

	private boolean gameJoined;
	private GameState gameState;
	private LineState lineState;

	private BingoWindow bingoWindow;

	public enum GameState
	{
		LOBBY(0), PLAYING(1), ENDING(2);

		private int stateValue;

		/**
		 * The Game State of the Bingo Game.
		 * 
		 * @param stateValue The State Value.
		 */
		private GameState(int stateValue)
		{
			this.stateValue = stateValue;
		}

		/**
		 * Returns the state value of the Game State.
		 * 
		 * @return {@link #stateValue}
		 */
		public int getStateValue()
		{
			return this.stateValue;
		}

		/**
		 * Returns a Game State based on the state value.
		 * 
		 * @param stateValue State value to check for.
		 * @return Returns a Game State. If it does not exist, returns null.
		 */
		public static GameState getGameState(int stateValue)
		{
			for (GameState states : values())
				if (states.getStateValue() == stateValue)
					return states;
			return null;
		}
	}

	public enum LineState
	{
		ONE(0), TWO(1), FULLHOUSE(2);

		private int lines;

		/**
		 * The Line State of the current Bingo Game.
		 * 
		 * @param lines
		 */
		private LineState(int lines)
		{
			this.lines = lines;
		}

		/**
		 * Returns the lines of the Line State.
		 * 
		 * @return {@link #lines}
		 */
		public int getLines()
		{
			return this.lines;
		}

		/**
		 * Returns the LineState based on the lines.
		 * 
		 * @param lines Lines of the state you are looking for.
		 * @return LineState
		 */
		public static LineState get(int lines)
		{
			for (LineState lineState : values())
			{
				if (lineState.getLines() == lines)
					return lineState;
			}
			return null;
		}
	}

	/**
	 * Bingo Game main class. This handles the logic of the game.
	 */
	public BingoGame()
	{
		if (BingoGame.gameInstance == null)
			BingoGame.gameInstance = this;
	}

	/**
	 * Returns the instance of the Bingo Game
	 * 
	 * @return {@link #gameInstance}
	 */
	public static BingoGame getInstance()
	{
		return BingoGame.gameInstance;
	}

	/**
	 * Creates the Game Panel of the Bingo Game.
	 * 
	 * @param x      X-Position of the Panel.
	 * @param y      Y-Position of the Panel.
	 * @param width  Width of the Panel.
	 * @param height Height of the Panel.
	 * @return {@link #gamePanel}
	 */
	public GamePanel createPanel(int x, int y, int width, int height)
	{
		if (this.gamePanel == null)
			this.gamePanel = new GamePanel(x, y, width, height);
		return this.gamePanel;
	}

	/**
	 * Sets the Game Joined and updates the current state of the Bingo Game. <br>
	 * This method also updates the Game Panel to update the header of the lobby
	 * message.
	 * 
	 * @param gameJoined Whether or not the game has been joined.
	 * @param state      The Game State.
	 */
	public void setGameJoined(boolean gameJoined, int state)
	{
		this.gameJoined = gameJoined;
		setGameState(state);
		this.gamePanel.getGameComponent().disableJoinButton();

		if (getGameState().equals(GameState.LOBBY))
		{
			this.gamePanel.getHeaderComponent().getInfo().setText("Currently in Lobby");
			this.gamePanel.getGameComponent().createCardGroup();
		} else
			this.gamePanel.getHeaderComponent().getInfo().setText("Game in Progress. Please Wait...");

	}

	/**
	 * Sets the next number of the current Bingo Game. <br>
	 * This will then update the numbers being shown and mark off the number if the
	 * player has it.
	 * 
	 * @param data The packet data to decode.
	 */
	public void setNextNumber(String data)
	{
		if (getGamePanel().getHeaderComponent().isShowingNumbers())
		{
			int number = Integer.parseInt(data.split(";")[1].split("/m/|/m/")[1]);
			if (getGamePanel().getGameComponent().getWinner().isVisible())
			{
				getGamePanel().getGameComponent().getWinner().setVisible(false);
				getGamePanel().getGameComponent().getWinner().repaint();
				getGamePanel().getGameComponent().repaint();
			}

			getGamePanel().getHeaderComponent().getNumbersComp().update(number);
			getGamePanel().getGameComponent().getCardGroup().markNumber(number);
		}
	}

	/**
	 * Shows the called numbers component.<br>
	 * This will also disable any cards that are selected.
	 */
	public void showCalledNumberComponent()
	{
		if (getGamePanel().getGameComponent().getCardGroup() != null)
		{
			getGamePanel().getGameComponent().getCardGroup().disableSelectors();
			getGamePanel().getHeaderComponent().showNumberComp();
		}
	}

	/**
	 * Updates the Bingo Cards values based on the new data.
	 * 
	 * @param data The packet data to decode.
	 */
	public void updateCards(String data)
	{
		try
		{
			String mes = data.split(";")[1].split("/m/|/m/")[1];
			String[] cards = mes.split("/nl/");

			getGamePanel().getGameComponent().getCardGroup().updatePurchasedCards(cards);
			setLineState(LineState.ONE);
		} catch (ArrayIndexOutOfBoundsException e)
		{
			getGamePanel().getGameComponent().getCardGroup().updatePurchasedCards(null);
		}
	}

	/**
	 * Updates the line state after receiving the new state from the packet.
	 * 
	 * @param data The packet data to decode.
	 */
	public void updateLineState(String data)
	{
		String mes = data.split(";")[1].split("/m/|/m/")[1];
		lineState = LineState.get(Integer.parseInt(mes));
	}

	/**
	 * Updates the Winners Panel with the list of Winners<br>
	 * The Winner Panel is then shown.
	 * 
	 * @param data The packet data to decode.
	 */
	public void showWinners(String data)
	{
		if (getGamePanel().getGameComponent().getCardGroup() != null)
		{
			String[] winners = data.split(";")[1].split("/m/|/m/");
			getGamePanel().getGameComponent().getWinner().setWinners(winners);
			getGamePanel().getGameComponent().getWinner().setVisible(true);
			getGamePanel().getGameComponent().repaint();
		}
	}

	/**
	 * Restart the Bingo Game and return the default values. <br>
	 * This method will also set the new numbers of the potential cards.
	 * 
	 * @param data The packet data to decode
	 */
	public void restart(String data)
	{
		String[] numbers = data.split(";")[1].split("/m/|/m/")[1].split("/c/|/c/");
		getGamePanel().getGameComponent().getCardGroup().setCardInformation(numbers);
		getGamePanel().getGameComponent().getWinner().setVisible(false);
		getGamePanel().getHeaderComponent().getNumbersComp().restart();
		getGamePanel().getGameComponent().repaint();
	}

	/**
	 * Sets the Game State of the Bingo Game
	 * 
	 * @param stateValue The state value to check for.
	 */
	public void setGameState(int stateValue)
	{
		this.gameState = GameState.getGameState(stateValue);
	}

	/**
	 * Sets the User Information retrieved from the server.
	 * 
	 * @param userInformation The User Information from the server.
	 */
	public void setUserInformation(UserInformation userInformation)
	{
		this.userInformation = userInformation;
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
	 * Sets the Bingo Window of the Bingo Game.
	 * 
	 * @param bingoWindow The Bingo Window of the application.
	 */
	public void setBingoWindow(BingoWindow bingoWindow)
	{
		this.bingoWindow = bingoWindow;
	}

	/**
	 * Returns the Game Panel for the Bingo Game.
	 * 
	 * @return {@link #gamePanel}
	 */
	public GamePanel getGamePanel()
	{
		return this.gamePanel;
	}

	/**
	 * Returns if the player has joined a game.
	 * 
	 * @return {@link #gameJoined}
	 */
	public boolean hasJoined()
	{
		return this.gameJoined;
	}

	/**
	 * Returns the User Information.
	 * 
	 * @return {@link #userInformation}
	 */
	public UserInformation getUserInformation()
	{
		return this.userInformation;
	}

	/**
	 * Returns the current Game State of the Bingo Game.
	 * 
	 * @return {@link #gameState}
	 */
	public GameState getGameState()
	{
		return this.gameState;
	}

	/**
	 * Returns the current Line State of the Bingo Game.
	 * 
	 * @return {@link #lineState}
	 */
	public LineState getLineState()
	{
		return this.lineState;
	}

	/**
	 * Returns the Bingo Window of the Bingo Application
	 * 
	 * @return {@link #bingoWindow}
	 */
	public BingoWindow getBingoWindow()
	{
		return this.bingoWindow;
	}
}
