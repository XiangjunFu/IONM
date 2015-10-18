package com.wanma.client.services;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TreeServiceAsync {

	public void getAsyncTree(ModelData data,AsyncCallback<List<ModelData>> callback);

	public void getDevResourceLeftPanelAsyncTree(ModelData loadConfig,
			AsyncCallback<List<ModelData>> callback);

	public void getGroupWorkerAsyncTree(ModelData loadConfig,
			AsyncCallback<List<ModelData>> callback);

}
