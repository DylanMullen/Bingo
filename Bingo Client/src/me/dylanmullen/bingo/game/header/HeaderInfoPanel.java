package me.dylanmullen.bingo.game.header;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.game.droplet.LineState;
import me.dylanmullen.bingo.gfx.components.shared.InformationComponent;
import me.dylanmullen.bingo.gfx.ui.colour.UIColour;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

public class HeaderInfoPanel extends UIPanel
{

	private static final long serialVersionUID = -5089245731477923082L;

	private Grid grid;
	private int calls;
	private InformationComponent callsInformation;
	private InformationComponent lineStateInformation;
	private InformationComponent prizeInformation;
	private InformationComponent playersInformation;

	public HeaderInfoPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		setup();
	}

	@Override
	public void setup()
	{
		setLayout(null);
		setOpaque(true);
		this.grid = new Grid(new GridSettings(getWidth() - 10, getHeight() - 10, 3, 2, 2), 5, 5);
		UIColour colour = BingoApp.getInstance().getColourManager().getSet("frame").getColour("header-information");
		this.callsInformation = new InformationComponent("Calls", 0 + "", colour);
		this.lineStateInformation = new InformationComponent("Lines", "One Line", colour);
		this.prizeInformation = new InformationComponent("Prize Money", "$1000", colour);
		this.playersInformation = new InformationComponent("Players", 10 + "", colour);
	}

	public void updatePrize(double credits)
	{
		prizeInformation.setText(credits + "");
	}

	public void updateLineState(LineState state)
	{
		String line = "";
		switch (state)
		{
			case ONE:
				line = "One Line";
				break;
			case TWO:
				line = "Two Lines";
				break;
			case FULLHOUSE:
				line = "Fullhouse";
				break;
		}
		lineStateInformation.setText(line);
	}

	public void resetCalls()
	{
		this.calls = 0;
	}

	public void updateCalls()
	{
		calls++;
		callsInformation.setText(calls + "");
	}

	public void setText(String text)
	{
		repaint();
	}

	@Override
	public void create()
	{
		grid.addGridItem(new GridItem(lineStateInformation, 1, 2), 0, false);
		grid.addGridItem(new GridItem(prizeInformation, 1, 2), 1, true);
		grid.addGridItem(new GridItem(callsInformation, 1, 1), 2, false);
		grid.addGridItem(new GridItem(playersInformation, 1, 1), 2, false);
		grid.updatePositions();
		add(callsInformation);
		add(lineStateInformation);
		add(prizeInformation);
		add(playersInformation);
	}

}
