package me.dylanmullen.bingo.gfx.ui.colour;

import java.awt.Color;

public class UIColour
{

	private String colourName;
	private Color colour;
	private Color textColour;

	public UIColour(String name, int r, int g, int b)
	{
		this.colourName = name;
		this.colour = new Color(r, g, b);
	}

	public void setupTextColour()
	{
		double luminance = (0.299 * colour.getRed() + 0.587 * colour.getGreen() + 0.114 * colour.getBlue()) / 255;
		if (luminance > 0.5)
			textColour = Color.BLACK;
		else
			textColour = Color.WHITE;
	}

	public Color applyTransparency(int opacity)
	{
		return new Color(getColour().getRed(), getColour().getBlue(), getColour().getGreen(), opacity);
	}

	public Color getColour()
	{
		return colour;
	}

	public String getColourName()
	{
		return colourName;
	}

	public Color getTextColour()
	{
		return textColour;
	}
}
