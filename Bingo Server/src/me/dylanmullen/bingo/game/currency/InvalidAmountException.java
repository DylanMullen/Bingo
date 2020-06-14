package me.dylanmullen.bingo.game.currency;

import me.dylanmullen.bingo.game.user.User;

public class InvalidAmountException extends Exception
{

	private static final long serialVersionUID = 8731969259917943606L;

	private User user;
	private double currentAmount;
	private double amount;

	public InvalidAmountException(User u, double current, double amount)
	{
		super("");
		this.user = u;
		this.currentAmount = current;
		this.amount = amount;
	}

	@Override
	public String getMessage()
	{
		return "Failed to deduct(User=" + user.getUUID().toString() + ",Balance="
				+ user.getUserInformation().getCredits() + ",Deduction=" + amount + ",Invalid="
				+ (currentAmount - amount);
	}

}
