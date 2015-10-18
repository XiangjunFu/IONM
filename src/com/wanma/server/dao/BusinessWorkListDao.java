package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.ExecuteOrder;
import com.wanma.domain.JobOrder;
import com.wanma.domain.dto.RouteWorkList;

public interface BusinessWorkListDao {

	public List<ExecuteOrder> getAllExecuteOrderByJobOrder(String jobOrderCode);
	
	/**
	 * 获取业务工单，根据设备类型
	 */
	public abstract List<JobOrder> getAllBusinessWorkList();

	public Integer addBusinessWorkList(JobOrder jobOrder);

	public String addExecuteOrder(ExecuteOrder executeOrder);

	public boolean assignExecuteOrder(String routeTaskCode,
			String groupName, String userName);

	public boolean updateBusinessWorkListStatus(String jobOrderCode,int status);

	public boolean getExecuteOrderByAZ(String aType, String aCode,
			String aPortCode, String zType, String zCode, String zPortCode);

	/**
	 * 用于远程webservice调用，获取路由工单
	 * @param worker
	 * @return
	 */
	public List<RouteWorkList> getExecuteOrderByDevice(String worker,String deviceCode,String status);
	
	public List<RouteWorkList> getExecuteOrderByStatus(String worker,String status);
	
	public boolean updateRouteWorkListStatus(String code, String deviceCode,
			String aBoardSeq, String aPortSeq, String zBoardSeq,
			String zPortSeq, String opType, String status, String result);
	
	public boolean onlyUpdateRouteWorkListStatus(String deviceCode,
			String aBoardSeq, String aPortSeq, String zBoardSeq,
			String zPortSeq, String opType, String status, String result);
}
