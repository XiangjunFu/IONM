package com.wanma.client.user;


import java.util.List;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.fx.Resizable;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.wanma.client.services.GroupService;
import com.wanma.client.services.GroupServiceAsync;
import com.wanma.client.services.TreeService;
import com.wanma.client.services.TreeServiceAsync;
import com.wanma.client.services.UserService;
import com.wanma.client.services.UserServiceAsync;
import com.wanma.domain.UserOdn;
import com.wanma.resources.Resources;

/**
 * 施工人员管理的左侧菜单：
 * 施工组
 * 	  +
 * 施工人员
 * 	  +
 */
public class SystemLeftPanel extends Composite {

	private GroupServiceAsync groupService = GWT.create(GroupService.class);
	
	private UserServiceAsync userService = GWT.create(UserService.class);
	
	private TreeServiceAsync treeService = GWT.create(TreeService.class);
	
	private UserManagerTable userManagerTable = null;
	
	TreePanel<ModelData> tree = null;
	
	public SystemLeftPanel() {
		initWidget(getAsyncTree());
	}

	public ContentPanel getAsyncTree() {
		ContentPanel panel = getContainer();
		RpcProxy<List<ModelData>> proxy = new RpcProxy<List<ModelData>>() {
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				treeService.getGroupWorkerAsyncTree((ModelData) loadConfig, callback);
			}
		};
		TreeLoader<ModelData> loader = new BaseTreeLoader<ModelData>(proxy) {
			@Override
			public boolean hasChildren(ModelData data) {
				if ("user".equals(data.get("nodeType"))) {
					return false;
				}
				return true;
			}
		};
		TreeStore<ModelData> store = new TreeStore<ModelData>(loader);
		store.setStoreSorter(new StoreSorter<ModelData>() {
			@Override
			public int compare(Store<ModelData> store, ModelData m1,
					ModelData m2, String property) {
				return 1;
			}
		});

		tree = new TreePanel<ModelData>(store);
		tree.setDisplayProperty("name");
		tree.addListener(Events.OnClick, doubleClickListener);
		tree.setContextMenu(getGroupWorkerContextMenu());
		tree.setCaching(false);
		tree.setIconProvider(new ModelIconProvider<ModelData>() {
			@Override
			public AbstractImagePrototype getIcon(ModelData model) {
				String type = (String) model.get("nodeType");
				if("root".equals(type)){
					return Resources.ICONS.home();
				}else if("group".equals(type)){
					return Resources.ICONS.group();
				}else if("user".equals(type)){
					return Resources.ICONS.user();
				}else{
					return Resources.ICONS.accordion();
				}
			}
		});
		panel.add(tree);
		return panel;
	}

	private Listener<TreePanelEvent<ModelData>> doubleClickListener = new Listener<TreePanelEvent<ModelData>>(){
		@Override
		public void handleEvent(TreePanelEvent<ModelData> event) {
			ModelData item = tree.getSelectionModel().getSelectedItem();
			String nodeType = item.get("nodeType");
			if("group".equals(nodeType)){
				String groupName = item.get("name");
				groupService.getUsersByGroup(groupName,new AsyncCallback<List<UserOdn>>(){
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("获取信息失败！！！");
					}
					@Override
					public void onSuccess(List<UserOdn> users) {
						if (users != null) {
							userManagerTable.updateUserInfos(users);
						}else{
							Window.alert("用户信息获取失败！！");
						}
					}
				});
			}else if("user".equals(nodeType)){
				String userName = item.get("name");
				userService.getUserByUserName(userName, getUserCallback);
			}
		}
	};
	//施工班组
	private Menu getGroupWorkerContextMenu() {
		Menu contextMenu = new Menu();
		MenuItem addGroupItem = new MenuItem("增加施工组",new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent event) {
				//显示添加施工组对话框
				GroupAdd groupAdd = new GroupAdd();
				groupAdd.center();
				groupAdd.show();
			}
		});
		addGroupItem.setIcon(Resources.ICONS.group_add());
		contextMenu.add(addGroupItem);
		
		MenuItem deleteItem = new MenuItem("删除施工组",new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent event) {
				ModelData data = tree.getSelectionModel().getSelectedItem();
				String name = data.get("name");
				groupService.deleteGroup(name,deleteGroup);
			}
		});
		deleteItem.setIcon(Resources.ICONS.group_delete());
		contextMenu.add(deleteItem);
		
		MenuItem addWorkeritem = new MenuItem("增加施工人员",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent event) {
						//MenuItem item = (MenuItem) ce.getItem();
						UserAdd userAdd = new UserAdd();
						userAdd.center();
						userAdd.show();
					}
				});
		addWorkeritem.setIcon(Resources.ICONS.user_add());
		//增加施工人员右键菜单
		contextMenu.add(addWorkeritem);
		
		MenuItem deleteWorkeritem = new MenuItem("删除施工人员",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent event) {
						ModelData data = tree.getSelectionModel().getSelectedItem();
						String username = data.get("name");
						userService.deleteUserByUserName(username, deleteUserCallback);
					}
				});
		deleteWorkeritem.setIcon(Resources.ICONS.user_delete());
		//增加施工人员右键菜单
		contextMenu.add(deleteWorkeritem);
		
		return contextMenu;
	}

	public void setUserManagerTable(UserManagerTable userManagerTable){
		this.userManagerTable = userManagerTable;
	}
	
	private AsyncCallback<UserOdn> getUserCallback = new AsyncCallback<UserOdn>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取用户失败！！！");
		}
		@Override
		public void onSuccess(UserOdn result) {
			if(result != null){
				userManagerTable.updateUserInfo(result);
			}
		}
	};
	
	private AsyncCallback<Boolean> deleteGroup = new AsyncCallback<Boolean>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("删除失败！！！");
		}
		@Override
		public void onSuccess(Boolean result) {
			if(!result.booleanValue()){
				Window.alert("删除失败！！！");
			}
		}
	};
	
	private AsyncCallback<Boolean> deleteUserCallback = new AsyncCallback<Boolean>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("用户删除失败！！！");
		}
		@Override
		public void onSuccess(Boolean result) {
			if(!result.booleanValue()){
				Window.alert("用户删除失败！！！");
			}
		}
	};
	
	private ContentPanel getContainer() {
		ContentPanel panel = new ContentPanel();
		panel.setAutoWidth(true);
		panel.setHeight(600);
		new Resizable(panel);

		return panel;
	}
}
