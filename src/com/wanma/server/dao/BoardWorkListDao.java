package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.BoardTask;
import com.wanma.domain.dto.BoardTaskDTO;

public interface BoardWorkListDao {

	public int addBoardTask(BoardTask boardTask);

	public boolean assignBoardTask(String workListCode, String groupName,
			String userName);

	public List<BoardTask> getAllBoardWorkList(String deviceType);
	
	/**
	 * web service方法
	 */
	public List<BoardTaskDTO> getBoardTaskByStatusWorker(String username,String status);
	
	public boolean updateBoardTaskStatus(String code,String deviceCode,String boardType,String frameNum,String boardNum,String opType,String status,String result);
	
}
