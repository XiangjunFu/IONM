package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.CableService;
import com.wanma.domain.Cable;
import com.wanma.domain.CableSpan;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Fiber;
import com.wanma.server.dao.CableDao;
import com.wanma.server.dao.impl.CableDaoImpl;
import com.wanma.server.utils.HibernateUtil;

public class CableServiceImpl extends RemoteServiceServlet implements
		CableService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3915827334324305467L;

	private CableDao cableDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cableDao = new CableDaoImpl();
	}

	@Override
	public String addCable(Cable cable) {
		return cableDao.addCable(cable);
	}

	@Override
	public List<Cable> getCables() {
		return cableDao.getCables();
	}

	@Override
	public Boolean deleteCable(String cableName) {
		return cableDao.deleteCable(cableName);
	}

	@Override
	public Boolean deleteCableSpan(String code) {
		return cableDao.deleteCableSpan(code);
	}

	@Override
	public List<CableSpan> getAllCableSpan() {
		return cableDao.getAllCableSpan();
	}

	@Override
	public Cable getCableByCableName(String cableName) {
		return cableDao.getCableByCableName(cableName);
	}

	@Override
	public void updateCable(Cable cable) {
		cableDao.updateCable(cable);
	}

	@Override
	public CableSpan getCableSpanByCode(String code) {
		return cableDao.getCableSpanByCode(code);
	}

	@Override
	public String addCableSpan(CableSpan cableSpan) {
		return cableDao.addCableSpan(cableSpan);
	}

	@Override
	public void updateCableSpan(CableSpan cableSpan) {
		cableDao.addCableSpan(cableSpan);
	}

	@Override
	public List<Fiber> getAllFibers() {
		return cableDao.getAllFibers();
	}

	@Override
	public boolean deleteFiber(String fiberCode) {
		return cableDao.deleteFiber(fiberCode);
	}

	@Override
	public String addFiber(Fiber fiber) {
		return cableDao.addFiber(fiber);
	}

	@Override
	public Fiber getFiberByFiberCode(String code) {
		return cableDao.getFiberByFiberCode(code);		
	}

	@Override
	public void updateFiber(Fiber fiber) {
		cableDao.updateFiber(fiber);
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

	/**
	 * 获取光纤，
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
