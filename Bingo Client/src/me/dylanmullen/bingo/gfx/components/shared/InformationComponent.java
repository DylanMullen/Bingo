package me.dylanmullen.bingo.gfx.components.shared;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JComponent;

import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class InformationComponent extends JComponent
{


	private static final long serialVersionUID = 5499959852042006713L;

	private String header;
	private String info;

	private Dimension textDim;
	private Color textColour;
	private Polygon headerShape;
	private Polygon bodyShape;

	/**
	 * Creates an Information row. <br>
	 * This row is headed with 2 columns with the left being the header and the
	 * right being the information that the header parents.
	 * 
	 * @param headerText The header text for the panel.
	 * @param infoText   The information text for the panel.
	 * @param x          X-Position of the panel.
	 * @param y          Y-Position of the panel.
	 * @param width      The width of the panel.
	 * @param height     The height of the panel.
	 */
	public InformationComponent(String headerText, String infoText, Vector2I pos, Vector2I dim, UIColour colour)
	{
		setBounds(pos.getX(), pos.getY(), dim.getX(), dim.getY());
		this.header = headerText;
		this.info = infoText;
		this.textColour = colour.getTextColour();
		setBackground(colour.darken(0.15).toColour());
		setForeground(colour.toColour());
		setFont(new Font("Calibri", Font.PLAIN, 15));
		repaint();
	}

	public InformationComponent(String headerText, String infoText, UIColour colour)
	{
		setBounds(0, 0, 0, 0);
		this.header = headerText;
		this.info = infoText;
		this.textColour = colour.getTextColour();
		setBackground(colour.darken(0.15).toColour());
		setForeground(colour.toColour());
		setFont(new Font("Calibri", Font.PLAIN, 15));
		repaint();
	}

	private void setup()
	{
		textDim = FontUtil.getFontSize(getFontMetrics(getFont()), header, 0, 0);

		headerShape = new Polygon();
		headerShape.addPoint(0, 0);
		headerShape.addPoint(10 + textDim.width + (10), 0);
		headerShape.addPoint(10 + textDim.width, getHeight());
		headerShape.addPoint(0, getHeight());

		bodyShape = new Polygon();
		bodyShape.addPoint(headerShape.xpoints[1], 0);
		bodyShape.addPoint(getWidth(), 0);
		bodyShape.addPoint(getWidth(), getHeight());
		bodyShape.addPoint(headerShape.xpoints[2], getHeight());
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		setup();
		drawBody(g2);
		drawHeader(g2);
	}

	private void drawHeader(Graphics2D g2)
	{
		g2.setColor(getBackground());
		g2.fillPolygon(headerShape);
		g2.setColor(textColour);
		g2.drawString(header, 5, getHeight() / 2 + (textDim.height / 4));
	}

	private void drawBody(Graphics2D g2)
	{
		g2.setColor(getForeground());
		g2.fill(bodyShape);
		g2.setColor(textColour);
		g2.drawString(info, headerShape.xpoints[1] + 5, getHeight() / 2 + textDim.height / 4);
	}

	public void setText(String text)
	{
		this.info = text;
		repaint();
	}

}
