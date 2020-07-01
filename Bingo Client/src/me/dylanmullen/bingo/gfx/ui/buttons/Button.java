package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

public class Button extends UIButton
{

	private static final long serialVersionUID = 9115079720635266490L;
	private Polygon customShape;

	public Button(String text, ButtonInformation information)
	{
		super(text, information);
	}

	public Button(String text, Polygon customShape, ButtonInformation information)
	{
		super(text, information);
		this.customShape = customShape;
	}

	public void setCustomShape(Polygon customShape)
	{
		this.customShape = customShape;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		if (customShape != null)
		{
			g2.fillPolygon(customShape);
		} else
			g2.fillRect(0, 0, getWidth(), getHeight());
		drawText(g2, 0, (getInformation().getMainColour() != null ? getInformation().getMainColour().getTextColour()
				: Color.BLACK));
	}

}
