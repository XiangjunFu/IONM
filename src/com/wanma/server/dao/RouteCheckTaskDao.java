package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.RouteCheckTask;

public interface RouteCheckTaskDao {

	public String addRouteCheckTask(RouteCheckTask checkWorkList);

	public List<RouteCheckTask> getAllCheckWorkListByDeviceType(
			String deviceType);
	
	public boolean assignCheckTask(String workListCode, String groupName,
			String userName);
	/**
	 * Web service方法
	 * @param deviceType
	 * @return
	 */
	
	public List<RouteCheckTask> getCheckWorkListByWorker(String username,String status);
	
	public List<RouteCheckTask> getCheckWorkListByDevice(String username,String deviceCode,String status);
	
	public boolean updateCheckWorkListStatus(String code,String oldCode,String newCode,String result,String status);

	public List<RouteCheckTask> getCheckWorkListByDeviceCode(String deviceCode);
	
}
