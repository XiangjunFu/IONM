package com.wanma.client.worklist.config;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.services.WorkListService;
import com.wanma.client.services.WorkListServiceAsync;
import com.wanma.domain.ConfigTask;
import com.wanma.resources.Resources;
import com.wanma.resources.models.Folder;
import com.wanma.resources.models.Music;

public class WorkListLeftPanel extends Composite {

	private static WorkListLeftPanelUiBinder uiBinder = GWT
			.create(WorkListLeftPanelUiBinder.class);

	interface WorkListLeftPanelUiBinder extends
			UiBinder<Widget, WorkListLeftPanel> {
	}
	
	private WorkListServiceAsync workListService = GWT.create(WorkListService.class);
	
	private WorkListManagerTable workListManagerTable = null;

	Tree tree = null;
	
	Folder root = new Folder("root");
	
	public WorkListLeftPanel() {
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
		
		Menu contextMenu = new Menu();
		MenuItem terminalUnit = new MenuItem("配置工单",
				new SelectionListener<MenuEvent>() {
					String deviceType = "";
					@Override
					public void componentSelected(MenuEvent ce) {
						ModelData data = tree.getSelectionModel().getSelectedItem();
						deviceType = data.get("name");
						if("配置工单管理".equals(deviceType)){
							return;
						}
						workListService.getAllConfigWorkList(deviceType,new AsyncCallback<List<ConfigTask>>() {
							@Override
							public void onFailure(Throwable caught) {
								Window.alert("获取工单信息失败！！！");
							}
							@Override
							public void onSuccess(List<ConfigTask> result) {
								if(result != null){
									workListManagerTable.setConfigWorkList(deviceType,result);
								}else{
									Window.alert("未获取工单信息！！！");
								}
							}
						});
					}
				});
		contextMenu.add(terminalUnit);
		MenuItem addConfigWorkList = new MenuItem("增加配置工单",new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				//选择要添加工单的设备以及类型（未配置的设备），填写（或者自动生成）工单信息（就是设备基本信息）
				//保存
				//ModelData item = tree.getSelectionModel().getSelectedItem();
				ConfigIodfOrOthDev addWorkList = new ConfigIodfOrOthDev();
				addWorkList.center();
				addWorkList.show();
			}
		});
		contextMenu.add(addConfigWorkList);
		
		tree.setContextMenu(contextMenu);
		return tree;
	}
	
	private Listener<TreePanelEvent<ModelData>> onClicklistener = new Listener<TreePanelEvent<ModelData>>() {
		String deviceType = "";
		@Override
		public void handleEvent(TreePanelEvent<ModelData> event) {
			//ModelData data = tree.getSelectionModel().getSelectedItem();
			ModelData data = event.getItem();
			deviceType = data.get("name");
			if("配置工单管理".equals(deviceType)){
				return;
			}
			workListService.getAllConfigWorkList(deviceType,new AsyncCallback<List<ConfigTask>>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("获取工单信息失败！！！");
				}
				@Override
				public void onSuccess(List<ConfigTask> result) {
					if(result != null){
						workListManagerTable.setConfigWorkList(deviceType,result);
					}else{
						Window.alert("未获取工单信息！！！");
					}
				}
			});
		}
	};
	
	@SuppressWarnings("null")
	public Folder getTreeModel(){
		Folder[] deviceTypes = null;
		Music[] types = {new Music("IODF"),new Music("IFDT"),new Music("IFAT"),new Music("OBD")};
		deviceTypes = new Folder[]{new Folder("配置工单管理",types)};
		for (int i = 0; i < deviceTypes.length; i++) {
			root.add((Folder) deviceTypes[i]);
		}
		return root;
	}

	public void setWorkListManagerTable(WorkListManagerTable workListManagerTable) {
		this.workListManagerTable = workListManagerTable;
	}
}
