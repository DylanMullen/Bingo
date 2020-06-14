package me.dylanmullen.bingo.core;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class Main
{

	public static void main(String[] args)
	{
		new BingoServer(4585);
	}

}
