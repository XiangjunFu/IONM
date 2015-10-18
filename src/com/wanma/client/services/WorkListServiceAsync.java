package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.ConfigTask;

public interface WorkListServiceAsync {

	public void getAllConfigWorkList(String deviceType,
			AsyncCallback<List<ConfigTask>> asyncCallback);

	public void addConfigTask(ConfigTask configTask,
			AsyncCallback<String> asyncCallback);

	public void assignConfigTask(String workListCode,String groupName, String userName,
			AsyncCallback<Boolean> asyncCallback);

}
