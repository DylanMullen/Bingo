package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class InformationComponent extends Panel
{

	// TODO remove JLabel and paint it instead.

	private static final long serialVersionUID = 5499959852042006713L;

	private Grid grid;
	private JLabel header;
	private JLabel info;

	/**
	 * Creates an Information row. <br>
	 * This row is headed with 2 columns with the left being the header and the
	 * right being the information that the header parents.
	 * 
	 * @param headerText The header text for the panel.
	 * @param infoText   The information text for the panel.
	 * @param x          X-Position of the panel.
	 * @param y          Y-Position of the panel.
	 * @param width      The width of the panel.
	 * @param height     The height of the panel.
	 */
	public InformationComponent(String headerText, String infoText, int x, int y, int width, int height)
	{
		super(x, y, width, height);

		this.header = new JLabel(headerText);
		this.info = new JLabel(infoText);
	}

	@Override
	public void setup()
	{
		setOpaque(false);
		this.grid = new Grid(new GridSettings(width, height, 1, 2, 0), 0, 0);
		getGrid().addGridItem(new GridItem(getHeader(), 1, 1), 0);
		getGrid().addGridItem(new GridItem(getInformation(), 1, 1), 0);

		Font headerF = new Font("Calibri", Font.PLAIN, 20);
		Font font = new Font("Calibri", Font.PLAIN, 16);

		getHeader().setFont(headerF);
		getInformation().setFont(font);

		getHeader().setOpaque(true);
		getInformation().setOpaque(true);

		getHeader().setHorizontalAlignment(SwingConstants.CENTER);
		getHeader().setVerticalAlignment(SwingConstants.CENTER);
		getInformation().setHorizontalAlignment(SwingConstants.CENTER);
		getInformation().setVerticalAlignment(SwingConstants.CENTER);

	}

	@Override
	public void create()
	{
		setup();
		add(getHeader());
		add(getInformation());
	}

	/**
	 * Returns the Grid component which houses the labels.
	 * 
	 * @return {@link #grid}
	 */
	private Grid getGrid()
	{
		return this.grid;
	}

	/**
	 * Returns the JLabel Header which houses the header text for the row.
	 * 
	 * @return {@link #header}
	 */
	public JLabel getHeader()
	{
		return this.header;
	}

	/**
	 * Returns the JLabel Information which houses the information text for the row.
	 * 
	 * @return {@link #info}
	 */
	public JLabel getInformation()
	{
		return this.info;
	}

}
