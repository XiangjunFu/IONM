package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.AlarmLog;

public interface CommonServiceAsync {

	/**
	 * 告警信息相关操作
	 */
	public void addAlarmLogInfo(AlarmLog alarmLog,AsyncCallback<Integer> callback);
	
	public void getAllAlarmLogInfos(AsyncCallback<List<AlarmLog>> callback);
	
	public void getAlarmLogInfoByDeviceType(String deviceType,AsyncCallback<List<AlarmLog>> callback);
}
