package me.dylanmullen.bingo.gfx.ui.colour;

import java.util.ArrayList;
import java.util.List;

public class UIColourSet
{

	private String name;
	private List<UIColour> colours;

	public UIColourSet(String setName)
	{
		this.name = setName;
		this.colours=new ArrayList<>();
	}

	public UIColour getColour(String name)
	{
		for (UIColour colour : getColours())
			if (colour.getColourName().equalsIgnoreCase(name))
				return colour;
		return null;
	}
	
	public void addColour(UIColour colour)
	{
		colours.add(colour);
	}
	
	public boolean hasColour(String name)
	{
		for(UIColour colour : getColours())
			if(colour.getColourName().equalsIgnoreCase(name))
				return true;
		return false;
	}

	public List<UIColour> getColours()
	{
		return colours;
	}

	public String getName()
	{
		return name;
	}

}
