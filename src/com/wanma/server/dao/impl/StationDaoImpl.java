package com.wanma.server.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.wanma.domain.District;
import com.wanma.domain.Station;
import com.wanma.server.dao.StationDao;
import com.wanma.server.utils.HibernateUtil;

public class StationDaoImpl implements StationDao {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3338622825166925521L;

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.StationDao#addStation(com.wanma.domain.Station)
	 */
	@Override
	public String addStation(Station station) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(station);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.StationDao#addDistrict(com.wanma.domain.District)
	 */
	@Override
	public String addDistrict(District district) {
		String temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		temp = (String) session.save(district);
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.StationDao#getDistricts()
	 */
	@Override
	public List<District> getDistricts() {
		List<District> temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(District.class);
		temp = criteria.list();
		tx.commit();
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wanma.server.dao.impl.StationDao#getStations()
	 */
	@Override
	public List<Station> getStations() {
		List<Station> temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Station.class);
		temp = criteria.list();
		tx.commit();
		return temp;
	}

	@Override
	public List<Station> getStationsByDis(String name) {
		List<Station> temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Station.class);
		criteria.add(Restrictions.eq("districtName", name));
		temp = criteria.list();
		tx.commit();
		return temp;
	}

	@Override
	public Station getStationByCode(String stationCode) {
		Station temp = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Station.class);
		criteria.add(Restrictions.eq("code", stationCode));
		temp = (Station)criteria.list().get(0);
		tx.commit();
		return temp;
	}

}
