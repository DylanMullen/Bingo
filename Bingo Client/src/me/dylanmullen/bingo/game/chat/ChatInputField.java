package me.dylanmullen.bingo.game.chat;

import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class ChatInputField extends JTextArea
{

	private static final long serialVersionUID = 2170803220795570917L;

	public ChatInputField(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
		setup();
	}

	private void setup()
	{
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	public int getTextAngle()
	{
		if (getText().length() == 0)
		{
			return 0;
		} else
		{
			return (int) (360 * ((double) getText().length() / 128));
		}
	}

//	@Override
//	protected void paintComponent(Graphics g)
//	{
//		super.paintComponent(g);
//
//		Graphics2D g2 = (Graphics2D) g;
//
//		int angle = getTextAngle();
//		g2.setStroke(new BasicStroke(1.5f));
//		g2.drawArc(5, 5, 40, 40, 0, angle);
//	}

}
