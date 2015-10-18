package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.BoardWorkListService;
import com.wanma.domain.BoardTask;
import com.wanma.server.dao.BoardWorkListDao;
import com.wanma.server.dao.impl.BoardWorkListDaoImpl;

public class BoardWorkListServiceImpl extends RemoteServiceServlet implements BoardWorkListService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1660401038789235562L;

	private BoardWorkListDao boardWorkListDao = null; 
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		boardWorkListDao = new BoardWorkListDaoImpl();
	}

	@Override
	public List<BoardTask> getAllBoardWorkList(String deviceType) {
		return boardWorkListDao.getAllBoardWorkList(deviceType);
	}

	@Override
	public int addBoardTask(BoardTask boardTask) {
		HttpSession session = this.perThreadRequest.get().getSession();
		String loginName = (String)session.getAttribute("loginName");
		boardTask.setCreator(loginName);
		String code = boardTask.getDeviceCode() + "/" + boardTask.getBoardNum() + "/" + boardTask.getOpType();
		boardTask.setCode(code);
		return boardWorkListDao.addBoardTask(boardTask);
	}

	@Override
	public boolean assignBoardTask(String workListCode, String groupName,
			String userName) {
		
		return boardWorkListDao.assignBoardTask(workListCode,groupName,userName);
	}

}
