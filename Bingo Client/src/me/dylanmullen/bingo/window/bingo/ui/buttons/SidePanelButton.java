package me.dylanmullen.bingo.window.bingo.ui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.gfx.ImageAtlas;
import me.dylanmullen.bingo.window.ui.ImageComponent;
import me.dylanmullen.bingo.window.ui.UIButton;
import me.dylanmullen.bingo.window.ui.UIColour;

public class SidePanelButton extends UIButton
{

	private static final long serialVersionUID = -1495099977168142089L;

	private final ImageAtlas ATLAS = new ImageAtlas("uiAtlas.png", 42);

	private JPanel selected;
	private ImageComponent icon;
	private ImageComponent chevron;

	private JLabel textLabel;
	private Dimension dimension;

	public SidePanelButton(Dimension dim, String text, int x, int y, int w, int h)
	{
		super(text, x, y, w, h);
		this.dimension = dim;
	}

	public SidePanelButton(String text, Dimension dim)
	{
		super(text);
		this.dimension = dim;
	}

	@Override
	protected void setup()
	{
		init();
		setBackground((isActive() ? UIColour.BTN_BINGO_SIDE_HOVER : UIColour.FRAME_BINGO_BG_SIDE).toColor());
		int indentX = (getWidth() / 20);
		int indentY = 10;

		selected = new JPanel();

		selected.setBounds(0, 0, indentX, getHeight());

		indentX += 10;
		icon = new ImageComponent(indentX, indentY, getMaxHeight(getHeight(), indentY),
				getMaxHeight(getHeight(), indentY));
		icon.setImage(ATLAS.getImage(dimension.width, dimension.height, UIColour.BTN_BINGO_ACTIVE.toColor()));

		indentX += icon.getWidth() + 4;
		textLabel = new JLabel(getText());
		textLabel.setBounds(indentX, indentY, ((getWidth() - indentX) - 12) - (42 + 4),
				getMaxHeight(getHeight(), indentY));

		textLabel.setFont(new Font("Calibri", Font.PLAIN, 40));
		textLabel.setVerticalAlignment(SwingConstants.TOP);

		indentX += textLabel.getWidth() + 4;
		chevron = new ImageComponent(indentX, indentY, getMaxHeight(getHeight(), indentY),
				getMaxHeight(getHeight(), indentY));
		chevron.setImage(ATLAS.getImage(0, 0, UIColour.BTN_BINGO_ACTIVE.toColor()));
	}

	@Override
	public SidePanelButton create()
	{
		setup();
		selected.setBackground(UIColour.BTN_BINGO_ACTIVE.toColor());
		selected.setVisible(isActive());

		textLabel.setForeground(UIColour.BTN_BINGO_TEXT.toColor());

		add(selected);
		add(icon);
		add(textLabel);
		add(chevron);

		return this;
	}

	private int getMaxHeight(int height, int indent)
	{
		int x = height - (indent * 2) + 2;
		return x;
	}

	public UIButton setActive()
	{
		super.setActive(!isActive());
		selected.setVisible(isActive());
		setBackground((isActive() ? UIColour.BTN_BINGO_SIDE_HOVER : UIColour.FRAME_BINGO_BG_SIDE).toColor());
		return this;
	}

}
