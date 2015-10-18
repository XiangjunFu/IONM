package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.District;
import com.wanma.domain.Station;

@RemoteServiceRelativePath("stationService")
public interface StationService extends RemoteService {

	public String addStation(Station station);
	
	public String addDistrict(District district);
	
	public List<District> getDistricts();
	
	public List<Station> getStations();
	
	public List<Station> getStationsByDistName(String disName);
}
