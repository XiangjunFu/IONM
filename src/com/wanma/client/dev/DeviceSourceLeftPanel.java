package com.wanma.client.dev;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.data.TreeLoader;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
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
import com.wanma.client.devspares.AddJumpConnector;
import com.wanma.client.devspares.AddWeldConnector;
import com.wanma.client.devspares.DeviceCableSelect;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.services.TreeService;
import com.wanma.client.services.TreeServiceAsync;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Iodf;
import com.wanma.domain.Obd;
import com.wanma.resources.Resources;

public class DeviceSourceLeftPanel extends Composite {

	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);

	private TreeServiceAsync treeService = GWT.create(TreeService.class);

	private DeviceManagerTable deviceManagerTable;

	private TreePanel<ModelData> tree = null;

	public DeviceSourceLeftPanel() {
		initWidget(getAsyncTree());
	}

	private Menu getContextMenu(final TreePanel<ModelData> tree) {
		Menu contextMenu = new Menu();
		MenuItem terminalUnit = new MenuItem("成端",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						ModelData data = tree.getSelectionModel()
								.getSelectedItem();
						String nodeType = data.get("nodeType");
						if ("device".equalsIgnoreCase(nodeType)) {
							String deviceName = data.get("name");
							DeviceCableSelect newModule = new DeviceCableSelect(
									deviceName);
							newModule.show();
							newModule.center();
						}
					}
				});
		contextMenu.add(terminalUnit);

		MenuItem weldConn = new MenuItem("熔接",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						ModelData data = tree.getSelectionModel()
								.getSelectedItem();
						String nodeType = data.get("nodeType");
						if ("device".equalsIgnoreCase(nodeType)) {
							String deviceName = data.get("name");
							AddWeldConnector addWeldConn = new AddWeldConnector(
									deviceName);
							addWeldConn.center();
							addWeldConn.show();
						}
					}
				});
		contextMenu.add(weldConn);

		MenuItem jumpConn = new MenuItem("跳接",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						ModelData data = tree.getSelectionModel()
								.getSelectedItem();
						// String deviceName = data.get("name");
						String nodeType = data.get("nodeType");
						if ("device".equalsIgnoreCase(nodeType)) {
							AddJumpConnector addJumpConn = new AddJumpConnector();
							addJumpConn.center();
							addJumpConn.show();
						}
					}
				});
		contextMenu.add(jumpConn);

		MenuItem deviceInfos = new MenuItem("设备列表",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent event) {
						ModelData data = tree.getSelectionModel()
								.getSelectedItem();
						String nodeType = data.get("nodeType");
						if("device".equalsIgnoreCase(nodeType) || "root".equalsIgnoreCase(nodeType)){
							Window.alert("选择设备类型才能显示列表");
							return;
						}
						String itemName = data.get("name");
						if ("IODF设备".equalsIgnoreCase(itemName)) {
							opticalDeviceService
									.getIodfs(new AsyncCallback<List<Iodf>>() {
										@Override
										public void onFailure(Throwable caught) {
											Window.alert("未获取到数据！！！");
										}

										@Override
										public void onSuccess(List<Iodf> result) {
											if (result != null) {
												deviceManagerTable.deviceTable
														.removeAllRows();
												deviceManagerTable
														.updateTableIodfInfo(result);
											}
										}
									});
						} else if ("IFAT设备".equalsIgnoreCase(itemName)) {
							opticalDeviceService
									.getIfats(new AsyncCallback<List<Ifat>>() {
										@Override
										public void onFailure(Throwable caught) {
											Window.alert("未获取到数据！！！");
										}

										@Override
										public void onSuccess(List<Ifat> result) {
											if (result != null) {
												deviceManagerTable.deviceTable
														.removeAllRows();
												deviceManagerTable
														.updateTableIfatInfo(result);
											}
										}
									});
						} else if ("IFDT设备".equalsIgnoreCase(itemName)) {
							opticalDeviceService
									.getIfdts(new AsyncCallback<List<Ifdt>>() {
										@Override
										public void onFailure(Throwable caught) {
											Window.alert("未获取到数据！！！");
										}

										@Override
										public void onSuccess(List<Ifdt> result) {
											if (result != null) {
												deviceManagerTable.deviceTable
														.removeAllRows();
												deviceManagerTable
														.updateTableIfdtInfo(result);
											}
										}
									});
						} else if ("OBD设备".equalsIgnoreCase(itemName)) {
							opticalDeviceService
									.getObds(new AsyncCallback<List<Obd>>() {
										@Override
										public void onFailure(Throwable caught) {
											Window.alert("未获取到数据！！！");
										}

										@Override
										public void onSuccess(List<Obd> result) {
											if (result != null) {
												deviceManagerTable.deviceTable
														.removeAllRows();
												deviceManagerTable
														.updateTableObdInfo(result);
											}
										}
									});
						}
					}
				});
		contextMenu.add(deviceInfos);

		return contextMenu;
	}

	public void setDeviceManagerTable(DeviceManagerTable deviceManagerTable) {
		this.deviceManagerTable = deviceManagerTable;
	}

	public ContentPanel getAsyncTree() {
		ContentPanel panel = getContainer();
		RpcProxy<List<ModelData>> proxy = new RpcProxy<List<ModelData>>() {
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				treeService.getDevResourceLeftPanelAsyncTree(
						(ModelData) loadConfig, callback);
			}
		};
		TreeLoader<ModelData> loader = new BaseTreeLoader<ModelData>(proxy) {
			@Override
			public boolean hasChildren(ModelData data) {
				// 如果结点类型显示是设备，则显示无子节点
				if ("device".equals(data.get("nodeType"))) {
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
		//tree.getStyle().setLeafIcon(Resources.ICONS.accordion());
		// tree.addListener(Events.OnDoubleClick, doubleClickListener);
		tree.setCaching(false);
		tree.setContextMenu(getContextMenu(tree));
		tree.setIconProvider(new ModelIconProvider<ModelData>() {
			@Override
			public AbstractImagePrototype getIcon(ModelData model) {
				String nodeType = (String) model.get("nodeType");
				// 根据设备类型设置相应图标
				if ("root".equals(nodeType)) {
					return Resources.ICONS.accordion();
				} else if ("iodf".equalsIgnoreCase(nodeType)) {
					return Resources.ICONS.connect();
				} else if ("ifdt".equalsIgnoreCase(nodeType)) {
					return Resources.ICONS.connect();
				} else if ("ifat".equalsIgnoreCase(nodeType)) {
					return Resources.ICONS.connect();
				} else if ("obd".equalsIgnoreCase(nodeType)) {
					return Resources.ICONS.connect();
				} else if ("device".equalsIgnoreCase(nodeType)) {
					return Resources.ICONS.plugin();
				}
				return null;
			}
		});
		panel.add(tree);
		return panel;
	}

	private ContentPanel getContainer() {
		ContentPanel panel = new ContentPanel();
		panel.setAutoWidth(true);
		panel.setHeight(600);
		new Resizable(panel);
		return panel;
	}
}
