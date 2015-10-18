package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.Cable;
import com.wanma.domain.CableSpan;
import com.wanma.domain.Fiber;

@RemoteServiceRelativePath("cableService")
public interface CableService extends RemoteService {

	public String addCable(Cable cable);
	
	public List<Cable> getCables();
	
	public Boolean deleteCable(String cableName);
	
	public Boolean deleteCableSpan(String code);
	
	public List<CableSpan> getAllCableSpan();
	
	public Cable getCableByCableName(String cableName);
	
	public void updateCable(Cable cable);
	
	public CableSpan getCableSpanByCode(String code);
	
	public String addCableSpan(CableSpan cableSpan);
	
	public void updateCableSpan(CableSpan cableSpan);
	
	public List<Fiber> getAllFibers();
	
	public boolean deleteFiber(String fiberCode);
	
	public String addFiber(Fiber fiber);
	
	public Fiber getFiberByFiberCode(String code);
	
	public void updateFiber(Fiber fiber);
	
	public int updateFiberByLogicalNo(String cableSpanCode,int logicalNo,String deviceName, String aPortCode);
}
