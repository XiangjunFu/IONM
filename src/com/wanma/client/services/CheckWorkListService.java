package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.RouteCheckTask;

@RemoteServiceRelativePath("checkWorkListService")
public interface CheckWorkListService extends RemoteService {

	public String addRouteCheckTask(RouteCheckTask checkWorkList);
	
	public List<RouteCheckTask> getAllCheckWorkList(String deviceType);
	
	public boolean assignCheckTask(String workListCode, String groupName,
			String userName);
	
}
