package me.dylanmullen.spring.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.dylanmullen.bingo.game.user.UserInformation;
import me.dylanmullen.bingo.game.user.UserManager;

@RestController
@RequestMapping(value = "/users")
public class UserRESTController
{

	@RequestMapping
	public List<UserInformation> getActiveUsers()
	{
		return UserManager.getInstance().getActiveUsersInformation();
	}

	@RequestMapping(value = "/{uuid}")
	public UserInformation getActiveUser(@PathVariable("uuid") UUID uuid)
	{
		return UserManager.getInstance().getUser(uuid).getUserInformation();
	}

}
