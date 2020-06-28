package me.dylanmullen.bingo.gfx.ui.colour;

import java.awt.Color;

public class UIColour
{

	private String colourName;
	private Color colour;

	public UIColour(String name, int r, int g, int b)
	{
		this.colourName = name;
		this.colour = new Color(r, g, b);
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

}
