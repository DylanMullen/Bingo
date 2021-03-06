package me.dylanmullen.bingo.gfx.ui.input;

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

import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.colour.UIColourSet;
import me.dylanmullen.bingo.util.Vector2I;

public class UIPasswordField extends JPasswordField
{

	private static final long serialVersionUID = 8395208345260502315L;

	private String placeholder;
	private UIColourSet set;

	private boolean hovered;
	private boolean focused;

	public UIPasswordField(String placeholder, Vector2I pos, Vector2I dimensions, UIColourSet set)
	{
		this(placeholder, set);
		setBounds(pos.getX(), pos.getY(), dimensions.getX(), dimensions.getY());
	}

	public UIPasswordField(String placeHolder, UIColourSet set)
	{
		this.placeholder = placeHolder;
		this.set = set;
		setup();
	}

	public void resize(Vector2I pos, Vector2I dimensions)
	{
		setBounds(pos.getX(), pos.getY(), dimensions.getX(), dimensions.getY());
	}

	private void setup()
	{
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder(5, 10, 8, 10));

		setFont(new Font("Calibri", Font.PLAIN, 25));
		setText(placeholder);
		setHorizontalAlignment(SwingConstants.CENTER);

		updateBackground(set.getColour("primary"));
		setForeground(set.getColour("text-colour").toColour());
		setCaretColor(set.getColour("text-colour").toColour());
		setEchoChar((char) 0);

		addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				focused = false;
				updateBackground(set.getColour("primary"));
				if (getPassword().length == 0)
				{
					setText(placeholder);
					setEchoChar((char) 0);
				} else
					setEchoChar('*');

				setForeground(set.getColour("text-colour").toColour());
				repaint();
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
				updateBackground(set.getColour("hovered"));
				setCaretPosition(getPassword().length);
				repaint();
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
					updateBackground(set.getColour("primary"));
					setForeground(set.getColour("text-colour").toColour());
					repaint();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				hovered = true;
				if (!focused)
				{
					updateBackground(set.getColour("hovered"));
					repaint();
				}
			}
		});
	}

	private void updateBackground(UIColour colour)
	{
		setBackground(colour.toColour());
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

		g2.setColor(set.getColour("border-bottom").toColour());
		g2.fillRoundRect(0, getHeight() - 8, getWidth(), 8, 15, 15);

		super.paintComponent(g);
	}

	public boolean isPlaceHolder()
	{
		return new String(getPassword()).equals(placeholder);
	}

	public boolean isHovered()
	{
		return hovered;
	}

}
