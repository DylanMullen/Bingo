package me.dylanmullen.bingo.window.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImageComponent extends JComponent
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7944118873866264777L;

	private BufferedImage image;

	public ImageComponent(int x, int y, int w, int h)
	{
		setup(x, y, w, h);
	}

	private void setup(int x, int y, int w, int h)
	{
		setBounds(x, y, w, h);
	}
	
	public void setImage(BufferedImage image)
	{
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

}
