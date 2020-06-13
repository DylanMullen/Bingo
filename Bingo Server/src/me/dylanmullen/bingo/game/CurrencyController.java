package me.dylanmullen.bingo.game;

import me.dylanmullen.bingo.game.user.User;

public class CurrencyController
{

	public CurrencyController()
	{
	}

	public void deposit(User u, int credits)
	{
		u.getUserInformation().setCredits(u.getUserInformation().getCredits() + credits);
		// TODO send packet
	}

	public void deduct(User u, int credits)
	{
		int userCredits = u.getUserInformation().getCredits() - credits;
		if (userCredits < 0)
			userCredits = 0;
		u.getUserInformation().setCredits(credits);
		
		//TODO send Packet
	}
	
	

}
