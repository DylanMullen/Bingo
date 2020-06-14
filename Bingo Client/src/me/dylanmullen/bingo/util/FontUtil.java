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

	public static Dimension getFontSize(FontMetrics metrics, Font font, String text, int LR, int TB)
	{
		int hgt = metrics.getAscent() + metrics.getDescent() + metrics.getLeading();
		int adv = metrics.stringWidth(text);
		Dimension size = new Dimension(adv + LR, hgt + TB);
		return size;
	}
	
	@Deprecated
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

	@Deprecated
	public static Font getFont(Component comp, String text, int paddingLR, int paddingTB, int minimum)
	{
		Font font = new Font("Calibri", Font.PLAIN, 0);
		for (int i = 0; i < 100; i++)
		{
			Font current = new Font(font.getFontName(), font.getStyle(), i);
			Dimension dim = getFontSize(comp.getFontMetrics(current), current, text, paddingLR, paddingTB);
			if (comp.getWidth() < dim.width || comp.getHeight() < dim.height)
			{
				font = current;
				break;
			}
			font = current;
		}
		if (font.getSize() < minimum)
		{
			font = new Font("Calibri", Font.PLAIN, minimum);
		}
		return font;
	}

}