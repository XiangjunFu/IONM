package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.Cable;
import com.wanma.domain.CableSpan;
import com.wanma.domain.Fiber;

public interface CableServiceAsync {

	public void addCable(Cable cable,AsyncCallback<String> callback);

	public void getCables(AsyncCallback<List<Cable>> callback);

	public void deleteCable(String cableName,AsyncCallback<Boolean> callback);
	
	public void deleteCableSpan(String code,AsyncCallback<Boolean> callback);

	public void getAllCableSpan(AsyncCallback<List<CableSpan>> callback);

	public void getCableByCableName(String cableName,AsyncCallback<Cable> callback);

	public void updateCable(Cable cable, AsyncCallback<Void> asyncCallback);

	public void getCableSpanByCode(String code, AsyncCallback<CableSpan> callback);

	public void addCableSpan(CableSpan cableSpan,AsyncCallback<String> callback);

	public void updateCableSpan(CableSpan cableSpan, AsyncCallback<Void> callback);

	public void getAllFibers(AsyncCallback<List<Fiber>> asyncCallback);

	public void deleteFiber(String code, AsyncCallback<Boolean> asyncCallback);

	public void addFiber(Fiber fiber, AsyncCallback<String> asyncCallback);

	public void getFiberByFiberCode(String code,
			AsyncCallback<Fiber> asyncCallback);

	public void updateFiber(Fiber fiber, AsyncCallback<Void> asyncCallback);
	
	public void updateFiberByLogicalNo(String cableSpanCode,int logicalNo,String deviceName, String aPortCode, AsyncCallback<Integer> callback);
}
