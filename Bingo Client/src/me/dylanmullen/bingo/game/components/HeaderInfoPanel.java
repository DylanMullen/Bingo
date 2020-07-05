package me.dylanmullen.bingo.game.components;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;
import me.dylanmullen.bingo.window.bingo.panels.sidemenu.InformationComponent;

public class HeaderInfoPanel extends UIPanel
{

	private static final long serialVersionUID = -5089245731477923082L;

	private Grid grid;
	private InformationComponent calls;
	private InformationComponent lineState;
	private InformationComponent prize;

	public HeaderInfoPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setup();
	}

	@Override
	public void setup()
	{
		setLayout(null);
		setOpaque(false);
		this.grid = new Grid(new GridSettings(getWidth() - 10, getHeight() - 10, 3, 1, 2), 5, 5);
		UIColour colour = BingoApp.getInstance().getColourManager().getSet("frame").getColour("content");
		this.calls = new InformationComponent("Calls", 0 + "", colour);
		this.lineState = new InformationComponent("Lines", "One Line", colour);
		this.prize = new InformationComponent("Prize Money", "$1000", colour);
		grid.addGridItem(new GridItem(lineState, 1, 1), 0, false);
		grid.addGridItem(new GridItem(calls, 1, 1), 1, false);
		grid.addGridItem(new GridItem(prize, 1, 1), 2, true);
		grid.updatePositions();
		add(calls);
		add(lineState);
		add(prize);
	}

	public void setText(String text)
	{
		repaint();
	}

	@Override
	public void create()
	{
	}

}
