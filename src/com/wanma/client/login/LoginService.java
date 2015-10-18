package com.wanma.client.login;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.UserOdn;

@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService{
	public boolean checkLogin(String username,String password);
	public UserOdn getUser(String username);
	public String test(String name);
}
