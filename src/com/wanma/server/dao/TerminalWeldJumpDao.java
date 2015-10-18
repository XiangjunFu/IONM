package com.wanma.server.dao;

import com.wanma.domain.JumperConnector;
import com.wanma.domain.TerminalUnit;
import com.wanma.domain.WeldConnector;

public interface TerminalWeldJumpDao {

	public abstract Integer addJumpConnector(JumperConnector jump);

	public abstract Integer addWeldConnector(WeldConnector weld);

	public abstract String addNewModule(TerminalUnit terminalUnit);

	public abstract int updatePortInfo(String portCode, String serviceStatus);

}