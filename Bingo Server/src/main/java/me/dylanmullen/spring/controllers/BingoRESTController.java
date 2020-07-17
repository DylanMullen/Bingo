package me.dylanmullen.spring.controllers;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dylanmullen.bingo.core.BingoServer;

@RestController
@RequestMapping("/bingo")
public class BingoRESTController
{

	@RequestMapping
	public List<JSONObject> getBingoClouds()
	{
		return BingoServer.getInstance().getGame().getClouds();
	}

	@RequestMapping("/{cloudUUID}")
	public JSONObject getCloud(@PathVariable("cloudUUID") UUID uuid)
	{
		return BingoServer.getInstance().getGame().getBingoCloud(uuid).getCloudInformation();
	}

	@RequestMapping(value = "/{cloudUUID}/create-droplet")
	public JSONObject createDroplet(@PathVariable("cloudUUID") UUID cloudUUID)
	{
		BingoServer.getInstance().getGame().getBingoCloud(cloudUUID).createDroplet();
		return getCloud(cloudUUID);
	}

	@RequestMapping("/{cloudUUID}/droplet/{dropletUUID}")
	public JSONObject getDroplet(@PathVariable("cloudUUID") UUID cloudUUID,
			@PathVariable("dropletUUID") UUID dropletUUID)
	{
		return BingoServer.getInstance().getGame().getBingoCloud(cloudUUID).getDroplet(dropletUUID).toJSON();
	}
}
