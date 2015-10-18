package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.WorkListService;
import com.wanma.domain.ConfigTask;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.dao.WorkListDao;
import com.wanma.server.dao.impl.OpticalDeviceDaoImpl;
import com.wanma.server.dao.impl.WorkListDaoImpl;

public class WorkListServiceImpl extends RemoteServiceServlet implements
		WorkListService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5674041302939636343L;

	private WorkListDao workListDao;
	
	private OpticalDeviceDao opticalDeviceDao = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		workListDao = new WorkListDaoImpl();
		opticalDeviceDao = new OpticalDeviceDaoImpl();
	}

	/**
	 * 根据设备类型，获取配置工单
	 */
	@Override
	public List<ConfigTask> getAllConfigWorkList(String deviceType) {
		return workListDao.getAllConfigWorkList(deviceType);
	}

	@Override
	public String addConfigTask(ConfigTask configTask) {
		String deviceCode = configTask.getDeviceCode();
		DeviceCommonInfo device = opticalDeviceDao.getDeviceCommonInfoByDeviceCode(deviceCode);
		
		HttpSession session = this.perThreadRequest.get().getSession();
		configTask.setCreator((String)session.getAttribute("loginName"));
		configTask.setStation(device.getStationName());
		configTask.setHostroom(device.getHostRoomName());
		
		return workListDao.addConfigTask(configTask);
	}

	@Override
	public boolean assignConfigTask(String workListCode,String groupName, String userName) {
		return workListDao.assignConfigTask(workListCode, groupName, userName);
	}
	
	@Test
	public void test(){
		ConfigTask config = new ConfigTask();
		config.setCode("fff");
		new WorkListDaoImpl().addConfigTask(config);
	}
}
