package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.FrameTask;

@RemoteServiceRelativePath("frameWorkListService")
public interface FrameWorkListService extends RemoteService {

	
	public List<FrameTask> getAllFrameWorkList(String deviceType);
	
	public Integer addFrameTask(FrameTask frameTask);
	
	public boolean assignFrameTask(String workListCode, String groupName, String userName);
}
