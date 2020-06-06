package me.dylanmullen.bingo.game;

import java.util.UUID;

public class BingoGame
{

	
	private static BingoGame instance;
	private GamePanel gamePanel;

	private UUID playerUUID;

	private boolean gameJoined;
	private GameState state;
	private LineState lineState;

	public enum GameState
	{
		LOBBY, PLAYING, ENDING;
	}

	public enum LineState
	{
		ONE(0), TWO(1), FULLHOUSE(2);

		private int state;

		private LineState(int state)
		{
			this.state = state;
		}

		public int getState()
		{
			return state;
		}

		public static LineState get(int state)
		{
			for (LineState ls : values())
			{
				if (ls.getState() == state)
					return ls;
			}
			return null;
		}
	}

	public BingoGame()
	{
		if (instance == null)
			instance = this;
	}

	public static BingoGame getInstance()
	{
		return instance;
	}

	public GamePanel createPanel(int x, int y, int w, int h)
	{
		gamePanel = new GamePanel(x, y, w, h);
		return gamePanel;
	}

	public void setGameJoined(boolean gameJoined, int state)
	{
		this.gameJoined = gameJoined;
		setState(state);
		gamePanel.getGameComponent().disableJoinButton();

		if (this.state == GameState.LOBBY)
		{
			gamePanel.getHeader().getInfo().setText("Currently in Lobby");
			gamePanel.getGameComponent().createCardGroup();
		} else
			gamePanel.getHeader().getInfo().setText("Game in Progress. Please Wait...");

	}

	public void setNextNumber(String mes)
	{
		if (getGamePanel().getHeader().isShowingNumbers())
		{
			int number = Integer.parseInt(mes.split(";")[1].split("/m/|/m/")[1]);
			if (getGamePanel().getGameComponent().getWinner().isVisible())
			{
				getGamePanel().getGameComponent().getWinner().setVisible(false);
				getGamePanel().getGameComponent().getWinner().repaint();
				getGamePanel().getGameComponent().repaint();
			}

			getGamePanel().getNumbersComp().update(number);
			getGamePanel().getGameComponent().getCardGroup().markNumber(number);
		}
	}

	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public boolean isJoined()
	{
		return gameJoined;
	}

	public void setState(int state)
	{
		switch (state)
		{
			case 0:
				this.state = GameState.LOBBY;
				return;
			case 1:
			{
				this.state = GameState.PLAYING;
				showNumberComp();
				return;
			}
			case 2:
				this.state = GameState.ENDING;
				return;
		}
	}

	public void showNumberComp()
	{
		if (getGamePanel().getGameComponent().getCardGroup() != null)
		{
			gamePanel.getGameComponent().getCardGroup().disableSelectors();
			gamePanel.getHeader().showNumberComp();
		}
	}
	
	public void updateCards(String data)
	{
		try
		{
			String mes = data.split(";")[1].split("/m/|/m/")[1];
			String[] cards = mes.split("/nl/");
			getGamePanel().getGameComponent().getCardGroup().updateCardNumbers(cards);
			lineState = LineState.ONE;
		} catch (ArrayIndexOutOfBoundsException e)
		{
			getGamePanel().getGameComponent().getCardGroup().updateCardNumbers(null);
		}
	}

	public void updateLineState(String data)
	{
		String mes = data.split(";")[1].split("/m/|/m/")[1];
		lineState = LineState.get(Integer.parseInt(mes));
		System.out.println(lineState.state);
	}

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

	public void restart(String data)
	{
		String[] numbers = data.split(";")[1].split("/m/|/m/")[1].split("/c/|/c/");
		getGamePanel().getGameComponent().getCardGroup().setCardNumbers(numbers);
		getGamePanel().getGameComponent().getWinner().setVisible(false);
		getGamePanel().getNumbersComp().restart();
		getGamePanel().getGameComponent().repaint();
	}
	
	public void setPlayerUUID(UUID playerUUID)
	{
		this.playerUUID = playerUUID;
	}
	
	public UUID getPlayerUUID()
	{
		return playerUUID;
	}

}
