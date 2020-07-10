package me.dylanmullen.bingo.gfx.ui.buttons;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import me.dylanmullen.bingo.util.Vector2I;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class ButtonContainer extends JComponent
{

	private static final long serialVersionUID = -5821559173698810258L;

	private List<UIButton> buttons = new ArrayList<>();
	private int BTN_HEIGHT = 0;

	/**
	 * Creates a container to contain buttons inside of.
	 * 
	 * @param x      X-Position of the container.
	 * @param y      Y-Position of the container.
	 * @param width  The width of the container.
	 * @param height The height of the container.
	 */
	public ButtonContainer(int x, int y, int width, int height)
	{
		setBounds(x, y, width, height);
	}

	/**
	 * Adds a button to the container.<br>
	 * Will not be added to the container if the button already exists in the
	 * container.
	 * 
	 * @param button Button to add to the container.
	 */
	public void addButton(UIButton button)
	{
		if (getButtons().contains(button))
			return;
		getButtons().add(button);
	}

	/**
	 * Removes a button from the container.<br>
	 * Cannot remove a button if it does not exist within the container.
	 * 
	 * @param button Button to remove from the container.
	 */
	public void remove(UIButton button)
	{
		if (!getButtons().contains(button))
			return;
		getButtons().add(button);
		remove(button);
	}

	/**
	 * Populates the button container with the buttons.<br>
	 * This will auto adjust the height and width of the buttons as well as the x
	 * and y positions of the buttons.
	 */
	public void populate()
	{
		int indent = 0;
		for (UIButton btn : getButtons())
		{
			btn.updateBounds(new Vector2I(getX(), indent), new Vector2I(getWidth(), this.BTN_HEIGHT));
			if (btn instanceof SidePanelButton)
			{
				System.out.println("seu");
				((SidePanelButton) btn).setup();
			}
			indent += this.BTN_HEIGHT;
			add(btn);
		}
	}

	/**
	 * Sets the button height for each of the buttons.
	 * 
	 * @param height The new height value.
	 * @return Returns the button container.
	 */
	public ButtonContainer setButtonHeight(int height)
	{
		this.BTN_HEIGHT = height;
		return this;
	}

	/**
	 * Returns the buttons inside of the container
	 * 
	 * @return {@link #buttons}
	 */
	public List<UIButton> getButtons()
	{
		return this.buttons;
	}
}
