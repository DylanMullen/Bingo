package me.dylanmullen.bingo.controllers;

import java.util.UUID;

import me.dylanmullen.bingo.game.BingoCard;
import me.dylanmullen.bingo.game.user.BUser;
import me.dylanmullen.bingo.game.user.UserManager;

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
	
	public BingoCard createBingoCard(UUID uuid)
	{
		BUser user = UserManager.getInstance().getUser(uuid);
		BingoCard card = new BingoCard();
		card.generate();
		user.setCard(card);
		return card;
	}

}
