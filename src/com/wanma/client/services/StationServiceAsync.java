package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.District;
import com.wanma.domain.Station;

public interface StationServiceAsync{
	
	public void addStation(Station station,AsyncCallback<String> callback);
	
	public void addDistrict(District district,AsyncCallback<String> callback);

	public void getDistricts(AsyncCallback<List<District>> asyncCallback);

	public void getStations(AsyncCallback<List<Station>> asyncCallback);

	public void getStationsByDistName(String disName,
			AsyncCallback<List<Station>> stationsCallback);
}
