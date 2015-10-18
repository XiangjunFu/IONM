package com.wanma.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.JumperConnector;
import com.wanma.domain.TerminalUnit;
import com.wanma.domain.WeldConnector;

@RemoteServiceRelativePath("terminalWeldJumpService")
public interface TerminalWeldJumpService extends RemoteService {

	public Integer addJumpConnector(JumperConnector jump);
	
	public Integer addWeldConnector(WeldConnector weld);
	
	public String addNewModule(TerminalUnit terminalUnit);
	
	public int updatePortInfo(String portCode,String serviceStatus);
}
