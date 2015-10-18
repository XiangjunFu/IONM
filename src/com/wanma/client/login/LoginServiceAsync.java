package com.wanma.client.login;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.UserOdn;

public interface LoginServiceAsync {
	public void checkLogin(String username,String password,AsyncCallback<Boolean> callback);
	public void getUser(String username,AsyncCallback<UserOdn> callback);
	public void test(String name,AsyncCallback<String> callback);
}
