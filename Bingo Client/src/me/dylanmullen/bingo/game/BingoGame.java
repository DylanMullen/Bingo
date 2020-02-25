package me.dylanmullen.bingo.game;

public class BingoGame
{

	private static BingoGame instance;
	private GamePanel gamePanel;

	private boolean gameJoined;

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

	public void setGameJoined(boolean gameJoined)
	{
		this.gameJoined = gameJoined;
		gamePanel.getGameComponent().disableJoinButton();
	}
	
	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public boolean isJoined()
	{
		return gameJoined;
	}

}
