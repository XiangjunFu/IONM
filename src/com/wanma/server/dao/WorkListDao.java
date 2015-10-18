package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.ConfigTask;
import com.wanma.domain.dto.ConfigTaskDTO;

public interface WorkListDao {

	/**
	 * 根据设备类型，获取配置工单
	 */
	public abstract List<ConfigTask> getAllConfigWorkList(String deviceType);

	public abstract String addConfigTask(ConfigTask configTask);

	public abstract boolean assignConfigTask(String workListCode,
			String groupName, String userName);
	
	/**
	 * 用于远程webservice调用，按照施工人员获取配置工单
	 * @param worker
	 * @return
	 */
	public List<ConfigTaskDTO> getConfigWorkListByDevice(String worker,String deviceCode,String status);
	
	public List<ConfigTaskDTO> getConfigWorkListByStatus(String worker,String status);
	
	public boolean updateConfigTaskStatusByCode(String code,String status);
	
	public boolean updateConfigTaskStatus(String code,String oldCode,String newCode,String result, String status);
	
	public boolean updateDeviceCode(String preCode,String newCode);

}