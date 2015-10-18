package com.wanma.server.services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.TreeService;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.District;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Station;
import com.wanma.domain.UserOdn;
import com.wanma.domain.WorkGroup;
import com.wanma.server.dao.GroupDao;
import com.wanma.server.dao.MachineRoomDao;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.dao.StationDao;
import com.wanma.server.dao.UserDao;
import com.wanma.server.dao.impl.GroupDaoImpl;
import com.wanma.server.dao.impl.MachineRoomDaoImpl;
import com.wanma.server.dao.impl.OpticalDeviceDaoImpl;
import com.wanma.server.dao.impl.StationDaoImpl;
import com.wanma.server.dao.impl.UserDaoImpl;

public class TreeServiceImpl extends RemoteServiceServlet implements
		TreeService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8803357112717405178L;

	private StationDao stationDao = null;

	private MachineRoomDao machineRoomDao = null;

	private OpticalDeviceDao opticalDeviceDao = null;

	private UserDao userDao = null;
	
	private GroupDao groupDao = null;
	
	private String[] types = new String[]{"IODF","IFDT","IFAT","OBD"};
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		stationDao = new StationDaoImpl();
		machineRoomDao = new MachineRoomDaoImpl();
		opticalDeviceDao = new OpticalDeviceDaoImpl();
		userDao = new UserDaoImpl();
		groupDao = new GroupDaoImpl();
	}

	@Override
	public List<ModelData> getAsyncTree(ModelData data) {
		List<ModelData> result = new ArrayList<ModelData>();
		if (data == null) {
			ModelData root = new BaseModelData();
			root.set("name", "空间资源");
			root.set("code", "root");
			root.set("type", "root");
			result.add(root);
			// 空间资源
			return result;
		} else {
			// 得到type，判断是什么类型
			/**
			 * 类型：level：0 1 2 3
			 * 
			 * 1.是空间资源，获取所有区域信息 2.是区域，获取区域名称，获取该区域下所有局站信息
			 * 3.是局站，获取局站名称，获取该局站下所有机房信息 4.是机房，获取机房名称，获取该机房下所有设备信息
			 */
			String type = data.get("type");
			String name = data.get("name");
			if ("root".equals(type)) {
				// 如果是root，区域
				List<District> districts = stationDao.getDistricts();
				for (District dis : districts) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", dis.getDistrictName());
					modelData.set("code", dis.getZipCode());
					modelData.set("type", "district");
					result.add(modelData);
				}
				return result;
			} else if ("district".equals(type)) {
				// 如果是root_child,局站
				List<Station> stations = stationDao.getStationsByDis(name);
				for (Station station : stations) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", station.getName());
					modelData.set("code", station.getCode());
					modelData.set("type", "station");
					result.add(modelData);
				}
				return result;
			} else if ("station".equals(type)) {
				// 如果是root_child_child，机房
				List<HostRoom> hostRooms = machineRoomDao
						.getMachineRoomInfosByStationName(name);
				for (HostRoom hostRoom : hostRooms) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", hostRoom.getName());
					modelData.set("code", hostRoom.getCode());
					modelData.set("type", "hostroom");
					result.add(modelData);
				}
				return result;
			} else if ("hostroom".equals(type)) {
				// 如果是root_child_child_child，设备
				List<DeviceCommonInfo> deviceInfos = opticalDeviceDao
						.getDeviceInfosByHostRoomName(name);
				for (DeviceCommonInfo deviceInfo : deviceInfos) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", deviceInfo.getDeviceName());
					modelData.set("code", deviceInfo.getDeviceCode());
					modelData.set("type", "device");
					modelData.set("deviceType", deviceInfo.getDeviceType());
					result.add(modelData);
				}
				return result;
			}
		}
		return result;
	}

	@Override
	public List<ModelData> getDevResourceLeftPanelAsyncTree(ModelData data) {
		List<ModelData> result = new ArrayList<ModelData>();
		if (data == null) {
			ModelData root = new BaseModelData();
			root.set("name", "设备资源");
			root.set("deviceType", "root");
			root.set("nodeType", "root");
			result.add(root);
			// 空间资源
			return result;
		} else {
			// 得到type，判断是什么类型
			String nodeType = data.get("nodeType");
			String deviceType = data.get("deviceType");
			
			if ("root".equals(nodeType)) {
				// 如果是root，显示设备类型
				for (String str : types) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", str+"设备");
					modelData.set("deviceType", str);
					modelData.set("nodeType", "deviceType");
					result.add(modelData);
				}
				return result;
			} else if ("deviceType".equals(nodeType)) {
				// 如果是root_child,局站
				List<DeviceCommonInfo> devices = opticalDeviceDao.getDeviceInfosByType(deviceType);
				for (DeviceCommonInfo dev : devices) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", dev.getDeviceName());
					modelData.set("deviceType", dev.getDeviceType());
					modelData.set("nodeType", "device");
					result.add(modelData);
				}
				return result;
			}
		}
		return result;
	}

	@Override
	public List<ModelData> getGroupWorkerAsyncTree(ModelData data) {
		List<ModelData> result = new ArrayList<ModelData>();
		if (data == null) {
			ModelData root = new BaseModelData();
			root.set("name", "施工人员管理");
			root.set("nodeType", "root");
			result.add(root);
			// 空间资源
			return result;
		} else {
			// 得到type，判断是什么类型
			String nodeType = data.get("nodeType");
			String name = data.get("name");
			if ("root".equals(nodeType)) {
				// 如果是root，显示施工班组
				List<WorkGroup> groups = groupDao.getWorkGroupIds();
				for (WorkGroup group : groups) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", group.getId().getGroupName());
					modelData.set("nodeType", "group");
					result.add(modelData);
				}
				return result;
			}else if("group".equals(nodeType)){
				//显示施工人员
				List<UserOdn> users = userDao.getUsersByGroup(name);
				for (UserOdn user : users) {
					ModelData modelData = new BaseModelData();
					modelData.set("name", user.getUserName());
					modelData.set("nodeType", "user");
					result.add(modelData);
				}
				return result;
			}
		}
		return result;
	}

}
