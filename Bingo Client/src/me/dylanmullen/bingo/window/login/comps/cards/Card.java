package me.dylanmullen.bingo.window.login.comps.cards;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import me.dylanmullen.bingo.window.login.LoginWindow;

public abstract class Card extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8804054765754472337L;

	public abstract void generateContent();
	public abstract void generateLabels();
	public abstract void styleLabels();
	
	protected LoginWindow window;
	protected String name;
	protected ArrayList<JLabel> buttons = new ArrayList<>();
	
	public Card(LoginWindow lw, String name)
	{
		this.window=lw;
		this.name=name;
		setName("l_c_"+name);
		setLayout(null);
	}
	
	public String getCardName()
	{
		return name;
	}
}
