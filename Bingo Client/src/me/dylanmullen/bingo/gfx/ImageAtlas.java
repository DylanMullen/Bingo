package me.dylanmullen.bingo.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageAtlas
{

	private String path;
	private int size;

	private BufferedImage image;

	public ImageAtlas(String path, int size)
	{
		this.path = path;
		this.size = size;
		load();
	}

	private void load()
	{
		try
		{
			image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(path));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public BufferedImage getImage(int x, int y)
	{
		return image.getSubimage(x * size, y * size, size, size);
	}

	public BufferedImage getImage(int x, int y, Color col)
	{
		BufferedImage img = image.getSubimage(x * size, y * size, size, size);
		BufferedImage temp = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = temp.createGraphics();
		g2.drawImage(img, 0, 0, size, size, null);
		g2.dispose();

		int[] initialBuf = ((DataBufferInt) temp.getRaster().getDataBuffer()).getData();

		for (int i = 0; i < initialBuf.length; i++)
		{
			initialBuf[i] = replaceColour(initialBuf[i], col.getRGB());
		}

		return temp;

	}

	private int replaceColour(int col, int toReplace)
	{
		if (col == Color.BLACK.getRGB())
		{
			return toReplace;
		}
		return col;
	}

}
