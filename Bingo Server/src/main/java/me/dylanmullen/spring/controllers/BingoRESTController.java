package me.dylanmullen.spring.controllers;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class BingoRESTController
{

	@RequestMapping("/")
	public JSONObject getGames()
	{
		return null;
	}
	
	@RequestMapping("/{name}")
	public JSONObject getGame(@PathVariable("name") String bingoName)
	{
		return null;
	}
	
	@RequestMapping("/{name}/droplets")
	public JSONObject getDroplets(@PathVariable("name") String bingoName)
	{
		return null;
	}
	
}
