package me.dylanmullen.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.dylanmullen.spring.beans.UserBean;
import me.dylanmullen.spring.services.UserServices;

@RestController
@RequestMapping(value = "/users")
public class UserRESTController
{

	@Autowired
	private UserServices service;

	@RequestMapping("")
	public List<UserBean> getUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page)
	{
		return service.getUsers(page);
	}

	@RequestMapping("/{uuid}")
	public UserBean getUser(@PathVariable("uuid") String uuid)
	{
		UserBean bean = service.getUser(uuid);
		return bean;
	}

}
