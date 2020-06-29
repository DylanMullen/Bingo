package me.dylanmullen.bingo.window.login.panels;

import me.dylanmullen.bingo.core.BingoApp;
import me.dylanmullen.bingo.gfx.components.login.WarningInfoComponent;
import me.dylanmullen.bingo.gfx.ui.grid.Grid;
import me.dylanmullen.bingo.gfx.ui.grid.GridItem;
import me.dylanmullen.bingo.gfx.ui.grid.GridSettings;
import me.dylanmullen.bingo.gfx.ui.panel.UIPanel;

/**
 * @author Dylan
 * @date 19 Jun 2020
 * @project Bingo Client
 */
public class LoginPanel extends UIPanel
{

	private static final long serialVersionUID = 4140252095370157896L;

	private Grid grid;

	private WarningInfoComponent warningInfoComponent;
	private LoginInformationPanel loginInfoComponent;

	private int indent;

	public LoginPanel(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.indent = (width / 100) * 5;
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		setLayout(null);
		setBackground(BingoApp.getInstance().getColourManager().getSet("frame").getColour("content").toColour());

		grid = new Grid(new GridSettings(width, height, 3, 1, 10), 0, 0);

		warningInfoComponent = new WarningInfoComponent();
		grid.addGridItem(new GridItem(warningInfoComponent, 1, 1), 0, true);

		loginInfoComponent = new LoginInformationPanel(this, indent, warningInfoComponent.getHeight() + (indent),
				getWidth() - (indent * 2), (height / 6 * 4) - (indent));
		grid.addGridItem(new GridItem(loginInfoComponent, 2, 1), 1, true);

		loginInfoComponent.create();
		warningInfoComponent.create();
	}

	@Override
	public void create()
	{
		add(warningInfoComponent);
		add(loginInfoComponent);
	}

	public LoginInformationPanel getLoginInfoComponent()
	{
		return loginInfoComponent;
	}

	public WarningInfoComponent getWarningInfoComponent()
	{
		return warningInfoComponent;
	}

}
