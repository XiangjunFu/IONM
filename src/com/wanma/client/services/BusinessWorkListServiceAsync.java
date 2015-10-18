package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.ExecuteOrder;
import com.wanma.domain.JobOrder;

public interface BusinessWorkListServiceAsync {

	public void getAllExecuteOrderByJobOrder(String jobOrderCode,
			AsyncCallback<List<ExecuteOrder>> allExecuteOrdersCallBack);

	public void getAllBusinessWorkList(AsyncCallback<List<JobOrder>> asyncCallback);

	public void addBusinessWorkList(JobOrder jobOrder,
			AsyncCallback<Integer> addBusinessWorkListCallBack);

	public void addExecuteOrder(ExecuteOrder executeOrder,
			AsyncCallback<String> addExecuteOrderCallback);

	public void assignExecuteOrder(String routeTaskCode,
			String groupName, String userName,String jobOrderCode, AsyncCallback<Boolean> assignTask);

	public void updateJobOrderStatus(String jobOrderCode, int status,AsyncCallback<Void> callback);
	
	public void verifyExecuteOrder(String aType,String aCode,String aPortCode,String zType,String zCode,String zPortCode,AsyncCallback<Boolean> callback);
}
