package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.BoardTask;

public interface BoardWorkListServiceAsync {

	public void getAllBoardWorkList(String deviceType,
			AsyncCallback<List<BoardTask>> asyncCallback);

	public void addBoardTask(BoardTask boardTask,
			AsyncCallback<Integer> asyncCallback);

	public void assignBoardTask(String workListCode, String groupName,
			String userName, AsyncCallback<Boolean> assignTask);

}
