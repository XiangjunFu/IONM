package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.FrameWorkListService;
import com.wanma.domain.FrameTask;
import com.wanma.server.dao.FrameWorkListDao;
import com.wanma.server.dao.impl.FrameWorkListDaoImpl;

public class FrameWorkListServiceImpl extends RemoteServiceServlet implements
		FrameWorkListService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2072033760842764307L;

	
	private FrameWorkListDao frameWorkListDao = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		frameWorkListDao = new FrameWorkListDaoImpl();
	}

	@Override
	public List<FrameTask> getAllFrameWorkList(String deviceType) {
		return frameWorkListDao.getAllFrameWorkList(deviceType);
	}

	@Override
	public Integer addFrameTask(FrameTask frameTask) {
		String code = frameTask.getDeviceCode() + "/" + frameTask.getFrameNum() + "/" + frameTask.getOpType();
		frameTask.setCode(code);
		return frameWorkListDao.addFrameTask(frameTask);
	}

	@Override
	public boolean assignFrameTask(String workListCode, String groupName,
			String userName) {
		return frameWorkListDao.assignFrameTask(workListCode,groupName,userName);
	}
}
