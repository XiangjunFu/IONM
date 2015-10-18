package com.wanma.server.dao;

import java.util.List;

import org.junit.Test;

import com.wanma.domain.UserOdn;

public interface UserDao {

	public boolean checklogin(String username,String password);
	
	public abstract String addUser(UserOdn user);

	public abstract List<UserOdn> getAllUsers();

	public abstract boolean updateUserPassword(String userName, String password);

	public abstract UserOdn getUserByUserName(String username);

	@Test
	public abstract void testgetUserdv();

	public abstract boolean deleteUserByUserName(String username);

	public List<UserOdn> getUsersByGroup(String name);

}