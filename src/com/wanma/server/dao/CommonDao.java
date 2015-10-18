package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.AlarmLog;

public interface CommonDao {

	public boolean alarmInfo(String deviceCode,int boardNum,int portNum,int info);

	public List<AlarmLog> getAllAlarmLogInfos();
	
	public List<AlarmLog> getAlarmLogInfosByDeviceType(String deviceType);
	
	
	public int addAlarmLogInfo(AlarmLog alarmLog);
}
