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
import com.wanma.client.utils.Constants;
import com.wanma.domain.ExecuteOrder;
import com.wanma.domain.JobOrder;
import com.wanma.domain.dto.RouteWorkList;
import com.wanma.server.dao.BusinessWorkListDao;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.utils.HibernateUtil;

public class BusinessWorkListDaoImpl implements BusinessWorkListDao {

	@Override
	public List<ExecuteOrder> getAllExecuteOrderByJobOrder(String jobOrderCode) {
		List<ExecuteOrder> executeOrders = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ExecuteOrder.class);
		criteria.add(Restrictions.eq("jobOderCode", jobOrderCode));
		executeOrders = criteria.list();
		tx.commit();
		return executeOrders;
	}
	
	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.WorkListDao#getAllBusinessWorkList(java.lang.String)
	 */
	@Override
	public List<JobOrder> getAllBusinessWorkList() {
		List<JobOrder> joborders = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(JobOrder.class);
		joborders = criteria.list();
		tx.commit();
		return joborders;
	}

	@Override
	public Integer addBusinessWorkList(JobOrder jobOrder) {
		Integer code = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		code = (Integer)session.save(jobOrder);
		tx.commit();
		return code;
	}

	@Override
	public String addExecuteOrder(ExecuteOrder executeOrder) {
		String code = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		code = (String)session.save(executeOrder);
		tx.commit();
		return code;
	}

	@Override
	public boolean assignExecuteOrder(String routeTaskCode,
			String groupName, String userName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update ExecuteOrder set groupName='" + groupName + "',worker= '" + userName + "',status='" + 2 + "' where code='" + routeTaskCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public boolean updateBusinessWorkListStatus(String jobOrderCode,int status) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update JobOrder set status='" + status + "' where code='" + jobOrderCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public boolean getExecuteOrderByAZ(String aType, String aCode,
			String aPortCode, String zType, String zCode, String zPortCode) {
		List<ExecuteOrder> executeOrders = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ExecuteOrder.class);
		criteria.add(Restrictions.eq("ameType", aType)).add(Restrictions.eq("ameCode", aCode))
		.add(Restrictions.eq("aportCode", aPortCode)).add(Restrictions.eq("zmeType", zType))
		.add(Restrictions.eq("zmeCode", zCode)).add(Restrictions.eq("zportCode", zPortCode));
		executeOrders = criteria.list();
		tx.commit();
		return executeOrders.size()>0;
	}

	/**
	 * =========================web service方法================================
	 */
	
	@Override
	public List<RouteWorkList> getExecuteOrderByDevice(String worker,String deviceCode, String status) {
		short temp = Short.parseShort(status);
		List<ExecuteOrder> executeOrders = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ExecuteOrder.class);
		criteria.add(Restrictions.eq("worker", worker)).add(Restrictions.eq("status", temp));
		if(deviceCode != null && !"".equals(deviceCode)){
			criteria.add(Restrictions.or(Restrictions.eq("ameCode", deviceCode), Restrictions.eq("zmeCode", deviceCode)));
		}
		executeOrders = criteria.list();
		tx.commit();
		return changeToRouteWorkList(executeOrders);
	}

	@Override
	public List<RouteWorkList> getExecuteOrderByStatus(String worker,String status) {
		short temp = Short.parseShort(status);
		List<ExecuteOrder> executeOrders = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(ExecuteOrder.class);
		criteria.add(Restrictions.eq("status", temp)).add(Restrictions.eq("worker", worker));
		executeOrders = criteria.list();
		tx.commit();
		//if(executeOrders.size() > 0){
		//	updateExecuteOrderStatus(worker,"","3");
		//}
		return changeToRouteWorkList(executeOrders);
	}

	private boolean updateExecuteOrderStatus(String worker,String deviceCode,String status) {
		Short temp = Short.parseShort(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String sql = "";
		if(deviceCode == null || "".equals(deviceCode)){
			sql = "update ExecuteOrder set status='" + temp + "' where worker='" + worker + "'";
		}
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	private List<RouteWorkList> changeToRouteWorkList(
			List<ExecuteOrder> executeOrders) {
		List<RouteWorkList> tempList = null;
		if(executeOrders != null){
			tempList = new ArrayList<RouteWorkList>();
			for(ExecuteOrder routeExecuteOrder : executeOrders){
				RouteWorkList workList = new RouteWorkList();
				
				workList.setCode(routeExecuteOrder.getCode());
				workList.setName(routeExecuteOrder.getName());
				
				workList.setaCode(routeExecuteOrder.getAmeCode());
				workList.setaType(routeExecuteOrder.getAmeType());
				
				//端口编码：设备编码/机框序号/板卡序号/端口序号
				//在A端板卡上序号 ./././.
				String aPortSeq = routeExecuteOrder.getAportCode().split("/")[3];
				workList.setaPortCode(aPortSeq);
				workList.setaCard(routeExecuteOrder.getAcard().split("/")[1]);
				
				workList.setzCode(routeExecuteOrder.getZmeCode());
				workList.setzType(routeExecuteOrder.getZmeType());
				
				//在Z端板卡上的序号  ./././.
				String zPortSeq = routeExecuteOrder.getZportCode().split("/")[3];
				workList.setzPortCode(zPortSeq);
				workList.setzCard(routeExecuteOrder.getZcard().split("/")[1]);
				
				workList.setType(routeExecuteOrder.getType());
				workList.setStatus(routeExecuteOrder.getStatus()+"");
				workList.setStation(routeExecuteOrder.getStation());
				workList.setAddress(routeExecuteOrder.getAddress());
				workList.setCreateTime(routeExecuteOrder.getCreateTime() == null?"--":DateUtils.formatDate(routeExecuteOrder.getCreateTime()));
				workList.setDeadline(routeExecuteOrder.getExpectedFinishTime() == null?"--":DateUtils.formatDate(routeExecuteOrder.getExpectedFinishTime()));
				
				tempList.add(workList);
			}
		}
		return tempList;
	}
	
	@Test
	public void test(){
		
	}

	/**
	 * 更改路由工单状态：
	 * 如果
	 */
	@Override
	public boolean updateRouteWorkListStatus(String code, String deviceCode,
			String aBoardSeq, String aPortSeq, String zBoardSeq,
			String zPortSeq, String opType, String status, String result) {
		boolean jump = false;
		boolean remove = false;
		OpticalDeviceDao opticalDeviceDao = new OpticalDeviceDaoImpl();
		if("1".equals(opType)){
			jump = opticalDeviceDao.jumpLine(deviceCode, aBoardSeq, aPortSeq, zBoardSeq, zPortSeq, result);
		}else if("2".equals(opType)){
			remove = opticalDeviceDao.removeLine(deviceCode, aBoardSeq, aPortSeq, zBoardSeq, zPortSeq, result);
		}
		boolean success = onlyUpdateRouteWorkListStatus(deviceCode, aBoardSeq,
				aPortSeq, zBoardSeq, zPortSeq, opType, status, result);
		return success && (jump || remove);
	}

	/**
	 * 仅更改路由工单状态：
	 */
	@Override
	public boolean onlyUpdateRouteWorkListStatus(String deviceCode,
			String aBoardSeq, String aPortSeq, String zBoardSeq,
			String zPortSeq, String opType, String status, String result) {
		boolean preAUpdate = false;
		boolean preZUpdate = false;
		//说明路由工单发送成功，更改端口状态为预跳纤状态,并且是跳纤状态
		if("3".equals(status) && "1".equals(opType)){
			int aboard = Integer.parseInt(aBoardSeq);
			int aPort = Integer.parseInt(aPortSeq);
			int zBoard = Integer.parseInt(zBoardSeq);
			int zPort = Integer.parseInt(zPortSeq);
			preAUpdate = preUpdatePortInfo(deviceCode,aboard,aPort,"5");
			preZUpdate = preUpdatePortInfo(deviceCode,zBoard,zPort, "5");
		}
		String routeWorkListCode = deviceCode + "/" + aBoardSeq + "/" +aPortSeq + "/" + zBoardSeq + "/" + zPortSeq + "/" + opType;
		Short temp = Short.parseShort(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update ExecuteOrder set status='" + temp + "',result='" + result + "' where code='" + routeWorkListCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	
	private boolean preUpdatePortInfo(String deviceCode, int board, int port,String status){
		int temp = board / Constants.boardNumPerFrame;
		int temp0 = board % Constants.boardNumPerFrame;
		
		int frameNo = temp0 == 0 ? temp : temp + 1;
		
		String portCode = deviceCode + "/" + frameNo + "/" + board + "/" + port;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Port set physicalStatue='" + status + "',serviceStatue='" + status + "' where code='" + portCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	
	@Test
	public void testUpdateRoute(){
		//02004610 0001 2D7425C3030000EC 01 02 01 0D 01 01 6E0A03
		
		//0200470F 0001 2D7425C3030000EC 01 0D 02 01 0D 337803 跳纤成功
		String deviceCode = "2D7425C3030000EC";
		String opType = "2";
		String aBoardSeq = "2";
		String aPortSeq = "1";
		String zBoardSeq = "13";
		String zPortSeq = "1";
		String result = "1";
		String status = "5";
		new BusinessWorkListDaoImpl().updateRouteWorkListStatus("", deviceCode, aBoardSeq, aPortSeq, zBoardSeq, zPortSeq, opType, status, result);
	}
}
