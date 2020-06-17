package me.dylanmullen.bingo.window.ui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RoundedButton extends UIButton
{

	private static final long serialVersionUID = 2732058454408689154L;

	private JLabel text;
	private Font font;
	private UIColour colour, hover;
	private boolean hovered;

	public RoundedButton(String text, Font font, int x, int y, int width, int height, UIColour colour)
	{
		super(text, x, y, width, height);
		this.colour = colour;
		this.font = font;
	}

	public RoundedButton(String text, Font font, UIColour colour)
	{
		super(text);
		this.colour = colour;
		this.font = font;
	}

	@Override
	protected void setup()
	{
		setOpaque(false);
		text = new JLabel();
		text.setText(getText());
		text.setForeground(colour.getTextColour());
		text.setBounds(0, 0, getWidth(), getHeight());
		text.setHorizontalAlignment(SwingConstants.CENTER);
		if (font != null)
			text.setFont(font);

		hover = UIColour.STATUS_CONNECTED;

		addMouseListener(new MouseAdapter()
		{

			@Override
			public void mouseExited(MouseEvent e)
			{
				hovered = false;
				repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				hovered = true;
				repaint();
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				setActive(true);
				repaint();
			}
		});
		addFocusListener(new FocusListener()
		{

			@Override
			public void focusLost(FocusEvent e)
			{
				hovered = false;
				repaint();
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				hovered = true;
				repaint();
			}
		});

	}

	public void updateBounds()
	{
		text.setBounds(0, 0, getWidth(), getHeight());
	}
	
	@Override
	public UIButton create()
	{
		setup();
		add(text);
		return this;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (hovered && hover != null)
			g2.setColor(hover.toColor());
		else
			g2.setColor(colour.toColor());

		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		super.paintComponent(g);
	}

}
