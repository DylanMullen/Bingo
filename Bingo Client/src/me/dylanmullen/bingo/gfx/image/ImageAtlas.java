package me.dylanmullen.bingo.gfx.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class ImageAtlas
{

	private String name;
	private String path;
	private int size;

	private BufferedImage image;
	private boolean loaded;

	/**
	 * Creates an Image Atlas based on an image passed in by the path and the size
	 * of each square in the atlas.
	 * 
	 * @param path The path of the image.
	 * @param size The size of each square in the atlas.
	 */
	public ImageAtlas(String name, String path, int size)
	{
		this.name = name;
		this.path = path;
		this.size = size;
		this.loaded = load();
	}

	private boolean load()
	{
		try
		{
			this.image = ImageIO.read(getClass().getClassLoader().getResource(path));
			return true;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns a new Image based on the co-ordinates of the image in the atlas.
	 * 
	 * @param x X-Position of the sub-image.
	 * @param y Y-Position of the sub-image.
	 * @return Returns a new sub-image.
	 */
	public BufferedImage getSubImage(int x, int y)
	{
		return getImage().getSubimage(x * this.size, y * this.size, this.size, this.size);
	}

	/**
	 * Returns a new Image based on the co-ordinates of the image in the atlas.<br>
	 * The image returned will be recoloured with the colour provided.
	 * 
	 * @param x      X-Position of the sub-image.
	 * @param y      Y-Position of the sub-image.
	 * @param colour The colour to replace with.
	 * @return Returns a new recoloured sub-image.
	 */
	public BufferedImage getImage(int x, int y, Color colour, Color toReplace)
	{
		BufferedImage img = getSubImage(x, y);
		BufferedImage temp = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = temp.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(img, 0, 0, this.size, this.size, null);
		g2.dispose();

		int[] initialBuf = ((DataBufferInt) temp.getRaster().getDataBuffer()).getData();

		for (int i = 0; i < initialBuf.length; i++)
		{
			Color current = new Color(initialBuf[i], true);
			if (isColour(current, colour))
			{
				initialBuf[i] = new Color(toReplace.getRed(), toReplace.getGreen(), toReplace.getBlue(), current.getAlpha()).getRGB();
			}
		}

		return temp;
	}

	private boolean isColour(Color color, Color replace)
	{
		if (color.getRed() != replace.getRed())
			return false;
		else if (color.getGreen() != replace.getGreen())
			return false;
		else if (color.getBlue() != replace.getBlue())
			return false;
		return true;
	}

	/**
	 * Returns the Image Atlas.
	 * 
	 * @return {@link #image}
	 */
	public BufferedImage getImage()
	{
		return this.image;
	}

	public boolean isLoaded()
	{
		return loaded;
	}

	public String getName()
	{
		return name;
	}
}
