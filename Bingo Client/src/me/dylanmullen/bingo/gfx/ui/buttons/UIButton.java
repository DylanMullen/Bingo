package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;

import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.util.Vector2I;

public abstract class UIButton extends JComponent
{

	private static final long serialVersionUID = 3666739796846121934L;

	private String text;
	private ButtonInformation information;

	private boolean active;
	private long lastClick;

	private boolean hovered;
	private boolean focused;

	public UIButton(String text, ButtonInformation information)
	{
		this.text = text;
		this.information = information;
		init();
	}

	private void init()
	{
		updateBounds();
		addListeners();
		if (getInformation().getMainColour() != null)
			setBackground(getInformation().getMainColour().toColour());

		setFocusable(true);
	}

	private void addListeners()
	{
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseEntered(MouseEvent e)
			{
				if (!hovered)
				{
					hovered = true;
					if (information.getHoverColour() != null)
						setBackground(information.getHoverColour().toColour());
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if (hovered)
				{
					hovered = false;
					if (information.getMainColour() != null)
						setBackground(information.getMainColour().toColour());
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (System.currentTimeMillis() - lastClick >= 1000)
				{
					if (isActive())
						setActive(false);
					else
						setActive(true);
					getInformation().getListener().invoke();
					lastClick = System.currentTimeMillis();
					if (focused)
					{
						focused = false;
						if (information.getMainColour() != null)
							setBackground(information.getMainColour().toColour());
					}
				}
			}
		});
		addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusGained(FocusEvent e)
			{
				if (!focused)
				{
					focused = true;
					if (information.getHoverColour() != null)
						setBackground(information.getHoverColour().toColour());
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (focused)
				{
					focused = false;
					if (information.getMainColour() != null)
						setBackground(information.getMainColour().toColour());
				}
			}
		});
		addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					if (System.currentTimeMillis() - lastClick >= 500)
					{
						getInformation().getListener().invoke();
						lastClick = System.currentTimeMillis();
						if (focused)
						{
							focused = false;
							if (information.getMainColour() != null)
								setBackground(information.getMainColour().toColour());
						}
					}
				}
			}
		});
	}

	public void updateBounds()
	{
		if (getInformation().getDimensions() != null || getInformation().getPosition() != null)
			setBounds(getInformation().getX(), getInformation().getY(), getInformation().getWidth(),
					getInformation().getHeight());
	}

	public void updateBounds(Vector2I pos, Vector2I dim)
	{
		getInformation().updateBounds(pos, dim);
		updateBounds();
	}

	public void updateColours(UIColour main, UIColour hover)
	{
		getInformation().setMainColour(main);
		getInformation().setHoverColour(hover);
		setBackground(getInformation().getMainColour().toColour());
		setForeground(getInformation().getHoverColour().toColour());
		repaint();
	}

	protected void drawText(Graphics2D g2, int x, Color textColor)
	{
		Dimension textDim = FontUtil.getFontSize(getFontMetrics(getInformation().getFont()), text, 0, 0);
		g2.setFont(getInformation().getFont());
		g2.setColor(textColor);
		switch (information.getTextPosition())
		{
			case CENTER:
				g2.drawString(text, getWidth() / 2 - (textDim.width / 2), getHeight() / 2 + (textDim.height / 4));
				break;
			case LEFT:
				g2.drawString(text, x, getHeight() / 2 + (textDim.height / 4));
				break;
			case RIGHT:
				g2.drawString(text, getWidth() - textDim.width, getHeight() / 2 + (textDim.height / 4));
				break;
		}
	}

	@Override
	public void setBounds(int x, int y, int width, int height)
	{
		super.setBounds(x, y, width, height);
		getInformation().updateBounds(new Vector2I(x, y), new Vector2I(width, height));
	}

	public String getText()
	{
		return text;
	}

	public ButtonInformation getInformation()
	{
		return information;
	}

	public boolean isActive()
	{
		return active;
	}

	public boolean isHovered()
	{
		return hovered;
	}

	public void setActive(boolean active)
	{
		this.active = active;
		repaint();
	}
}
