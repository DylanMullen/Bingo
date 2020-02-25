package me.dylanmullen.bingo.game.components;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.game.components.buttons.JoinButton;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class JoinComponent extends Panel
{

	private JLabel text;
	private JoinButton button;

	public JoinComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setBackground(UIColour.FRAME_BINGO_BG_TOP.toColor());
		this.text = new JLabel();
		text.setBounds(50, (getHeight() / 2) - ((getHeight() / 4))-12, getWidth() - 100, getHeight() / 4);
		text.setOpaque(true);
		text.setText("You must first join a game!");
		text.setHorizontalAlignment(SwingConstants.CENTER);
		add(text);

		button = new JoinButton("Join!", text.getX(), (getHeight() / 2) + 12, text.getWidth(), 60);
		button.create();
		add(button);
	}

	@Override
	public void create()
	{
		// TODO Auto-generated method stub

	}

}
