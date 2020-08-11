package me.dylanmullen.spring.controllers;

import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/droplet")
public class DropletRESTController
{

	@RequestMapping("/")
	public JSONObject getDroplets()
	{
		return null;
	}
	
	@RequestMapping("/{uuid}")
	public JSONObject getDroplet(@PathVariable("uuid") UUID dropletUUID)
	{
		return null;
	}
	
}
