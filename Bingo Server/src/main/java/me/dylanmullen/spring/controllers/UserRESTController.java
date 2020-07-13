package me.dylanmullen.spring.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRESTController
{

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public String viewUser()
	{
		return "Test";
	}

}
