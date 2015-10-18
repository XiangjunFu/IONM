package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.FrameTask;

public interface FrameWorkListServiceAsync {

	public void getAllFrameWorkList(String deviceType,
			AsyncCallback<List<FrameTask>> asyncCallback);

	public void addFrameTask(FrameTask frameTask,
			AsyncCallback<Integer> asyncCallback);

	public void assignFrameTask(String workListCode, String groupName,
			String userName, AsyncCallback<Boolean> assignTask);

}
