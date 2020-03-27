package me.dylanmullen.bingo.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

public class FontUtil
{

	public FontUtil()
	{
	}

	private static Dimension getFontSize(FontMetrics metrics, Font font, String text, int LR, int TB)
	{
		int hgt = metrics.getAscent() + metrics.getDescent() + metrics.getLeading();
		int adv = metrics.stringWidth(text);
		Dimension size = new Dimension(adv + LR, hgt + TB);
		return size;
	}

	public static Font getFont(Component comp, String text, int paddingLR, int paddingTB)
	{
		Font font = new Font("Calibri", Font.PLAIN, 0);
		for (int i = 0; i < 100; i++)
		{
			Font current = new Font(font.getFontName(), font.getStyle(), i);
			Dimension dim = getFontSize(comp.getFontMetrics(current), current, text, paddingLR, paddingTB);
			if (comp.getWidth() < dim.width || comp.getHeight() < dim.height)
			{
				return current;
			}
			font = current;
		}
		return font;
	}

}
