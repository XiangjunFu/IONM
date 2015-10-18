package com.wanma.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import com.wanma.domain.HostRoom;
import com.wanma.server.dao.MachineRoomDao;
import com.wanma.server.utils.HibernateUtil;

public class MachineRoomDaoImpl implements MachineRoomDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2624629527423092236L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.MachineRooomDao#addMachineRoom(com.wanma.domain.HostRoom)
	 */
	@Override
	public String addMachineRoom(HostRoom room) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(room);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.MachineRooomDao#getMachineRoomInfosByStationCode(java.lang.String)
	 */
	@Override
	public List<HostRoom> getMachineRoomInfosByStationName(String stationName) {
		List<HostRoom> temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(HostRoom.class);
		criteria.add(Restrictions.eq("stationName", stationName));
		temp = criteria.list();
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.MachineRooomDao#getMachineRoomInfos()
	 */
	@Override
	public List<HostRoom> getMachineRoomInfos() {
		List<HostRoom> temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(HostRoom.class);
		temp = criteria.list();
		tx.commit();
		return temp;
	}

	@Override
	public HostRoom getMachineRoomNameByCode(String code) {
		HostRoom temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(HostRoom.class);
		criteria.add(Restrictions.eq("code", code));
		temp = (HostRoom) criteria.list().get(0);
		tx.commit();
		return temp;
	}

}
