package me.dylanmullen.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import me.dylanmullen.spring.beans.UserBean;
import me.dylanmullen.spring.repository.UserRepository;

@Service
public class UserServices
{

	@Autowired
	private UserRepository repository;

	public List<UserBean> getUsers(int page)
	{
		return repository.findAll(new PageRequest(page, 10)).getContent();
	}

	public UserBean getUser(String uuid)
	{
		return repository.findByUuid(uuid);
	}

}
