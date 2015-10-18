package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.District;
import com.wanma.domain.Station;

public interface StationDao {

	public abstract String addStation(Station station);

	public abstract String addDistrict(District district);

	public abstract List<District> getDistricts();

	public abstract List<Station> getStations();

	public abstract List<Station> getStationsByDis(String name);

	public abstract Station getStationByCode(String stationCode);

}