package me.dylanmullen.bingo.window.ui;

import java.awt.Color;

public enum UIColour
{

	/*
	 * Prefixes: BTN PANEL FRAME
	 */
	FRAME_BINGO_BG(0x633974), FRAME_BINGO_BG_SIDE(0x4A235A), FRAME_BINGO_BG_TOP(0x512E5F), //
	BTN_BINGO_SIDE_HOVER(0x5B2C6F), BTN_BINGO_TEXT(0x5DADE2), BTN_BINGO_ACTIVE(0x3498DB), BTN_FAILURE(0x3F1D4D),
	CARD_SELECTED(0xD4AC0D), CARD_PURCHASED(0x2980B90), CARD_DEFAULT(0x2980B9), SQUARE_MARKED(0x8E44AD),
	BINGO_BALL_0(0xEF5350, 0), BINGO_BALL_1(0x7E57C2, 1), BINGO_BALL_2(0x42A5F5, 2), BINGO_BALL_3(0x66BB6A, 3),
	BINGO_BALL_4(0xFFEE58, 4), BINGO_BALL_5(0xFFA726, 5), BINGO_BALL_6(0xFF7043, 6), BINGO_BALL_7(0x8D6E63, 7),
	BINGO_BALL_8(0x78909C, 8), STATUS_CONNECTED(0x2ECC71), STATUS_DISCONNECTED(0xE74C3C), STATUS_UNDEFINED(0xBDC3C7),
	BTN_LOGIN(0x1ABC9C), BTN_REGISTER(0x3498DB);

	private Color colour;
	private Color textColour;
	private int hexcode;
	private int index = -1;

	private UIColour(int hex)
	{
		this.hexcode = hex;
		this.colour = new Color(hex);
		setupTextColour();
	}

	private UIColour(int hex, int ball)
	{
		this.hexcode = hex;
		this.colour = new Color(hex);
		this.index = ball;
		setupTextColour();
	}

	/*
	 * Based on
	 * https://stackoverflow.com/questions/1855884/determine-font-color-based-on-
	 * background-color
	 */
	public void setupTextColour()
	{
		double luminance = (0.299 * colour.getRed() + 0.587 * colour.getGreen() + 0.114 * colour.getBlue()) / 255;
		if (luminance > 0.5)
			textColour = Color.BLACK;
		else
			textColour = Color.WHITE;
	}

	public int getHexcode()
	{
		return hexcode;
	}

	public Color toColor()
	{
		return colour;
	}

	private int getIndex()
	{
		return index;
	}
	
	public Color getTextColour()
	{
		return textColour;
	}

	public static UIColour getBingoBallColour(int num)
	{
		int index = (num == 90 ? (num / 10) - 1 : num / 10);

		for (UIColour colour : values())
		{
			if (colour.getIndex() != -1)
			{
				if (colour.getIndex() == index)
					return colour;
			}
		}
		return null;
	}

}
