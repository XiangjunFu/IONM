package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.BoardTask;

@RemoteServiceRelativePath("boardWorkListService")
public interface BoardWorkListService extends RemoteService {

	public List<BoardTask> getAllBoardWorkList(String deviceType);
	
	public int addBoardTask(BoardTask boardTask);
	
	public boolean assignBoardTask(String workListCode, String groupName,String userName);
}
