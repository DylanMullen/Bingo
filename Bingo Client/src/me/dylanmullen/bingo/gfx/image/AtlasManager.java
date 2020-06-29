package me.dylanmullen.bingo.gfx.image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import me.dylanmullen.bingo.configs.IOController;

public class AtlasManager
{

	private final String FILE_EXTENSION = ".png";

	private List<ImageAtlas> atlasList;

	public AtlasManager()
	{
		this.atlasList = new ArrayList<>();
	}

	public ImageAtlas loadAtlast(String name, int textureSize)
	{
		ImageAtlas atlas = new ImageAtlas(name, "images" + IOController.SEPERATOR + name + FILE_EXTENSION, textureSize);
		if (!atlas.isLoaded())
			return null;
		atlasList.add(atlas);
		return atlas;
	}

	public List<ImageAtlas> getAtlasList()
	{
		return atlasList;
	}

	public ImageAtlas getAtlas(String name, int textureSize)
	{
		ImageAtlas atlas;
		try
		{
			atlas = atlasList.stream().findFirst().filter(e -> e.getName().equalsIgnoreCase(name)).get();
		} catch (NoSuchElementException e)
		{
			atlas = loadAtlast(name, textureSize);
		}
		return atlas;
	}
}
