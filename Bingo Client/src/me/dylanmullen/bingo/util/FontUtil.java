package me.dylanmullen.bingo.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class FontUtil
{

	/**
	 * This calculates the dimensions of a String based on the font being passed
	 * through.
	 * 
	 * @param fontMetrics The font metrics being used.
	 * @param text        The text to be used to calucate the dimensions.
	 * @param LR          Left/Right padding.
	 * @param TB          Top/Bottom padding.
	 * @return Returns the {@link Dimension} of the text using that font.
	 */
	public static Dimension getFontSize(FontMetrics fontMetrics, String text, int LR, int TB)
	{
		int hgt = fontMetrics.getAscent() + fontMetrics.getDescent() + fontMetrics.getLeading();
		int adv = fontMetrics.stringWidth(text);
		Dimension size = new Dimension(adv + LR, hgt + TB);
		return size;
	}

	/**
	 * Deprecated Method. <br>
	 * This method is intensive and should only be used behind the scenes and not
	 * used on the fly due to the 100 loops that the methods uses.
	 * 
	 * @param component The parent component for the text to fight inside
	 * @param text      The text to find the optimal size for.
	 * @param paddingLR The Left/Right padding.
	 * @param paddingTB The Top/Botom padding.
	 * @return Returns the optimal {@link Font} to use to fit in that container;
	 */
	@Deprecated
	public static Font getFont(Component component, String text, int paddingLR, int paddingTB)
	{
		Font font = new Font("Calibri", Font.PLAIN, 0);
		for (int i = 0; i < 100; i++)
		{
			Font current = new Font(font.getFontName(), font.getStyle(), i);
			Dimension dim = getFontSize(component.getFontMetrics(current), text, paddingLR, paddingTB);
			if (component.getWidth() < dim.width || component.getHeight() < dim.height)
			{
				return current;
			}
			font = current;
		}
		return font;
	}

	/**
	 * Deprecated Method. <br>
	 * This method is intensive and should only be used behind the scenes and not
	 * used on the fly due to the 100 loops that the methods uses.
	 * 
	 * @param component The parent component for the text to fight inside
	 * @param text      The text to find the optimal size for.
	 * @param paddingLR The Left/Right padding.
	 * @param paddingTB The Top/Botom padding.
	 * @param minimum   The minimum size for the font.
	 * @return Returns the optimal {@link Font} to use to fit in that container;
	 */
	@Deprecated
	public static Font getFont(Component component, String text, int paddingLR, int paddingTB, int minimum)
	{
		Font font = new Font("Calibri", Font.PLAIN, minimum);
		for (int i = minimum; i < 100; i++)
		{
			Font current = new Font(font.getFontName(), font.getStyle(), i);
			Dimension dim = getFontSize(component.getFontMetrics(current), text, paddingLR, paddingTB);
			if (component.getWidth() < dim.width || component.getHeight() < dim.height)
			{
				font = current;
				break;
			}
			font = current;
		}
		return font;
	}

}
