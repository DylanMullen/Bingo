package me.dylanmullen.bingo.window.bingo.panels.content;

import java.util.HashSet;

import me.dylanmullen.bingo.window.ui.Panel;
import me.dylanmullen.bingo.window.ui.UIColour;

public class MainContent extends Panel
{

	private static final long serialVersionUID = 670976601982278116L;

	private HashSet<Panel> panels;

	public MainContent(int x, int y, int width, int height)
	{
		super(x, y, width, height);
	}

	@Override
	public void setup()
	{
		setBounds(x, y, width, height);
		System.out.println(getX()+":"+getY());
		setBackground(UIColour.FRAME_BINGO_BG.toColor());
		setLayout(null);
		panels = new HashSet<Panel>();
	}

	@Override
	public void create()
	{
		for(Panel p : getPanels())
		{
			p.setup();
			p.create();
			add(p);
		}
	}

	public HashSet<Panel> getPanels()
	{
		return panels;
	}
	
	public Panel getPanel(String name)
	{
		for (Panel p : panels)
		{
			if (p.getName() == null)
				continue;
			if (p.getName().equalsIgnoreCase(name))
				return p;
		}
		return null;
	}

}
