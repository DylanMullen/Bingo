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
		return new Color(toColour().getRed(), toColour().getBlue(), toColour().getGreen(), opacity);
	}

	public Color darken(double darkenAmount)
	{
		double percent = clamp(1.0 - darkenAmount, 1.0, 0.0);
		return new Color((int) (toColour().getRed() * percent), (int) (toColour().getGreen() * percent),
				(int) (toColour().getBlue() * percent));
	}

	public Color lighten(double lightAmount)
	{
		double percent = 1.0 + clamp(lightAmount, 1.0, 0.0);
		return new Color((int) (toColour().getRed() * percent), (int) (toColour().getGreen() * percent),
				(int) (toColour().getBlue() * percent));
	}

	private double clamp(double value, double max, double min)
	{
		if (value > max)
			return max;
		else if (value < min)
			return min;
		else
			return value;
	}

	public Color toColour()
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
