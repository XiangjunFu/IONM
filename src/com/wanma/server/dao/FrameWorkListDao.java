package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.FrameTask;
import com.wanma.domain.dto.FrameTaskDTO;

public interface FrameWorkListDao {

	public Integer addFrameTask(FrameTask frameTask);

	public List<FrameTask> getAllFrameWorkList(String deviceType);

	public boolean assignFrameTask(String workListCode, String groupName,
			String userName);
	/**
	 * ============web service=======
	 */
	
	public List<FrameTaskDTO> getFrameTaskByStatusWorker(String username,String status);
	
	public boolean updateFrameTaskStatusByCode(String code,String deviceCode,String frameNum,String opType,String status,String result);
}
