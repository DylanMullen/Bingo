package me.dylanmullen.bingo.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
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

	private String path;
	private int size;

	private BufferedImage image;

	/**
	 * Creates an Image Atlas based on an image passed in by the path and the size
	 * of each square in the atlas.
	 * 
	 * @param path The path of the image.
	 * @param size The size of each square in the atlas.
	 */
	public ImageAtlas(String path, int size)
	{
		this.path = path;
		this.size = size;
		load();
	}

	/**
	 * Loads the image based on the path provided.<br>
	 * This will throw an IOException if the path cannot provide an image. This will
	 * make the image null.
	 */
	private void load()
	{
		try
		{
			this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
		} catch (IOException e)
		{
			e.printStackTrace();
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
	public BufferedImage getImage(int x, int y, Color colour)
	{
		BufferedImage img = getSubImage(x, y);
		BufferedImage temp = new BufferedImage(this.size, this.size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = temp.createGraphics();
		g2.drawImage(img, 0, 0, this.size, this.size, null);
		g2.dispose();

		int[] initialBuf = ((DataBufferInt) temp.getRaster().getDataBuffer()).getData();

		for (int i = 0; i < initialBuf.length; i++)
		{
			initialBuf[i] = replaceColour(initialBuf[i], colour.getRGB());
		}
		return temp;
	}

	/**
	 * Replaces a colour with another colour.
	 * 
	 * @param colour    The current colour of the pixel.
	 * @param toReplace The colour to replace with.
	 * @return Returns the RGB colour value of toReplace if the colour equals black.
	 */
	private int replaceColour(int colour, int toReplace)
	{
		// TODO make the colour be able to change.
		if (colour == Color.BLACK.getRGB())
		{
			return toReplace;
		}
		return colour;
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

}
