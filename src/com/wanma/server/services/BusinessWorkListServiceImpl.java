package com.wanma.server.services;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.ExecuteOrder;
import com.wanma.domain.JobOrder;
import com.wanma.domain.TerminalUnit;
import com.wanma.server.dao.BusinessWorkListDao;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.dao.impl.BusinessWorkListDaoImpl;
import com.wanma.server.dao.impl.OpticalDeviceDaoImpl;

public class BusinessWorkListServiceImpl extends RemoteServiceServlet implements
		BusinessWorkListService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5979179367900065773L;

	private BusinessWorkListDao businessWorkListDao;
	
	private OpticalDeviceDao opticalDeviceDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		businessWorkListDao = new BusinessWorkListDaoImpl();
		opticalDeviceDao = new OpticalDeviceDaoImpl();
	}

	@Override
	public List<ExecuteOrder> getAllExecuteOrderByJobOrder(String jobOrderCode) {
		return businessWorkListDao.getAllExecuteOrderByJobOrder(jobOrderCode);
	}
	
	/**
	 * 获取业务工单，根据设备类型
	 */
	@Override
	public List<JobOrder> getAllBusinessWorkList() {
		return businessWorkListDao.getAllBusinessWorkList();
	}

	@Override
	public Integer addBusinessWorkList(JobOrder jobOrder) {
		
		return businessWorkListDao.addBusinessWorkList(jobOrder);
	}

	@Override
	public String addExecuteOrder(ExecuteOrder executeOrder) {
		HttpSession session = perThreadRequest.get().getSession();
		
		String aCode = executeOrder.getAmeCode();
		String zCode = executeOrder.getZmeCode();
		
		DeviceCommonInfo aDeviceCommonInfo = opticalDeviceDao.getDeviceCommonInfoByDeviceCode(aCode);
		String aType = aDeviceCommonInfo.getDeviceType();
		executeOrder.setAmeType(aType);
		
		DeviceCommonInfo zDeviceCommonInfo = opticalDeviceDao.getDeviceCommonInfoByDeviceCode(zCode);
		String zType = zDeviceCommonInfo.getDeviceType();
		executeOrder.setZmeType(zType);
		
		String station = "A:" + aDeviceCommonInfo.getStationName() + " <-> Z:" + zDeviceCommonInfo.getStationName();
		executeOrder.setStation(station);
		executeOrder.setAddress("A:" + aDeviceCommonInfo.getHostRoomName() + " <--> Z:" + zDeviceCommonInfo.getHostRoomName());
		executeOrder.setCreateTime(new Date());
		executeOrder.setCreator((String)session.getAttribute("loginName"));
		
		String type = "1".equals(executeOrder.getType())?"1":"2";
		executeOrder.setType(type);
		
		//设置路由工单编码：设备编码/a板卡号/a端口号/z板卡号/z端口号
		String aCardSeq = executeOrder.getAcard().split("/")[1];
		String aPortSeq = executeOrder.getAportCode().split("/")[3];
		String zCardSeq = executeOrder.getZcard().split("/")[1];
		String zPortSeq = executeOrder.getZportCode().split("/")[3];
		String code = aCode + "/" + aCardSeq + "/" + aPortSeq + "/" + zCardSeq + "/" + zPortSeq + "/" + type;
		executeOrder.setCode(code);
		//Acard Zcard 是板卡在设备上的序号
		//TerminalUnit aCard = opticalDeviceDao.getBoardByBoardCode(executeOrder.getAcard());// 板卡编码：设备名/板卡序号
		//executeOrder.setAcard(executeOrder.getAcard().split("/")[1]);
		
		//TerminalUnit zCard = opticalDeviceDao.getBoardByBoardCode(executeOrder.getZcard());// 板卡编码：设备名/板卡序号
		//executeOrder.setZcard(executeOrder.getZcard().split("/")[1]);
		
		if(verifyExecuteOrder(aType,aCode,executeOrder.getAportCode(),zType,zCode,executeOrder.getZportCode())){
			return "existedExecuteOrder";
		}
		return businessWorkListDao.addExecuteOrder(executeOrder);
	}

	@Override
	public boolean assignExecuteOrder(String routeTaskCode,
			String groupName, String userName,String jobOrderCode) {
		boolean flag = true;
		List<ExecuteOrder> executeOrders  = businessWorkListDao.getAllExecuteOrderByJobOrder(jobOrderCode);
		for(ExecuteOrder executeOrder : executeOrders){
			if(executeOrder.getStatus() == 1){//
				flag = false;
				break;
			}
		}
		if(flag){//如果全部路由工单均已指派，更改业务工单状态为已指派待施工状态
			businessWorkListDao.updateBusinessWorkListStatus(jobOrderCode,3);
		}
		return businessWorkListDao.assignExecuteOrder(routeTaskCode,groupName,userName);
	}

	@Override
	public void updateJobOrderStatus(String jobOrderCode, int status) {
		businessWorkListDao.updateBusinessWorkListStatus(jobOrderCode, status);
	}

	@Override
	public boolean verifyExecuteOrder(String aType,String aCode,String aPortCode,String zType,String zCode,String zPortCode) {
		return businessWorkListDao.getExecuteOrderByAZ(aType,aCode,aPortCode,zType,zCode,zPortCode);
	}
	
}
