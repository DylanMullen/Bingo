package me.dylanmullen.bingo.configs;

import java.io.File;

public class Config
{

	private String name;
	private File file;

	public Config(String name, File file)
	{
		this.name = name;
		this.file = file;
	}

	public File getFile()
	{
		return file;
	}

	public String getName()
	{
		return name;
	}

}
