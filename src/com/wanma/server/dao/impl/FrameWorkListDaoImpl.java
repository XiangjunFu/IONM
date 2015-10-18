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
import com.wanma.domain.FrameTask;
import com.wanma.domain.dto.FrameTaskDTO;
import com.wanma.server.dao.FrameWorkListDao;
import com.wanma.server.utils.HibernateUtil;

public class FrameWorkListDaoImpl implements FrameWorkListDao {

	@Override
	public Integer addFrameTask(FrameTask frameTask) {
		Integer temp = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(frameTask);
		tx.commit();
		return temp;
	}

	@Override
	public List<FrameTask> getAllFrameWorkList(String deviceType) {
		List<FrameTask> frameTasks = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(FrameTask.class);
		criteria.add(Restrictions.eq("deviceType", deviceType));
		frameTasks = criteria.list();
		tx.commit();
		return frameTasks;
	}

	@Override
	public boolean assignFrameTask(String workListCode, String groupName,
			String userName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update FrameTask set groupId='" + groupName + "',workerId = '" + userName + "',status='" + 2 + "' where code='" + workListCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	//===========webs services==================
	
	@Override
	public List<FrameTaskDTO> getFrameTaskByStatusWorker(String username,
			String status) {
		List<FrameTask> frameTasks = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(FrameTask.class);
		criteria.add(Restrictions.eq("workerId", username)).add(Restrictions.eq("status", Integer.parseInt(status)));
		frameTasks = criteria.list();
		tx.commit();
		return changeToDomain(frameTasks);
	}

	/**
	 * 更新子框工单状态
	 * 1、在加载/拆除板卡命令发送成功后，更新一次工单状态
	 * 2、在加载/拆除板卡操作完成后，更新一次工单信息;
	 * 	怎么确定工单（deviceCode,boardType,frameNum,boardNum,opType）,能确定吗？
	 */
	@Override
	public boolean updateFrameTaskStatusByCode(String code,String deviceCode,String frameNum,String opType,String status,String result) {
		
		String frameTaskCode = deviceCode + "/" + frameNum + "/" + opType;
		
		int temp = Integer.parseInt(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String sql = "update FrameTask set status='" + temp + "',result='" + result +"' where code='" + frameTaskCode + "'";
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		if(count > 0 && "5".equals(status)){
			String newStatus = "1".equals(opType)?"2":"3";//如果操作类型是加载，更改为已管理；否则未管理
			updateFrameStatus(deviceCode,frameNum,newStatus);//已管理
		}
		return count > 0;
	}
	
	private boolean updateFrameStatus(String newCode, String frameNum,String boardStatus) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String code = newCode + "/" + frameNum;
		String sql = "update Frame set status='" + boardStatus + "' where code='" + code + "'";
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	
	private List<FrameTaskDTO> changeToDomain(List<FrameTask> frameTasks){
		List<FrameTaskDTO> results = new ArrayList<FrameTaskDTO>();
		for(FrameTask f:frameTasks){
			FrameTaskDTO dto = new FrameTaskDTO();
			dto.setCode(f.getCode());
			dto.setName(f.getName());
			dto.setCreatetime(DateUtils.formatDate(f.getCreatetime()));
			dto.setCreator(f.getCreator());
			dto.setDeviceCode(f.getDeviceCode());
			dto.setDeviceName(f.getDeviceName());
			dto.setDeviceType(f.getDeviceType());
			dto.setFinishtime(f.getFinishtime() != null?DateUtils.formatDate(f.getFinishtime()):"--");
			dto.setFrameSeq(f.getFrameNum());
			dto.setGroupId(f.getGroupId());
			dto.setOpType(f.getOpType());
			dto.setStatus(f.getStatus()+"");
			dto.setWorkerId(f.getWorkerId());
			results.add(dto);
		}
		return results;
	}
	
	
	
	@Test
	public void test(){

		//发送加载机框成功
		//0200420D 0001 2D7425C3030000EC 01 01 01 892203
		
		//加载机框成功
		//0200430D 0001 2D7425C3030000EC 01 01 01 89E303
		
		String deviceCode = "2D7425C3030000EC";
		String frameNum = "1";
		String opType = "2";
		String result = "1";
		String status = "5";
		
		String frameTaskCode = deviceCode + "/" + frameNum + "/" + opType;
		
		boolean g = new FrameWorkListDaoImpl().updateFrameTaskStatusByCode("", deviceCode, frameNum, opType, status, result);
		System.out.println(g);
	}
	
}
