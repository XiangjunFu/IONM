package com.test;

import com.wanma.client.login.LoginService;
import com.wanma.server.login.LoginServiceImpl;

public class TestLogin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LoginService login = new LoginServiceImpl();
		boolean check = login.checkLogin("test", "test");
		System.out.println(check);
	}

}
