package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.UserOdn;

@RemoteServiceRelativePath("userService")
public interface UserService extends RemoteService {

	public String addUser(UserOdn user);
	
	public List<UserOdn> getAllUsers();
	
	public boolean updateUserPassword(String userName,String password);
	
	public UserOdn getUserByUserName(String username);
	
	public boolean deleteUserByUserName(String username);
	
	public String print();
}
