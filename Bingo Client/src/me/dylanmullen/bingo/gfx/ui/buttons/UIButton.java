package me.dylanmullen.bingo.gfx.ui.buttons;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import me.dylanmullen.bingo.util.FontUtil;

public abstract class UIButton extends JComponent
{

	private static final long serialVersionUID = 3666739796846121934L;

	private String text;
	private ButtonInformation information;

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
						setBackground(information.getHoverColour().getColour());
				}
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				if (hovered)
				{
					hovered = false;
					if (information.getMainColour() != null)
						setBackground(information.getMainColour().getColour());
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (System.currentTimeMillis() - lastClick >= 500)
				{
					getInformation().getListener().invoke();
					lastClick = System.currentTimeMillis();
					if (focused)
					{
						focused = false;
						if (information.getMainColour() != null)
							setBackground(information.getMainColour().getColour());
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
						setBackground(information.getHoverColour().getColour());
				}
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				if (focused)
				{
					focused = false;
					if (information.getMainColour() != null)
						setBackground(information.getMainColour().getColour());
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
								setBackground(information.getMainColour().getColour());
						}
					}
				}
			}
		});
	}

	public void updateBounds()
	{
		setBounds(getInformation().getX(), getInformation().getY(), getInformation().getWidth(),
				getInformation().getHeight());
	}

	protected void drawText(Graphics2D g2)
	{
		Dimension textDim = FontUtil.getFontSize(getFontMetrics(getInformation().getFont()), text, 0, 0);
		g2.setFont(getInformation().getFont());
		switch (information.getTextPosition())
		{
			case CENTER:
				g2.drawString(text, getWidth() / 2 - (textDim.width / 2), getHeight() / 2 - (textDim.height / 4));
				break;
			case LEFT:
				g2.drawString(text, 0, getHeight() / 2 - (textDim.height / 4));
				break;
			case RIGHT:
				g2.drawString(text, getWidth() - textDim.width, getHeight() / 2 - (textDim.height / 4));
				break;
		}
	}

	public String getText()
	{
		return text;
	}

	public ButtonInformation getInformation()
	{
		return information;
	}
}
