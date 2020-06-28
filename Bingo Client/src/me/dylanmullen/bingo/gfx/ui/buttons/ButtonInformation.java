package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Font;

import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.util.Vector2I;

public class ButtonInformation
{

	public enum TextPosition
	{
		LEFT, CENTER, RIGHT;
	}

	private Vector2I position;
	private Vector2I dimensions;

	private TextPosition textPosition;
	private Font font;

	private UIColour mainColour;
	private UIColour hoverColour;

	private ButtonListener listener;

	public ButtonInformation(Vector2I pos, Vector2I dimensions, ButtonListener listener)
	{
		this.position = pos;
		this.dimensions = dimensions;
		this.mainColour = null;
		this.hoverColour = null;
		this.textPosition = TextPosition.CENTER;
		this.listener = listener;
		this.font = new Font("Calibri", Font.PLAIN, 25);// TODO change this.
	}

	public void setMainColour(UIColour mainColour)
	{
		this.mainColour = mainColour;
	}

	public void setHoverColour(UIColour hoverColour)
	{
		this.hoverColour = hoverColour;
	}

	public void setTextPosition(TextPosition textPosition)
	{
		this.textPosition = textPosition;
	}

	public int getX()
	{
		return getPosition().getX();
	}

	public int getY()
	{
		return getPosition().getY();
	}

	public int getWidth()
	{
		return getDimensions().getX();
	}

	public int getHeight()
	{
		return getDimensions().getY();
	}

	public Vector2I getPosition()
	{
		return position;
	}

	public Vector2I getDimensions()
	{
		return dimensions;
	}

	public UIColour getHoverColour()
	{
		return hoverColour;
	}

	public UIColour getMainColour()
	{
		return mainColour;
	}

	public TextPosition getTextPosition()
	{
		return textPosition;
	}

	public ButtonListener getListener()
	{
		return listener;
	}
	
	public Font getFont()
	{
		return font;
	}
}
