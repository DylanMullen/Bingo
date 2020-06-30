package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Button extends UIButton
{

	private static final long serialVersionUID = 9115079720635266490L;

	public Button(String text, ButtonInformation information)
	{
		super(text, information);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getBackground());
		g2.fillRect(0, 0, getWidth(), getHeight());
		drawText(g2, 0, (getInformation().getMainColour() != null ? getInformation().getMainColour().getTextColour()
				: Color.BLACK));
	}

}
