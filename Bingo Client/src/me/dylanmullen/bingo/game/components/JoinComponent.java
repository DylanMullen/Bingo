package me.dylanmullen.bingo.game.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.game.components.buttons.JoinButton;
import me.dylanmullen.bingo.window.ui.RoundedPanel;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

public class JoinComponent extends RoundedPanel
{

	private static final long serialVersionUID = -9169345629205701763L;

	private Grid grid;

	private JLabel text;
	private JoinButton button;

	public JoinComponent(int x, int y, int width, int height, int arc)
	{
		super(x, y, width, height, arc);
		int indent = 15;
		this.grid = new Grid(new GridSettings(width-(indent*2), height-(indent*2), 3, 2, 5), indent, indent);
	}

	@Override
	public void setup()
	{
		setBackground(UIColour.FRAME_BINGO_BG_TOP.toColor());
		this.text = new JLabel();
		grid.addGridItem(new GridItem(text, 2, 2), 0);
		text.setOpaque(true);
		text.setText("You need to join a game!");
		text.setFont(new Font("Calbiri",Font.PLAIN,32));
		text.setForeground(Color.WHITE);
		text.setBackground(UIColour.BTN_BINGO_ACTIVE.toColor());
		text.setHorizontalAlignment(SwingConstants.CENTER);

		button = new JoinButton("Join Game!");
		grid.addGridItem(new GridItem(button, 1, 2), 2);

//		grid.updateItems();

		button.create();
	}

	@Override
	public void create()
	{
		setup();
		add(text);
		add(button);
	}

}
