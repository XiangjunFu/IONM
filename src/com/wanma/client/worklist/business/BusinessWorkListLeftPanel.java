package com.wanma.client.worklist.business;

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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.client.services.BusinessWorkListServiceAsync;
import com.wanma.domain.JobOrder;
import com.wanma.resources.Resources;
import com.wanma.resources.models.Folder;


public class BusinessWorkListLeftPanel extends Composite {

	private BusinessWorkListServiceAsync businessWorkListService = GWT.create(BusinessWorkListService.class);
	
	private BusinessWorkListManagerTable businessWorkListManagerTable;
	
	private Tree tree = null;
	
	private Folder root = new Folder("root");
	
	public BusinessWorkListLeftPanel() {
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
		tree.getStyle().setLeafIcon(Resources.ICONS.form());
		tree.setAutoWidth(true);
		tree.setHeight(600);
		tree.addListener(Events.OnClick, onClicklistener);
		Menu contextMenu = new Menu();
		
		MenuItem weldConn = new MenuItem("业务工单",new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				businessWorkListService.getAllBusinessWorkList(new AsyncCallback<List<JobOrder>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("获取工单信息失败！！！");
					}
					@Override
					public void onSuccess(List<JobOrder> result) {
						if(result != null){
							businessWorkListManagerTable.setBusinessWorkList(result);
						}else{
							Window.alert("未获取到工单信息！！！");
						}
					}
				});
			}
		});
		contextMenu.add(weldConn);
		
		//业务工单添加入口
		MenuItem addBusinessWorkList = new MenuItem("增加业务工单",new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				//选择要添加工单的设备类型
				//根据跳转不同业务工单界面
				//保存
				AddBusinessWorkList addBusinessWorkList = new AddBusinessWorkList(businessWorkListManagerTable);
				addBusinessWorkList.center();
				addBusinessWorkList.show();
			}
		});
		
		MenuItem addSpecialWorkList = new MenuItem("增加路由工单",new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				//选择要添加工单的设备类型
				//根据跳转不同业务工单界面
				//保存
				AddTestRouteWorkList addTestRouteWorkList = new AddTestRouteWorkList(businessWorkListManagerTable);
				addTestRouteWorkList.center();
				addTestRouteWorkList.show();
			}
		});
		
		contextMenu.add(addSpecialWorkList);
		
		tree.setContextMenu(contextMenu);
		return tree;
	}
	
	private Listener<TreePanelEvent<ModelData>> onClicklistener = new Listener<TreePanelEvent<ModelData>>() {
		@Override
		public void handleEvent(TreePanelEvent<ModelData> be) {
			businessWorkListService.getAllBusinessWorkList(new AsyncCallback<List<JobOrder>>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("获取工单信息失败！！！");
				}
				@Override
				public void onSuccess(List<JobOrder> result) {
					if(result != null){
						businessWorkListManagerTable.setBusinessWorkList(result);
					}else{
						Window.alert("未获取到工单信息！！！");
					}
				}
			});
		}
	};
	
	@SuppressWarnings("null")
	public Folder getTreeModel(){
		Folder[] deviceTypes = new Folder[]{new Folder("业务工单管理")};
		for (int i = 0; i < deviceTypes.length; i++) {
			root.add((Folder) deviceTypes[i]);
		}
		return root;
	}

	public void setWorkListManagerTable(BusinessWorkListManagerTable businessWorkListManagerTable) {
		this.businessWorkListManagerTable = businessWorkListManagerTable;
	}
}
