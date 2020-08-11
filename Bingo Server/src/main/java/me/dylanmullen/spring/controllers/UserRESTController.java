package me.dylanmullen.spring.controllers;

import java.util.UUID;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserRESTController
{

	@RequestMapping("/")
	public JSONObject getUsers()
	{
		return null;
	}
	
	@RequestMapping("/{uuid}")
	public JSONObject getUser(@PathVariable("uuid") UUID uuid)
	{
		return null;
	}

}
