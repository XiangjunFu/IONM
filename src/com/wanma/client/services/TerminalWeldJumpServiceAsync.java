package com.wanma.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.JumperConnector;
import com.wanma.domain.TerminalUnit;
import com.wanma.domain.WeldConnector;

public interface TerminalWeldJumpServiceAsync {

	public void addJumpConnector(JumperConnector jump,AsyncCallback<Integer> callback);

	public void addWeldConnector(WeldConnector weldConn,
			AsyncCallback<Integer> asyncCallback);

	public void addNewModule(TerminalUnit terminalUnit,
			AsyncCallback<String> asyncCallback);
	
	public void updatePortInfo(String portCode,String serviceStatus,AsyncCallback<Integer> callback);
}
