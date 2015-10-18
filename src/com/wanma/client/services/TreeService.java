package com.wanma.client.services;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("treeService")
public interface TreeService extends RemoteService {

	public List<ModelData> getAsyncTree(ModelData data);
	
	public List<ModelData> getDevResourceLeftPanelAsyncTree(ModelData data);
	
	public List<ModelData> getGroupWorkerAsyncTree(ModelData data);
	
}
