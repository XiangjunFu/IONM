package com.wanma.server.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.wanma.domain.JumperConnector;
import com.wanma.domain.TerminalUnit;
import com.wanma.domain.WeldConnector;
import com.wanma.server.dao.TerminalWeldJumpDao;
import com.wanma.server.utils.HibernateUtil;

public class TerminalWeldJumpDaoImpl implements TerminalWeldJumpDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2141837097173228864L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.TerminalWeldJumpDao#addJumpConnector(com.wanma.domain.JumperConnector)
	 */
	@Override
	public Integer addJumpConnector(JumperConnector jump) {
		Integer temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(jump);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.TerminalWeldJumpDao#addWeldConnector(com.wanma.domain.WeldConnector)
	 */
	@Override
	public Integer addWeldConnector(WeldConnector weld) {
		Integer temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (Integer) session.save(weld);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.TerminalWeldJumpDao#addNewModule(com.wanma.domain.TerminalUnit)
	 */
	@Override
	public String addNewModule(TerminalUnit terminalUnit) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(terminalUnit);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.TerminalWeldJumpDao#updatePortInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public int updatePortInfo(String portCode,String serviceStatus) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update Port set serviceStatue='" + serviceStatus + "' where code='" + portCode + "'");
		int temp = query.executeUpdate();
		tx.commit();
		return temp;
	}
	
}
