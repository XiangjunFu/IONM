package com.wanma.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import com.wanma.domain.Cable;
import com.wanma.domain.CableSpan;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Fiber;
import com.wanma.server.dao.CableDao;
import com.wanma.server.utils.HibernateUtil;

public class CableDaoImpl implements CableDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3915827334324305467L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#addCable(com.wanma.domain.Cable)
	 */
	@Override
	public String addCable(Cable cable) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(cable);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#getCables()
	 */
	@Override
	public List<Cable> getCables() {
		List<Cable> cables = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Cable.class);
		cables = criteria.list();
		tx.commit();
		return cables;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#deleteCable(java.lang.String)
	 */
	@Override
	public Boolean deleteCable(String cableName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from Cable where name='" + cableName +"'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#deleteCableSpan(java.lang.String)
	 */
	@Override
	public Boolean deleteCableSpan(String code) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from CableSpan where code='" + code +"'");
		int count = query.executeUpdate();
		tx.commit();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#getAllCableSpan()
	 */
	@Override
	public List<CableSpan> getAllCableSpan() {
		List<CableSpan> cableSpans = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(CableSpan.class);
		cableSpans = criteria.list();
		tx.commit();
		return cableSpans;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#getCableByCableName(java.lang.String)
	 */
	@Override
	public Cable getCableByCableName(String cableName) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Cable.class);
		criteria.add(Restrictions.eq("name", cableName));
		List<Cable> cables = (List<Cable>) criteria.list();
		tx.commit();
		return cables.size() >= 1 ? cables.get(0) : null;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#updateCable(com.wanma.domain.Cable)
	 */
	@Override
	public void updateCable(Cable cable) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(cable);
		tx.commit();
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#getCableSpanByCode(java.lang.String)
	 */
	@Override
	public CableSpan getCableSpanByCode(String code) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(CableSpan.class);
		criteria.add(Restrictions.eq("code", code));
		List<CableSpan> cableSpans = (List<CableSpan>) criteria.list();
		tx.commit();
		return cableSpans.size() >= 1 ? cableSpans.get(0) : null;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#addCableSpan(com.wanma.domain.CableSpan)
	 */
	@Override
	public String addCableSpan(CableSpan cableSpan) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(cableSpan);
		tx.commit();
		session.close();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#updateCableSpan(com.wanma.domain.CableSpan)
	 */
	@Override
	public void updateCableSpan(CableSpan cableSpan) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.update(cableSpan);
		tx.commit();
		session.close();
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#getAllFibers()
	 */
	@Override
	public List<Fiber> getAllFibers() {
		List<Fiber> cableSpans = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Fiber.class);
		cableSpans = criteria.list();
		tx.commit();
		return cableSpans;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#deleteFiber(java.lang.String)
	 */
	@Override
	public boolean deleteFiber(String fiberCode) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("delete from CableSpan where code='" + fiberCode +"'");
		int count = query.executeUpdate();
		tx.commit();
		session.close();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#addFiber(com.wanma.domain.Fiber)
	 */
	@Override
	public String addFiber(Fiber fiber) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(fiber);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#getFiberByFiberCode(java.lang.String)
	 */
	@Override
	public Fiber getFiberByFiberCode(String code) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Fiber.class);
		criteria.add(Restrictions.eq("code", code));
		List<Fiber> fibers = (List<Fiber>) criteria.list();
		tx.commit();
		return fibers.size() >= 1 ? fibers.get(0) : null;		
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#updateFiber(com.wanma.domain.Fiber)
	 */
	@Override
	public void updateFiber(Fiber fiber) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.update(fiber);
		tx.commit();
		session.close();
	}
	
	@Test
	public void test(){
		List<Cable> cables = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Cable.class);
		cables = criteria.list();
		tx.commit();
		System.out.println(cables.get(0).getCode());
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.CableDao#updateFiberByLogicalNo(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	@Override
	public int updateFiberByLogicalNo(String cableSpanCode,int logicalNo,String deviceName, String aPortCode) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(DeviceCommonInfo.class);
		criteria.add(Restrictions.eq("deviceName", deviceName));
		DeviceCommonInfo deviceCommonInfo = (DeviceCommonInfo)criteria.list().get(0);
		String deviceCode = deviceCommonInfo.getDeviceCode();
		String deviceType = deviceCommonInfo.getDeviceType();
		//System.out.println(deviceType + " : " + deviceName);
		Query query = session.createQuery("update Fiber set aMeCode='" + deviceCode +"',aMeType='" + deviceType + "',aPortCode='" + aPortCode +"' where parentCode='" + cableSpanCode + "' and logicalNo='" + logicalNo + "'");
		int count = query.executeUpdate();
		tx.commit();
		session.close();
		return count;
	}
}
