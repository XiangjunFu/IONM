package com.wanma.server.services;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.CheckWorkListService;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.RouteCheckTask;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.dao.RouteCheckTaskDao;
import com.wanma.server.dao.impl.OpticalDeviceDaoImpl;
import com.wanma.server.dao.impl.RouteCheckTaskDaoImpl;

public class CheckWorkListServiceImpl extends RemoteServiceServlet implements CheckWorkListService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2273641491942930689L;

	private RouteCheckTaskDao routeCheckTaskDao = null;
	
	private OpticalDeviceDao opticalDeviceDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		routeCheckTaskDao = new RouteCheckTaskDaoImpl();
		opticalDeviceDao = new OpticalDeviceDaoImpl();
	}

	@Override
	public String addRouteCheckTask(RouteCheckTask checkWorkList) {
		String deviceCode = checkWorkList.getDeviceCode();
		DeviceCommonInfo device = opticalDeviceDao.getDeviceCommonInfoByDeviceCode(deviceCode);
		
		List<RouteCheckTask> preCheckTasks = routeCheckTaskDao.getCheckWorkListByDeviceCode(deviceCode);
		
		HttpSession session = this.perThreadRequest.get().getSession();
		String loginName = (String)session.getAttribute("loginName");
		
		int num = preCheckTasks.size() + 1;
		
		checkWorkList.setCode(device.getDeviceCode() + "/" + num);
		checkWorkList.setName(device.getDeviceCode() + "/" + num);
		checkWorkList.setDeviceName(device.getDeviceName());
		checkWorkList.setDeviceType(device.getDeviceType());
		checkWorkList.setStation(device.getStationName());
		checkWorkList.setHostroom(device.getHostRoomName());
		checkWorkList.setCreator(loginName);
		checkWorkList.setCreateTime(new Date());
		checkWorkList.setStatus((short)1);//待指派
		
		return routeCheckTaskDao.addRouteCheckTask(checkWorkList);
	}

	@Override
	public List<RouteCheckTask> getAllCheckWorkList(String deviceType) {
		
		return routeCheckTaskDao.getAllCheckWorkListByDeviceType(deviceType);
	}

	@Override
	public boolean assignCheckTask(String workListCode, String groupName,
			String userName) {
		
		return routeCheckTaskDao.assignCheckTask(workListCode,groupName,userName);
	}

}
