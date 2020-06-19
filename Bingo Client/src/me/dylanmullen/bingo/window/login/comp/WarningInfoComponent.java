package me.dylanmullen.bingo.window.login.comp;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.RoundedPanel;
import me.dylanmullen.bingo.window.ui.UIColour;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class WarningInfoComponent extends RoundedPanel
{

	private static final long serialVersionUID = 5423352332179650770L;

	// TODO convert to TextArea.
	private JLabel informationLabel;

	/**
	 * Creates a Warning Information rounded panel which displays information to the
	 * Player.
	 * 
	 * @param x      X-Position of the Component.
	 * @param y      Y-Position of the Component.
	 * @param width  The width of the Component.
	 * @param height The height of the Component.
	 */
	public WarningInfoComponent(int x, int y, int width, int height)
	{
		super(x, y, width, height, 15);
	}

	/**
	 * Creates a Warning Information rounded panel which displays information to the
	 * Player. <br>
	 * This uses default values to construct a dynamically sized component.
	 * <ul>
	 * <li>{@link #getX()}=0</li>
	 * <li>{@link #getY()}=0</li>
	 * <li>{@link #getWidth()}=0</li>
	 * <li>{@link #getHeight()}=0</li>
	 * </ul>
	 */
	public WarningInfoComponent()
	{
		this(0, 0, 0, 0);
	}

	@Override
	public void setup()
	{
		updateBackground(UIColour.STATUS_UNDEFINED);

		this.informationLabel = new JLabel();
		getInformationLabel().setBounds(0, 0, getWidth(), getHeight());
		updateText("Please Login/Register");
		getInformationLabel().setHorizontalAlignment(SwingConstants.CENTER);
		getInformationLabel().setVerticalTextPosition(SwingConstants.CENTER);
		getInformationLabel().setForeground(getForeground());
	}

	@Override
	public void create()
	{
		setup();
		add(getInformationLabel());
	}

	/**
	 * Updates the text within the component. <br>
	 * This method will take the text and convert the text to HTML code to be able
	 * to display multiple lines in the JLabel.
	 * 
	 * <h1>Deprecated</h1> <br>
	 * This method contains a method which is intensive on searching for a Font
	 * size.
	 * 
	 * @param text The text to update the label.
	 */
	@SuppressWarnings("deprecation")
	public void updateText(String text)
	{
		if (text.contains("\n"))
		{
			StringBuilder sb = new StringBuilder();
			String[] split = text.split("\n");
			sb.append("<html><center>");
			for (int i = 0; i < split.length; i++)
				sb.append(split[i] + (split.length - 1 == i ? "" : "<br>"));
			sb.append("</center></html>");
			text = sb.toString();
		}

		getInformationLabel().setText(text);
		getInformationLabel().setFont(FontUtil.getFont(getInformationLabel(), text, 30, 30, 25));
	}

	/**
	 * Updates the background colour of the Component.
	 * 
	 * @param colour The colour to change to.
	 */
	public void updateBackground(UIColour colour)
	{
		setBackground(colour.toColor());
		setForeground(colour.getTextColour());
	}

	/**
	 * Returns the JLabel for the information of this component.
	 * 
	 * @return {@link #informationLabel}
	 */
	public JLabel getInformationLabel()
	{
		return this.informationLabel;
	}
}
