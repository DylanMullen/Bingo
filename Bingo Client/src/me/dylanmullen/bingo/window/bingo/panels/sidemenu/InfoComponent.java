package me.dylanmullen.bingo.window.bingo.panels.sidemenu;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import me.dylanmullen.bingo.util.FontUtil;
import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.grid.Grid;
import me.dylanmullen.bingo.window.ui.grid.GridItem;
import me.dylanmullen.bingo.window.ui.grid.GridSettings;

public class InfoComponent extends Panel
{

	private static final long serialVersionUID = 5499959852042006713L;

	private Grid grid;
	private JLabel header;
	private JLabel info;

	public InfoComponent(String header, String info, int x, int y, int width, int height)
	{
		super(x, y, width, height);
		this.header = new JLabel(header);
		this.info = new JLabel(info);
	}

	@Override
	public void setup()
	{
		setOpaque(false);
		grid = new Grid(new GridSettings(width, height, 1, 2, 0), 0, 0);
		grid.addGridItem(new GridItem(header, 1, 1), 0);
		grid.addGridItem(new GridItem(info, 1, 1), 0);
		grid.updateItems();
		
		Font headerF = new Font("Calibri", Font.PLAIN, 20);
		Font font = new Font("Calibri", Font.PLAIN, 16);
		
		header.setFont(headerF);
		info.setFont(font);
		
		header.setOpaque(true);
		info.setOpaque(true);
		
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setVerticalAlignment(SwingConstants.CENTER);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setVerticalAlignment(SwingConstants.CENTER);
		
		create();
	}

	public JLabel getInfo()
	{
		return info;
	}
	
	@Override
	public void create()
	{
		add(info);
		add(header);
	}

}
