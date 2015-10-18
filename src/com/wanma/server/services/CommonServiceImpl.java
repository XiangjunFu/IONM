package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.CommonService;
import com.wanma.domain.AlarmLog;
import com.wanma.server.dao.CommonDao;
import com.wanma.server.dao.impl.CommonDaoImpl;

public class CommonServiceImpl extends RemoteServiceServlet implements
		CommonService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8301565972741841617L;

	private CommonDao commonDao = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		commonDao = new CommonDaoImpl();
	}

	@Override
	public int addAlarmLogInfo(AlarmLog alarmLog) {
		return commonDao.addAlarmLogInfo(alarmLog);
	}

	@Override
	public List<AlarmLog> getAllAlarmLogInfos() {
		return commonDao.getAllAlarmLogInfos();
	}

	@Override
	public List<AlarmLog> getAlarmLogInfoByDeviceType(String deviceType) {
		return commonDao.getAlarmLogInfosByDeviceType(deviceType);
	}
	
}
