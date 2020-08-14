package me.dylanmullen.spring.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user_info")
public class UserBean
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public String uuid;

	private String username;
	private int wins;
	private double credits;

	@Transient
	private boolean online = false;
	
	public String getUUID()
	{
		return uuid;
	}

	public String getUsername()
	{
		return username;
	}

	public int getWins()
	{
		return wins;
	}

	public double getCredits()
	{
		return credits;
	}

	public boolean isOnline()
	{
		return online;
	}
	
	public void setOnline(boolean online)
	{
		this.online = online;
	}
	
}
