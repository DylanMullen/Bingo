package me.dylanmullen.bingo.game;

import java.util.ArrayList;

public class BingoGame
{

	private static BingoGame instance;
	private GamePanel gamePanel;

	private boolean gameJoined;
	private GameState state;

	public enum GameState
	{
		LOBBY, PLAYING, ENDING;
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
			gamePanel.getGameComponent().createCardGroup();
	}

	
	public void setNextNumber(String mes)
	{
		int number = Integer.parseInt(mes.split(";")[1].split("/m/|/m/")[1]);
		getGamePanel().getNumbersComp().update(number);

		
		getGamePanel().getGameComponent().getCardGroup().markNumber(number);
	
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
				this.state = GameState.PLAYING;
				return;
			case 2:
				this.state = GameState.ENDING;
				return;
		}
	}

	public void updateCards(String data)
	{
		try
		{
			String mes = data.split(";")[1].split("/m/|/m/")[1];
			String[] cards = mes.split("/nl/");
			getGamePanel().getGameComponent().getCardGroup().updateCardNumbers(cards);
		}catch(ArrayIndexOutOfBoundsException e)
		{
			getGamePanel().getGameComponent().getCardGroup().updateCardNumbers(null);
		}
	}

}
