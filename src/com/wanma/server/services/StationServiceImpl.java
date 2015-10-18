package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.StationService;
import com.wanma.domain.District;
import com.wanma.domain.Station;
import com.wanma.server.dao.StationDao;
import com.wanma.server.dao.impl.StationDaoImpl;

public class StationServiceImpl extends RemoteServiceServlet implements
		StationService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3338622825166925521L;

	private StationDao stationDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		stationDao = new StationDaoImpl();
	}

	@Override
	public String addStation(Station station) {
		return stationDao.addStation(station);
	}

	@Override
	public String addDistrict(District district) {
		return stationDao.addDistrict(district);
	}

	@Override
	public List<District> getDistricts() {
		return stationDao.getDistricts();
	}

	@Override
	public List<Station> getStations() {
		return stationDao.getStations();
	}

	@Override
	public List<Station> getStationsByDistName(String disName) {
		return stationDao.getStationsByDis(disName);
	}

}
