package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.UserOdn;
import com.wanma.domain.WorkGroup;
import com.wanma.domain.WorkGroupId;

@RemoteServiceRelativePath("groupService")
public interface GroupService extends RemoteService {
	
	/**
	 * 添加施工组
	 * @param group
	 * @return
	 */
	public WorkGroupId addGroup(WorkGroup group);
	
	/**
	 * 获取施工组列表
	 * @return
	 */
	public List<WorkGroup> getWorkGroupIds();
	
	/**
	 * 
	 */
	public List<UserOdn> getUsersByGroup(String groupName);
	
	/**
	 * 
	 */
	public boolean deleteGroup(String name);
}