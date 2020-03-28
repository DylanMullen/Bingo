package me.dylanmullen.bingo.window.login.panels;

import me.dylanmullen.bingo.window.login.comp.LoginInfoComponent;
import me.dylanmullen.bingo.window.login.comp.ServerInfoComponent;
import me.dylanmullen.bingo.window.login.comp.WarningInfoComponent;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

public class LoginPanel extends Panel
{

	private static final long serialVersionUID = 4140252095370157896L;

	private Grid grid;

	private WarningInfoComponent warningInfoComponent;
	private LoginInfoComponent loginInfoComponent;

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
		setBackground(UIColour.FRAME_BINGO_BG.toColor());

		grid = new Grid(new GridSettings(width, height, 3, 1, 10), 0, 0);

		warningInfoComponent = new WarningInfoComponent();
		grid.addGridItem(new GridItem(warningInfoComponent, 1, 1), 0);

		loginInfoComponent = new LoginInfoComponent(indent, warningInfoComponent.getHeight() + (indent),
				getWidth() - (indent * 2), (height / 6 * 4) - (indent));
		grid.addGridItem(new GridItem(loginInfoComponent, 2, 1), 1);
		
		grid.updateItems();
		loginInfoComponent.create();
		warningInfoComponent.create();
	}

	@Override
	public void create()
	{
		setup();
		add(warningInfoComponent);
		add(loginInfoComponent);
	}

}
