package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.ConfigTask;
import com.wanma.domain.JobOrder;

@RemoteServiceRelativePath("workListService")
public interface WorkListService extends RemoteService{

	public List<ConfigTask> getAllConfigWorkList(String deviceType);
	
	public String addConfigTask(ConfigTask configTask);
	
	public boolean assignConfigTask(String workListCode,String groupName,String userName);
}
