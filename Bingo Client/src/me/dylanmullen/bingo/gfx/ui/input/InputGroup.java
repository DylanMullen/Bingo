package me.dylanmullen.bingo.gfx.ui.input;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

public class InputGroup extends JComponent
{

	private static final long serialVersionUID = -7082528826587911143L;

	private String spanTag;
	private InputField input;
	private UIColour colour;
	private Polygon spanShape;

	public InputGroup(String spanTag, Vector2I position, Vector2I dimensions, UIColour colour, boolean hideText)
	{
		this.spanTag = spanTag;
		this.colour = colour;
		setBackground(colour.darken(0.15).toColour());
		setForeground(colour.getTextColour());
	}

	public void setupShapes()
	{
		setFont(new Font("Calibri", Font.PLAIN, 18));
		Dimension textDimension = FontUtil.getFontSize(getFontMetrics(getFont()), spanTag, 0, 0);
		spanShape = new Polygon();
		spanShape.addPoint(0, 0);
		spanShape.addPoint(10 + textDimension.width + 10, 0);
		spanShape.addPoint(10 + textDimension.width, getHeight());
		spanShape.addPoint(0, getHeight());

		Polygon inputShape = new Polygon();
		inputShape.addPoint(10, 0);
		inputShape.addPoint(getWidth(), 0);
		inputShape.addPoint(getWidth(), getHeight());
		inputShape.addPoint(0, getHeight());
		input = new InputField(inputShape, new Vector2I(textDimension.width + 10, 0),
				new Vector2I(getWidth() - textDimension.width + 20, getHeight()), true);
		input.setBackground(colour.toColour());
		input.setForeground(getForeground());
		input.setCaretColor(getForeground());
		input.setFont(new Font(getFont().getFamily(), Font.PLAIN, getFont().getSize()));
		add(input);
		repaint();
	}

	public String getText()
	{
		return input.getText();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillPolygon(spanShape);

		drawSpan(g2);
	}

	private void drawSpan(Graphics2D g2)
	{
		Dimension textDimension = FontUtil.getFontSize(getFontMetrics(getFont()), spanTag, 0, 0);
		g2.setColor(getForeground());
		g2.drawString(spanTag, 5, getHeight() / 2 + textDimension.height / 4);
	}

}
