package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageButton extends Button
{

	private static final long serialVersionUID = 8764676062442125881L;

	public enum ImageButtonType
	{
		FULLSIZE, CENTER
	}

	private BufferedImage image;
	private ImageButtonType type;

	public ImageButton(BufferedImage image, ButtonInformation information)
	{
		super("", information);
		this.image = image;
		this.type = ImageButtonType.FULLSIZE;
	}

	public ImageButton(BufferedImage image, ImageButtonType type, ButtonInformation information)
	{
		this(image, information);
		this.type = type;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		if (type.equals(ImageButtonType.FULLSIZE))
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		else
		{
			super.paintComponent(g);
			g.drawImage(image, getWidth() / 2 - (getHeight() - 10) / 2, 5, getHeight() - 10, getHeight() - 10, null);
		}
	}

}
