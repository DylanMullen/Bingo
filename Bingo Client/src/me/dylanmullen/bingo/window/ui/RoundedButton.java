package me.dylanmullen.bingo.window.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.util.FontUtil;

public class RoundedButton extends UIButton
{

	private static final long serialVersionUID = 2732058454408689154L;

	private JLabel text;

	public RoundedButton(String text, int x, int y, int width, int height)
	{
		super(text, x, y, width, height);
	}
	public RoundedButton(String text)
	{
		super(text);
	}

	@Override
	protected void setup()
	{
		init();
		setOpaque(false);
		text = new JLabel();
		text.setText(getText());
		text.setBounds(0, 0, getWidth(), getHeight());
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setFont(FontUtil.getFont(text, getText(), 0, 0));
	}

	@Override
	public UIButton create()
	{
		setup();
		add(text);
		System.out.println("BUttons");
		return this;
	}
	
	public void setBackground(UIColour colour)
	{
		setForeground(colour.getTextColour());
		super.setBackground(colour.toColor());
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		super.paintComponent(g);
	}

}
