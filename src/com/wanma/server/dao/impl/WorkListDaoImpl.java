package com.wanma.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.wanma.server.utils.DateUtils;
import com.wanma.domain.ConfigTask;
import com.wanma.domain.dto.ConfigTaskDTO;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.dao.WorkListDao;
import com.wanma.server.utils.HibernateUtil;

public class WorkListDaoImpl implements WorkListDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5674041302939636343L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.WorkListDao#getAllConfigWorkList(java.lang.String)
	 */
	@Override
	public List<ConfigTask> getAllConfigWorkList(String deviceType) {
		List<ConfigTask> cables = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ConfigTask.class);
		criteria.add(Restrictions.eq("deviceType", deviceType));
		cables = criteria.list();
		tx.commit();
		return cables;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.WorkListDao#addConfigTask(com.wanma.domain.ConfigTask)
	 */
	@Override
	public String addConfigTask(ConfigTask configTask) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(configTask);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.WorkListDao#assignConfigTask(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean assignConfigTask(String workListCode,String groupName, String userName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update ConfigTask set groupId='" + groupName + "',workerId = '" + userName + "',status='" + 2 + "' where code='" + workListCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	
	
	/**
	 * ====================webservice方法==================================
	 */
	@Override
	public List<ConfigTaskDTO> getConfigWorkListByDevice(String worker,String deviceCode, String status) {
		short temp = Short.parseShort(status);
		List<ConfigTask> configTasks = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ConfigTask.class);
		criteria.add(Restrictions.eq("workerId", worker)).add(Restrictions.eq("status", temp));
		if(deviceCode != null && !"".equals(deviceCode)){
			criteria.add(Restrictions.eq("deviceCode", deviceCode));
		}
		configTasks = criteria.list();
		tx.commit();
		/*
		 * 配置工单状态更改
		if(configTasks.size() > 0){
			updateConfigTaskStatus(worker, deviceCode, "3");
		}*/
		return changeDomainTODto(configTasks);
	}

	@Override
	public List<ConfigTaskDTO> getConfigWorkListByStatus(String worker,String status) {
		short temp = Short.parseShort(status);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ConfigTask.class);
		criteria.add(Restrictions.eq("status", temp)).add(Restrictions.eq("workerId", worker));
		List<ConfigTask> configTasks = criteria.list();
		tx.commit();
		/*
		 * 配置工单状态更改
		if(configTasks.size() > 0 && !"3".equals(status)){
			updateConfigTaskStatus(worker,"","3");
		}*/
		return changeDomainTODto(configTasks);
	}
	
	public boolean updateConfigTaskStatusByCode(String code,String status){
		
		return false;
	}
	
	private boolean updateConfigTaskStatus(String worker, String deviceCode, String status) {
		Short temp = Short.parseShort(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String sql = "";
		if(deviceCode == null || "".equals(deviceCode)){
			sql = "update ConfigTask set status='" + temp + "' where workerId='" + worker + "'";
		}else{
			sql = "update ConfigTask set status='" + temp + "' where workerId='" + worker + "' and deviceCode='" + deviceCode + "'";
		}
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Test
	public void test(){
		short temp = Short.parseShort("3");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ConfigTask.class);
		criteria.add(Restrictions.eq("status", temp)).add(Restrictions.eq("workerId", "test"));
		List<ConfigTask> configTasks = criteria.list();
		tx.commit();
		if(configTasks.size() > 0 && !"3".equals("3")){
			updateConfigTaskStatus("test","","3");
		}
		System.out.println(changeDomainTODto(configTasks).size());
	}
	
	private List<ConfigTaskDTO> changeDomainTODto(List<ConfigTask> configTasks){
		List<ConfigTaskDTO> configTaskdtos = new ArrayList<ConfigTaskDTO>();
		for(ConfigTask task : configTasks){
			ConfigTaskDTO dto = new ConfigTaskDTO();
			dto.setCode(task.getCode());
			dto.setCreateTime(task.getCreateTime() == null ? "--":DateUtils.formatDate(task.getCreateTime()));
			dto.setCreator(task.getCreator());
			dto.setDeadline(task.getDeadline() == null ? "--":DateUtils.formatDate(task.getDeadline()));
			dto.setDeviceCode(task.getDeviceCode());
			dto.setDeviceName(task.getDeviceName());
			dto.setDeviceType(task.getDeviceType());
			dto.setFinishedTime(task.getFinishedTime() == null ? "--":DateUtils.formatDate(task.getFinishedTime()));
			dto.setGroupId(task.getGroupId());
			dto.setName(task.getName());
			dto.setResult(task.getResult());
			dto.setStatus(task.getStatus()+"");
			dto.setWorkerId(task.getWorkerId());
			configTaskdtos.add(dto);
		}
		return configTaskdtos;
	}

	/**
	 *根据前设备编码更改成为正确的设备编码 
	 */
	@Override
	public boolean updateDeviceCode(String preCode, String newCode) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Ifdt set code='" + newCode + "' where code='" + preCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public boolean updateConfigTaskStatus(String code,String oldCode,String newCode,String result, String status) {
		Short temp = Short.parseShort(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update ConfigTask set status='" + temp + "',result='" + result +"' where code='" + code + "'");
		int count = query.executeUpdate();
		tx.commit();
		if(count > 0){
			OpticalDeviceDao opticalDeviceDao = new OpticalDeviceDaoImpl();
			opticalDeviceDao.updateDeviceInfo(oldCode, newCode, result);
			return true;
		}
		return count > 0;
	}
	
	@Test
	public void testTask(){
		//0200490200015C5203
		//02004A40 0001 2D7425C3030000EC 01 01 00 00 
		//01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 01 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
		//DC6003
		String code = "config01";
		String oldCode = "2D7425C3030000EC";
		String newCode = "2D7425C3030000EC";
		String result = "110011111111111111111111111100000000000000000000000000";
		String status = "5";
		new WorkListDaoImpl().updateConfigTaskStatus(code, oldCode, newCode, result, status);
	}
}

