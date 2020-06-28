package me.dylanmullen.bingo.game.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class InfoPanel extends UIPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5089245731477923082L;
	private JLabel text;

	public InfoPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setOpaque(false);
		this.text = new JLabel();
		text.setBounds(0, 0, width, height);
		text.setHorizontalAlignment(SwingConstants.CENTER);

		Font font = new Font("Calibri", Font.PLAIN, 25);
		text.setFont(font);
		text.setForeground(Color.WHITE);
	}

	public void setText(String text)
	{
		this.text.setText(text);
		repaint();
	}

	@Override
	public void create()
	{
		add(text);
	}

}
