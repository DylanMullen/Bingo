package me.dylanmullen.bingo.controllers;

public class GameController
{

	private static GameController instance;

	public GameController()
	{
		if (instance == null)
			instance = this;
	}

	public static GameController getInstance()
	{
		return instance;
	}
	
//	public BingoCard createBingoCard(UUID uuid)
//	{
//////		BUser user = UserManager.getInstance().getUser(uuid);
////		BingoCard card = new BingoCard();
////		card.generate();
////		user.setCard(card);
////		return card;
//	}

}
