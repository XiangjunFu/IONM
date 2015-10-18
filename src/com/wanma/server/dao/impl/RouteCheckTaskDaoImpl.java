package com.wanma.server.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.RouteCheckTask;
import com.wanma.domain.dto.RouteCheckTaskDTO;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.dao.RouteCheckTaskDao;
import com.wanma.server.utils.HibernateUtil;

public class RouteCheckTaskDaoImpl implements RouteCheckTaskDao {

	@Override
	public String addRouteCheckTask(RouteCheckTask checkWorkList) {
		String code = "";
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		code = (String)session.save(checkWorkList);
		tx.commit();
		return code;
	}

	@Override
	public boolean assignCheckTask(String workListCode, String groupName,
			String userName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update RouteCheckTask set groupId='" + groupName + "',workerId= '" + userName + "',status='" + 2 + "' where code='" + workListCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	
	@Override
	public List<RouteCheckTask> getAllCheckWorkListByDeviceType(
			String deviceType) {
		List<RouteCheckTask> checkWorkLists = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria  criteria = session.createCriteria(RouteCheckTask.class);
		criteria.add(Restrictions.eq("deviceType", deviceType));
		checkWorkLists = criteria.list();
		tx.commit();
		return checkWorkLists;
	}
	
	
	/**
	 * ============================web service方法===========================
	 */
	private List<RouteCheckTaskDTO> changeDomainToDto(List<RouteCheckTask> checkWorkLists){
		List<RouteCheckTaskDTO> dtos = new ArrayList<RouteCheckTaskDTO>();
		for(RouteCheckTask task : checkWorkLists){
			RouteCheckTaskDTO dto = new RouteCheckTaskDTO();
			
			dto.setCode(task.getCode());
			dto.setCreateTime(DateUtils.formatDateToString(task.getCreateTime()));
			dto.setCreator(task.getCreator());
			
			dto.setDeviceCode(task.getDeviceCode());
			dto.setDeviceName(task.getDeviceName());
			dto.setDeviceType(task.getDeviceType());
			
			dto.setGroupId(task.getGroupId());
			dto.setName(task.getName());
			dto.setStatus(Constants.routeWorkListStatus[task.getStatus()]);
			
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public boolean updateCheckWorkListStatus(String code,String oldCode,String newCode,String result,String status) {
		Short temp = Short.parseShort(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update RouteCheckTask set status='" + temp + "',result='" + result + "' where code='" + code + "'");
		int count = query.executeUpdate();
		tx.commit();
		if(count > 0){
			OpticalDeviceDao opticalDeviceDao = new OpticalDeviceDaoImpl();
			opticalDeviceDao.updateDeviceInfo(oldCode, newCode, result);
			return true;
		}
		return count > 0;
	}

	@Override
	public List<RouteCheckTask> getCheckWorkListByWorker(String username,
			String status) {
		Short temp = Short.parseShort(status);
		List<RouteCheckTask> checkWorkLists = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria  criteria = session.createCriteria(RouteCheckTask.class);
		criteria.add(Restrictions.eq("workerId", username)).add(Restrictions.eq("status", temp));
		checkWorkLists = criteria.list();
		tx.commit();
		/*
		 * 更改工单状态在确实执行时改变
		if(checkWorkLists.size() > 0){
			//更改工单为执行状态
			updateCheckTaskStatus(username,"","3");
		}*/
		return checkWorkLists;
	}

	@Override
	public List<RouteCheckTask> getCheckWorkListByDevice(String username,
			String deviceCode, String status) {
		Short temp = Short.parseShort(status);
		List<RouteCheckTask> checkWorkLists = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria  criteria = session.createCriteria(RouteCheckTask.class);
		criteria.add(Restrictions.eq("workerId", username)).add(Restrictions.eq("status", temp));
		if(deviceCode != null && !"".equals(deviceCode)){
			criteria.add(Restrictions.eq("deviceCode", deviceCode));
		}
		checkWorkLists = criteria.list();
		tx.commit();
		/*
		 * 更改工单状态在确实执行时改变
		if(checkWorkLists.size() > 0){
			//更改工单为执行状态
			updateCheckTaskStatus(username,deviceCode,"3");
		}*/
		return checkWorkLists;
	}

	private boolean updateCheckTaskStatus(String username, String deviceCode,
			String status) {
		Short temp = Short.parseShort(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String sql = "";
		if(deviceCode == null || "".equals(deviceCode)){
			sql = "update RouteCheckTask set status='" + temp + "' where workerId='" + username + "'";
		}else{
			sql = "update RouteCheckTask set status='" + temp + "' where workerId='" + username + "' and deviceCode='" + deviceCode + "'";
		}
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public List<RouteCheckTask> getCheckWorkListByDeviceCode(String deviceCode) {
		List<RouteCheckTask> checkWorkLists = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria  criteria = session.createCriteria(RouteCheckTask.class);
		criteria.add(Restrictions.eq("deviceCode", deviceCode));
		checkWorkLists = criteria.list();
		tx.commit();
		return checkWorkLists;
	}
}
