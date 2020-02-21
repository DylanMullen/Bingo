package me.dylanmullen.bingo.window.ui;

import java.awt.Color;

public enum UIColour
{

	/*
	 * Prefixes: BTN PANEL FRAME
	 */
	FRAME_BINGO_BG(0x633974), FRAME_BINGO_BG_SIDE(0x4A235A), FRAME_BINGO_BG_TOP(0x512E5F), //
	BTN_BINGO_SIDE_HOVER(0x5B2C6F), BTN_BINGO_TEXT(0x5DADE2), BTN_BINGO_ACTIVE(0x3498DB),
	BTN_FAILURE(0x3F1D4D);

	private Color colour;
	private int hexcode;

	private UIColour(int hex)
	{
		this.hexcode = hex;
		this.colour = 	new Color(hex);
	}

	public int getHexcode()
	{
		return hexcode;
	}

	public Color toColor()
	{
		return colour;
	}

}
