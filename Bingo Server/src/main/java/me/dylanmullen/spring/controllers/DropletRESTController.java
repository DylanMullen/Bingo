package me.dylanmullen.spring.controllers;

import java.util.List;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dylanmullen.spring.services.DropletService;

@RestController
@RequestMapping("/droplet")
public class DropletRESTController
{
	
	@Autowired
	private DropletService dropletService;

	@RequestMapping()
	public List<JSONObject> getDroplets()
	{
		return dropletService.getAllDroplets();
	}
	
	@RequestMapping("/{uuid}")
	public JSONObject getDroplet(@PathVariable("uuid") UUID dropletUUID)
	{
		return dropletService.getDropletFromUUID(dropletUUID);
	}
	
}
