package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.AlarmLog;


@RemoteServiceRelativePath("commonService")
public interface CommonService extends RemoteService {

	public int addAlarmLogInfo(AlarmLog alarmLog);
	
	public List<AlarmLog> getAllAlarmLogInfos();
	
	public List<AlarmLog> getAlarmLogInfoByDeviceType(String deviceType);
}
