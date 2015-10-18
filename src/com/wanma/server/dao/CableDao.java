package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.Cable;
import com.wanma.domain.CableSpan;
import com.wanma.domain.Fiber;

public interface CableDao {

	public abstract String addCable(Cable cable);

	public abstract List<Cable> getCables();

	public abstract Boolean deleteCable(String cableName);

	public abstract Boolean deleteCableSpan(String code);

	public abstract List<CableSpan> getAllCableSpan();

	public abstract Cable getCableByCableName(String cableName);

	public abstract void updateCable(Cable cable);

	public abstract CableSpan getCableSpanByCode(String code);

	public abstract String addCableSpan(CableSpan cableSpan);

	public abstract void updateCableSpan(CableSpan cableSpan);

	public abstract List<Fiber> getAllFibers();

	public abstract boolean deleteFiber(String fiberCode);

	public abstract String addFiber(Fiber fiber);

	public abstract Fiber getFiberByFiberCode(String code);

	public abstract void updateFiber(Fiber fiber);

	/**
	 * 获取光纤，
	 */
	public abstract int updateFiberByLogicalNo(String cableSpanCode,
			int logicalNo, String deviceName, String aPortCode);

}