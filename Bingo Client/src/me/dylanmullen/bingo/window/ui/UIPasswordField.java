package me.dylanmullen.bingo.window.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

public class UIPasswordField extends JPasswordField
{

	private static final long serialVersionUID = 8395208345260502315L;

	private String placeholder;
	private boolean hovered;
	private boolean focused;

	public UIPasswordField(String placeholder, int[] properties)
	{
		this(placeholder);
		setBounds(properties[0], properties[1], properties[2], properties[3]);
		setup();
	}

	public UIPasswordField(String placeHolder)
	{
		this.placeholder = placeHolder;
		setup();
	}

	public void resize(int[] properties)
	{
		setBounds(properties[0], properties[1], properties[2], properties[3]);
	}

	private void setup()
	{
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(5, 10, 8, 10));

		setFont(new Font("Calibri", Font.PLAIN, 25));
		setText(placeholder);
		setHorizontalAlignment(SwingConstants.CENTER);

		updateBackground(UIColour.FRAME_BINGO_BG);
		setForeground(Color.LIGHT_GRAY);
		setEchoChar((char) 0);

		addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				focused = false;
				updateBackground(UIColour.FRAME_BINGO_BG);
				if (getPassword().length == 0)
				{
					setText(placeholder);
					setEchoChar((char) 0);
				} else
					setEchoChar('*');

				setForeground(Color.LIGHT_GRAY);
			}

			@Override
			public void focusGained(FocusEvent e)
			{
				focused = true;
				if (new String(getPassword()).equals(placeholder))
				{
					setText("");
				}
				setEchoChar((char) 0);
				updateBackground(UIColour.FRAME_BINGO_BG_TOP);
				setCaretPosition(getPassword().length);
			}
		});

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseExited(MouseEvent e)
			{
				hovered = false;
				if (!focused)
				{
					updateBackground(UIColour.FRAME_BINGO_BG);
					setForeground(Color.LIGHT_GRAY);
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				hovered = true;
				if (!focused)
				{
					updateBackground(UIColour.FRAME_BINGO_BG_TOP);
					repaint();
				}
			}
		});
	}

	private void updateBackground(UIColour colour)
	{
		setForeground(colour.getTextColour());
		setBackground(colour.toColor());
		setCaretColor(colour.getTextColour());
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		g2.setColor(UIColour.FRAME_BINGO_BG_SIDE.toColor());
		g2.fillRoundRect(0, getHeight() - 8, getWidth(), 8, 15, 15);

		super.paintComponent(g);
	}

	public boolean isPlaceHolder()
	{
		return new String(getPassword()).equals(placeholder);
	}

}