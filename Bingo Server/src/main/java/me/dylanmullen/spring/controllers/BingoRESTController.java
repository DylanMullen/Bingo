package me.dylanmullen.spring.controllers;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dylanmullen.spring.services.DropletService;
import me.dylanmullen.spring.services.GameService;

@RestController
@RequestMapping("/games")
public class BingoRESTController
{

	@Autowired
	private GameService service;

	@Autowired
	private DropletService dropletService;

	@RequestMapping()
	public JSONObject getGames()
	{
		return service.getAllGames();
	}

	@RequestMapping("/{name}")
	public JSONObject getGame(@PathVariable("name") String cloudName)
	{
		return service.getCloudInformation(cloudName);
	}

	@RequestMapping("/{name}/droplets")
	public JSONObject getDroplets(@PathVariable("name") String bingoName)
	{
		return dropletService.getDropletsFromCloud(bingoName);
	}

}
