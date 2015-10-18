package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.ExecuteOrder;
import com.wanma.domain.JobOrder;

@RemoteServiceRelativePath("businessWorkListService")
public interface BusinessWorkListService extends RemoteService {

	public List<ExecuteOrder> getAllExecuteOrderByJobOrder(String jobOrderCode);
	
	public List<JobOrder> getAllBusinessWorkList();

	public Integer addBusinessWorkList(JobOrder jobOrder);
	
	public String addExecuteOrder(ExecuteOrder executeOrder);
	
	public boolean assignExecuteOrder(String routeTaskCode,String groupName, String userName,String jobOrderCode);
	
	public void updateJobOrderStatus(String jobOrderCode,int status);

	public boolean verifyExecuteOrder(String aType,String aCode,String aPortCode,String zType,String zCode,String zPortCode);
}
