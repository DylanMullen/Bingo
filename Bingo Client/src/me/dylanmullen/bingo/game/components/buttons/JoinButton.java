package me.dylanmullen.bingo.game.components.buttons;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.game.components.listeners.JoinButtonListener;
import me.dylanmullen.bingo.window.ui.UIButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class JoinButton extends UIButton
{

	private JLabel label;

	public JoinButton(String text)
	{
		super(text);
	}

	@Override
	protected void setup()
	{
		init();
		setBackground(UIColour.BTN_BINGO_TEXT.toColor());
		setOpaque(false);
		
		label = new JLabel(getText());
		label.setBounds(12, 12, getWidth() - 24, getHeight() - 24);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Calibri",Font.PLAIN,32));
//		label.setForeground(Color.DARK_GRAY);
		add(label);
		addMouseListener(new JoinButtonListener(this));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(UIColour.BTN_BINGO_TEXT.toColor());

		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
	}

	@Override
	protected void paintBorder(Graphics g)
	{
		super.paintBorder(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
	}

	@Override
	public UIButton create()
	{
		setup();
		return null;
	}

}
