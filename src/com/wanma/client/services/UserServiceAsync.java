package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.UserOdn;

public interface UserServiceAsync {
	public void addUser(UserOdn user, AsyncCallback<String> callback);

	public void getAllUsers(AsyncCallback<List<UserOdn>> asyncCallback);
	
	public void updateUserPassword(String userName,String password,AsyncCallback<Boolean> callback);

	public void getUserByUserName(String username, AsyncCallback<UserOdn> callback);

	public void deleteUserByUserName(String username,AsyncCallback<Boolean> callback);
	
	public void print(AsyncCallback<String> callback);
}
