package com.wanma.client.alarm;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.wanma.client.services.CommonService;
import com.wanma.client.services.CommonServiceAsync;
import com.wanma.domain.AlarmLog;
import com.wanma.resources.Resources;
import com.wanma.resources.models.Folder;
import com.wanma.resources.models.Music;

public class AlarmLeftPanel extends Composite {

	private CommonServiceAsync commonService = GWT.create(CommonService.class);
	
	private AlarmManagerTable alarmManagerTable = null;

	Tree tree = null;
	
	Folder root = new Folder("root");
	
	public AlarmLeftPanel() {
		tree = new Tree();
		root = getTreeModel();
		tree.add(getTreePanel2(root));
		initWidget(tree);
	}

	public TreePanel<ModelData> getTreePanel2(Folder model) {
		final TreeStore<ModelData> store = new TreeStore<ModelData>();
		store.add(model.getChildren(), true);

		final TreePanel<ModelData> tree = new TreePanel<ModelData>(store);
		
		tree.setDisplayProperty("name");
		tree.getStyle().setLeafIcon(Resources.ICONS.accordion());
		tree.setAutoWidth(true);
		tree.setHeight(600);
		
		tree.addListener(Events.OnClick, onClicklistener);
		
		return tree;
	}
	
	private Listener<TreePanelEvent<ModelData>> onClicklistener = new Listener<TreePanelEvent<ModelData>>() {
		String deviceType = "";
		@Override
		public void handleEvent(TreePanelEvent<ModelData> event) {
			//ModelData data = tree.getSelectionModel().getSelectedItem();
			ModelData data = event.getItem();
			deviceType = data.get("name");
			if("告警信息管理".equals(deviceType)){
				return;
			}
			commonService.getAlarmLogInfoByDeviceType(deviceType,new AsyncCallback<List<AlarmLog>>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("获取告警信息失败！！！");
				}
				@Override
				public void onSuccess(List<AlarmLog> result) {
					if(result != null){
						alarmManagerTable.setAlarmInfoList(deviceType,result);
					}else{
						Window.alert("未获取到告警信息！！！");
					}
				}
			});
		}
	};
	
	@SuppressWarnings("null")
	public Folder getTreeModel(){
		Folder[] deviceTypes = null;
		Music[] types = {new Music("IODF"),new Music("IFDT"),new Music("IFAT"),new Music("OBD")};
		deviceTypes = new Folder[]{new Folder("告警信息管理",types)};
		for (int i = 0; i < deviceTypes.length; i++) {
			root.add((Folder) deviceTypes[i]);
		}
		return root;
	}

	public void setAlarmManagerTable(AlarmManagerTable alarmManagerTable) {
		this.alarmManagerTable = alarmManagerTable;
	}
}
