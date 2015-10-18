package com.wanma.server.services;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.TerminalWeldJumpService;
import com.wanma.domain.JumperConnector;
import com.wanma.domain.TerminalUnit;
import com.wanma.domain.WeldConnector;
import com.wanma.server.dao.TerminalWeldJumpDao;
import com.wanma.server.dao.impl.TerminalWeldJumpDaoImpl;
import com.wanma.server.utils.HibernateUtil;

public class TerminalWeldJumpServiceImpl extends RemoteServiceServlet implements
		TerminalWeldJumpService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2141837097173228864L;

	private TerminalWeldJumpDao terminalWeldJumpDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		terminalWeldJumpDao = new TerminalWeldJumpDaoImpl();
	}

	@Override
	public Integer addJumpConnector(JumperConnector jump) {
		return terminalWeldJumpDao.addJumpConnector(jump);
	}

	@Override
	public Integer addWeldConnector(WeldConnector weld) {
		return terminalWeldJumpDao.addWeldConnector(weld);
	}

	@Override
	public String addNewModule(TerminalUnit terminalUnit) {
		return terminalWeldJumpDao.addNewModule(terminalUnit);
	}

	@Override
	public int updatePortInfo(String portCode,String serviceStatus) {
		return terminalWeldJumpDao.updatePortInfo(portCode, serviceStatus);
	}
	
}
