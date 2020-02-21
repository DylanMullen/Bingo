package me.dylanmullen.bingo.window.bingo.ui.buttons;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;

import me.dylanmullen.bingo.window.bingo.ui.listeners.SP_BTN_Listener;
import me.dylanmullen.bingo.window.ui.UIButton;

public class ButtonContainer extends JComponent
{

	private static final long serialVersionUID = -5821559173698810258L;

	private ArrayList<UIButton> buttons = new ArrayList<>();
	private int BTN_HEIGHT = 0;

	public ButtonContainer(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
	}

	public void addButton(UIButton button)
	{
		buttons.add(button);
	}

	public void remove(UIButton button)
	{
		if (!buttons.contains(button))
			return;
		buttons.add(button);
		remove(button);
	}

	public void populate()
	{
		int indent = 0;
		for (UIButton btn : buttons)
		{
			btn.setWidth(getWidth());
			btn.setHeight(BTN_HEIGHT);
			btn.setX(getX());
			btn.setY(indent);
			btn.create();
			btn.addMouseListener(new SP_BTN_Listener());

			indent += BTN_HEIGHT;
			add(btn);
		}
	}

	public ButtonContainer setButtonHeight(int x)
	{
		this.BTN_HEIGHT = x;
		return this;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
