package com.wanma.client.user;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
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
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.services.UserService;
import com.wanma.client.services.UserServiceAsync;
import com.wanma.domain.UserOdn;
import com.wanma.resources.Resources;
import com.wanma.resources.models.Folder;
import com.wanma.resources.models.Music;

public class ODNSecurityLeftPanel extends Composite {

	private static ODNSecurityLeftPanelUiBinder uiBinder = GWT
			.create(ODNSecurityLeftPanelUiBinder.class);

	interface ODNSecurityLeftPanelUiBinder extends
			UiBinder<Widget, ODNSecurityLeftPanel> {
	}

	private Tree leftTree;
	
	private TreePanel<ModelData> tree;
	
	private UserServiceAsync userService = GWT.create(UserService.class);

	private UserManagerTable userManagerTable = null;

	//private UserManagerTable userManagerTable;

	public ODNSecurityLeftPanel() {
		leftTree = new Tree();
		initWidget(leftTree);
		updateTree();
	}
	
	void updateTree(){
		userService.getAllUsers(new AsyncCallback<List<UserOdn>>() {

			@Override
			public void onSuccess(List<UserOdn> result) {
				// System.out.println(result.size());
				leftTree.clear();
				leftTree.add(getTreePanel1(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！！！");
			}
		});
	}

	public TreePanel<ModelData> getTreePanel1(List<UserOdn> result) {
		final Folder model = getTreeModel11(result);

		final TreeStore<ModelData> store = new TreeStore<ModelData>();
		// store.add(root.getChildren(), true);
		store.add(model.getChildren(), true);

		tree = new TreePanel<ModelData>(store);

		tree.setDisplayProperty("name");
		tree.getStyle().setLeafIcon(Resources.ICONS.user());
		tree.setWidth(260);
		tree.addListener(Events.DoubleClick, doubleClickUserlistener);
		
		Menu contextMenu = new Menu();

		MenuItem passwordResetItem = new MenuItem("修改密码",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent event) {
						// 显示修改密码对话框
						ModelData data = tree.getSelectionModel().getSelectedItem();
						PasswordReset passwordReset = new PasswordReset(
								data.toString());
						passwordReset.center();
						passwordReset.show();
					}
				});
		passwordResetItem.setIcon(Resources.ICONS.user_edit());
		contextMenu.add(passwordResetItem);
		MenuItem deleteUserItem = new MenuItem("删除用户",new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent event) {
				ModelData data = tree.getSelectionModel().getSelectedItem();
				String username = data.toString();
				userService.deleteUserByUserName(username,new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("用户删除失败！");
					}
					@Override
					public void onSuccess(Boolean result) {
						if(result.booleanValue()){
							updateTree();
						}
					}
				});
			}
		});
		deleteUserItem.setIcon(Resources.ICONS.user_delete());
		contextMenu.add(deleteUserItem);
		
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
		
		tree.setContextMenu(contextMenu);
		return tree;
	}

	// 施工组菜单
	@SuppressWarnings("null")
	public Folder getTreeModel11(List<UserOdn> result) {
		// 此处应该获取施工组信息填充到施工菜单中去
		Music[] musics = null;

		if (result == null || result.size() <= 0) {
			musics = new Music[] { new Music("Error Menu") };
		} else {
			musics = new Music[result.size()];
			for (int i = 0; i < result.size(); i++) {
				UserOdn user = result.get(i);
				musics[i] = new Music(user.getUserName());
			}
		}
		Folder[] fs = new Folder[] { new Folder("用户组", musics) };

		Folder root = new Folder("root");
		for (int i = 0; i < fs.length; i++) {
			root.add((Folder) fs[i]);
		}
		return root;
	}
	public void setUserManagerTable(UserManagerTable userManagerTable) {
		this.userManagerTable  = userManagerTable;
	}
	
	private Listener<TreePanelEvent<ModelData>> doubleClickUserlistener = new Listener<TreePanelEvent<ModelData>>() {
		@Override
		public void handleEvent(TreePanelEvent<ModelData> event) {
			ModelData item = event.getItem();
			String userName = item.toString();
			userService.getUserByUserName(userName, getUserCallback);
		}
	};
	
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
}
