package me.dylanmullen.spring.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import me.dylanmullen.spring.beans.UserBean;

public interface UserRepository extends PagingAndSortingRepository<UserBean, String>
{

	public UserBean findByUuid(String uuid);

}
