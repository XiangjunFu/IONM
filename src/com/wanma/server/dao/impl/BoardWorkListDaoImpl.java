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
import com.wanma.domain.BoardTask;
import com.wanma.domain.dto.BoardTaskDTO;
import com.wanma.server.dao.BoardWorkListDao;
import com.wanma.server.utils.HibernateUtil;

public class BoardWorkListDaoImpl implements BoardWorkListDao {

	@Override
	public int addBoardTask(BoardTask boardTask) {
		Integer code = -1;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		code = (Integer)session.save(boardTask);
		tx.commit();
		return code;
	}

	@Override
	public boolean assignBoardTask(String workListCode, String groupName,
			String userName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update BoardTask set groupId='" + groupName + "',workerId = '" + userName + "',status='" + 2 + "' where code='" + workListCode + "'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	@Override
	public List<BoardTask> getAllBoardWorkList(String deviceType) {
		List<BoardTask> cables = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(BoardTask.class);
		criteria.add(Restrictions.eq("deviceType", deviceType));
		cables = criteria.list();
		tx.commit();
		return cables;
	}

	/**
	 * ===========Web services方法===========
	 */

	@Override
	public List<BoardTaskDTO> getBoardTaskByStatusWorker(String username,
			String status) {
		List<BoardTask> boardTasks = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(BoardTask.class);
		criteria.add(Restrictions.eq("workerId", username)).add(Restrictions.eq("status", Integer.parseInt(status)));
		boardTasks = criteria.list();
		tx.commit();
		return changeToDomain(boardTasks);
	}

	
	/**
	 * 更新板卡工单状态
	 * 1、在加载/拆除板卡命令发送成功后，更新一次工单状态;
	 * 2、在加载/拆除板卡操作完成后，更新一次工单信息;
	 * 	怎么确定工单（deviceCode,boardType,frameNum,boardNum,opType）,能确定吗？
	 */
	@Override
	public boolean updateBoardTaskStatus(String code,String deviceCode,String boardType,String frameNum,String boardNum,String opType,String status,String result) {
		
		String boardTaskCode = deviceCode + "/" + boardNum + "/" + opType;
		
		int temp = Integer.parseInt(status);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String sql = "update BoardTask set status='" + temp + "',result='" + result + "' where code='" + boardTaskCode + "'";
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		if(count > 0 && "5".equals(status)){//只是在加载/拆除板卡成功后在更改设备状态
			String newStatus = "1".equals(opType)?"2":"3";//如果操作类型是加载，更改为已管理；否则未管理
			updateBoardStatus(deviceCode,boardNum,newStatus);//已管理
		}
		return count > 0;
	}
	
	private boolean updateBoardStatus(String newCode, String boardNum,String boardStatus) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String code = newCode + "/" + boardNum;
		String sql = "update TerminalUnit set status='" + boardStatus + "' where terminalUnitCode='" + code + "'";
		Query query = session.createQuery(sql);
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}
	
	private List<BoardTaskDTO> changeToDomain(List<BoardTask> boardTasks){
		List<BoardTaskDTO> results = new ArrayList<BoardTaskDTO>();
		for(BoardTask b:boardTasks){
			BoardTaskDTO dto = new BoardTaskDTO();
			dto.setCode(b.getCode());
			dto.setName(b.getName());
			dto.setCreatetime(DateUtils.formatDate(b.getCreatetime()));
			dto.setCreator(b.getCreator());
			dto.setDeviceCode(b.getDeviceCode());
			dto.setDeviceName(b.getDeviceName());
			dto.setDeviceType(b.getDeviceType());
			dto.setFinishtime(b.getFinishtime() != null?DateUtils.formatDate(b.getFinishtime()):"--");
			dto.setFrameSeq(b.getFrameNum());
			dto.setGroupId(b.getGroupId());
			dto.setOpType(b.getOpType());
			dto.setStatus(b.getStatus()+"");
			dto.setWorkerId(b.getWorkerId());
			dto.setBoardType(b.getBoardType());
			dto.setBoardSeq(b.getBoardNum());
			
			results.add(dto);
		}
		return results;
	}
	
	@Test
	public void test(){
		//加载单板
		//0200440F 0001 2D7425C3030000EC 01 01 01 01 01 967B03
		//成功
		//0200450F 0001 2D7425C3030000EC 01 01 01 01 01 062A03
		String deviceCode = "2D7425C3030000EC";
		String boardType = "1";
		String frameNum = "1";
		String boardNum = "1";
		String opType = "1";
		String status = "5";
		String result = "1";
		boolean test = new BoardWorkListDaoImpl().updateBoardStatus(deviceCode, boardNum, "1");
		System.out.println(test);
	}
}
