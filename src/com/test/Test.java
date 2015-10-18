package com.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.wanma.client.login.LoginService;
import com.wanma.client.login.LoginServiceAsync;


public class Test {

	public static void main(String[] args) {
		//testConn();
		testtt();	
	}

	private static void testConn() {
		//jdbc:sqlserver://localhost:1433;databaseName=wanmaodn
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=wanmaodn","sa","sa");
			if(conn !=null){
				System.out.println("...");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void testtt(){
		LoginServiceAsync loginService = (LoginServiceAsync)GWT.create(LoginService.class);
		ServiceDefTarget login = (ServiceDefTarget)loginService;
		login.setServiceEntryPoint(GWT.getModuleBaseURL()+"/loginService");
		loginService.checkLogin("test", "test", new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				System.out.println(result.booleanValue());
			}
			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail...");
			}
		});
	}
}
