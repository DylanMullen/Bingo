package me.dylanmullen.bingo.window.login.comps;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import me.dylanmullen.bingo.window.login.LoginWindow;
import me.dylanmullen.bingo.window.login.comps.cards.Card;
import me.dylanmullen.bingo.window.login.comps.cards.HomeCard;
import me.dylanmullen.bingo.window.login.comps.cards.LoginCard;

public class LoginContent extends JPanel
{

	private Color background = new Color(0x6a89cc);
	private ArrayList<Card> cards = new ArrayList<>();
	private CardLayout cl;

	/**
	 * Create the panel.
	 */
	public LoginContent(LoginWindow lw)
	{
		setBackground(background);
		setLayout(new CardLayout(0, 0));

		generateCards(lw);
		populate();
		display();
	}

	private void generateCards(LoginWindow lw)
	{
		cards.add(new HomeCard(lw, "home"));
		cards.add(new LoginCard(lw, "login"));
	}

	private void populate()
	{
		for (int i = 0; i < cards.size(); i++)
		{
			Card c = cards.get(i);
			add(c, c.getCardName());
			c.setBackground(getBackground());
		}
	}

	private void display()
	{
		cl = (CardLayout) getLayout();
		cl.first(this);
	}

	public CardLayout getCl()
	{
		return cl;
	}

}
