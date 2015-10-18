package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.RouteCheckTask;

public interface CheckWorkListServiceAsync {

	public void addRouteCheckTask(RouteCheckTask checkWorkList,AsyncCallback<String> callback);

	public void getAllCheckWorkList(String deviceType,
			AsyncCallback<List<RouteCheckTask>> allCheckworkListCallback);

	public void assignCheckTask(String workListCode, String groupName,
			String userName, AsyncCallback<Boolean> assignTask);
	
}
