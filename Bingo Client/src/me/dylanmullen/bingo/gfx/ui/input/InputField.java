package me.dylanmullen.bingo.gfx.ui.input;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import me.dylanmullen.bingo.util.Vector2I;

public class InputField extends JTextField
{

	private static final long serialVersionUID = -3416880900272999521L;

	private Polygon customShape;
	private boolean hidden;

	public InputField(Polygon shape, Vector2I position, Vector2I dimension, boolean hidden)
	{
		this.customShape = shape;
		this.hidden = hidden;
		setBounds(position.getX(), position.getY(), dimension.getX(), dimension.getY());
		setBorder(new EmptyBorder(5, 15, 5, 40));
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getBackground());
		g2.fillPolygon(customShape);
		super.paintComponent(g2);
	}

}
