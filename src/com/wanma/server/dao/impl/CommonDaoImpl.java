package com.wanma.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.wanma.client.utils.Constants;
import com.wanma.domain.AlarmLog;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Port;
import com.wanma.server.dao.CommonDao;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.utils.HibernateUtil;

public class CommonDaoImpl implements CommonDao {

	
	private OpticalDeviceDao opticalDeviceDao = null;
	
	
	/**
	 * 警告信息：
	 * 设备编码 板卡位置    端口位置
	 * info：0正常  1有跳纤插入  2有跳纤拔出
	 */
	@Override
	public int addAlarmLogInfo(AlarmLog alarmLog) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(alarmLog);
		tx.commit();
		return temp;
	}
	
	@Override
	public List<AlarmLog> getAllAlarmLogInfos(){
		List<AlarmLog> temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(AlarmLog.class);
		temp = criteria.list();
		tx.commit();
		return temp;
	}
	
	@Override
	public List<AlarmLog> getAlarmLogInfosByDeviceType(String deviceType){
		List<AlarmLog> temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(AlarmLog.class);
		criteria.add(Restrictions.eq("deviceType", deviceType));
		temp = criteria.list();
		tx.commit();
		return temp;
	}
	
	/**
	 * ========webservice============方法
	 */
	
	@Override
	public boolean alarmInfo(String deviceCode, int boardNum, int portNum,int info) {
		
		opticalDeviceDao = new OpticalDeviceDaoImpl();
		
		DeviceCommonInfo device = opticalDeviceDao.getDeviceCommonInfoByDeviceCode(deviceCode);
		
		AlarmLog alarm = new AlarmLog();
		alarm.setAlarmtype(info);
		alarm.setBoard(boardNum+"");
		alarm.setPort(portNum+"");
		alarm.setDeviceCode(deviceCode);
		alarm.setDeviceName(device.getDeviceName());
		alarm.setDeviceType(device.getDeviceType());
		alarm.setLogtime(new Date());
		
		Integer alarmId = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		alarmId = (Integer) session.save(alarm);
		tx.commit();
		updatePortInfo(deviceCode,boardNum,portNum,info);
		return alarmId != -1;
	}
	
	//================测试方法================
	
	private boolean updatePortInfo(String deviceCode, int boardNum, int portNum,int info) {
		int temp = boardNum / Constants.boardNumPerFrame;
		int temp0 = boardNum % Constants.boardNumPerFrame;
		
		int frameNo = temp0 == 0 ? temp : temp+1;
		
		String portCode = deviceCode + "/" + frameNo + "/" + boardNum + "/" + portNum;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		
		Criteria c = session.createCriteria(Port.class);
		c.add(Restrictions.eq("code", portCode));
		Port port = (Port)c.list().get(0);
		//info 0恢复正常  1有跳纤插入 2有跳纤拔出
		
		String serviceStatue = port.getServiceStatue();
		String preServiceState = port.getPreServiceState();
		if(info == 1){
			preServiceState = serviceStatue;
			serviceStatue = "3";
		}else if(info == 2){
			preServiceState = serviceStatue;
			serviceStatue = "4";
		}else if(info == 0){
			serviceStatue = preServiceState;
		}
		
		Query query = session.createQuery("update Port set serviceStatue='" + serviceStatue + "',preServiceState='"+ preServiceState + "' where code='" + portCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Test
	public void testAlarm(){
		//0200480D 0001 2D7425C3030000EC 01 08 04 DD6E03
		//0200480D 0001 2D7425C3030000EC 00 08 04 1D3F03
		String deviceCode = "2D7425C3030000EC";
		int boardNum = 12;
		int portNum = 2;
		int info = 0;
		new CommonDaoImpl().alarmInfo(deviceCode, boardNum, portNum, info);
	} 
	
}
