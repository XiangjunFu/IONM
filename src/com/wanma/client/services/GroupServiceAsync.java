package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.UserOdn;
import com.wanma.domain.WorkGroup;
import com.wanma.domain.WorkGroupId;

public interface GroupServiceAsync {

	/**
	 * 添加工作组
	 * @param group
	 * @param callback
	 */
	public void addGroup(WorkGroup group,AsyncCallback<WorkGroupId> callback);
	
	/**
	 * 获取所有工作组名称（Id）
	 * @param callback
	 */
	public void getWorkGroupIds(AsyncCallback<List<WorkGroup>> callback);
	
	/**
	 * 获取某一施工组成员
	 */
	public void getUsersByGroup(String groupName,AsyncCallback<List<UserOdn>> callback);

	/**
	 * 删除施工组
	 * @param name
	 * @param deleteGroup
	 */
	public void deleteGroup(String name, AsyncCallback<Boolean> deleteGroup);
}
