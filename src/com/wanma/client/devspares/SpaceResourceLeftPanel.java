package com.wanma.client.devspares;

import java.util.List;
import com.extjs.gxt.ui.client.data.BaseTreeLoader;
import com.extjs.gxt.ui.client.data.ModelData;
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
import com.google.gwt.user.client.ui.Composite;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.services.TreeService;
import com.wanma.client.services.TreeServiceAsync;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.District;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Station;
import com.wanma.domain.TerminalUnit;
import com.wanma.resources.Resources;

public class SpaceResourceLeftPanel extends Composite {

	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);

	private TreeServiceAsync treeService = GWT.create(TreeService.class);

	private SpaceResourceManagerTable spaceResourceManagerTable;

	private TreePanel<ModelData> tree = null;

	List<District> districts = null;
	List<Station> stations = null;
	List<HostRoom> machineRoomInfos = null;
	List<DeviceCommonInfo> deviceCommonInfos = null;

	public SpaceResourceLeftPanel() {
		initWidget(getAsyncTree());
	}

	public void setSpaceResourceManagerTable(
			SpaceResourceManagerTable spaceResourceManagerTable) {
		this.spaceResourceManagerTable = spaceResourceManagerTable;
	}

	public ContentPanel getAsyncTree() {
		ContentPanel panel = getContainer();
		RpcProxy<List<ModelData>> proxy = new RpcProxy<List<ModelData>>() {
			@Override
			protected void load(Object loadConfig,
					AsyncCallback<List<ModelData>> callback) {
				treeService.getAsyncTree((ModelData) loadConfig, callback);
			}
		};
		TreeLoader<ModelData> loader = new BaseTreeLoader<ModelData>(proxy) {
			@Override
			public boolean hasChildren(ModelData data) {
				if ("device".equals(data.get("type"))) {
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
		tree.getStyle().setLeafIcon(Resources.ICONS.accordion());
		tree.addListener(Events.OnDoubleClick, doubleClickListener);
		tree.setContextMenu(getContextMenu());
		tree.setCaching(false);
		/**
		tree.setIconProvider(new ModelIconProvider<ModelData>() {
			@Override
			public AbstractImagePrototype getIcon(ModelData model) {
				String type = (String) model.get("type");
				
				return Resources.ICONS.accordion();
			}
		});
		*/
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

	private Listener<TreePanelEvent<ModelData>> doubleClickListener = new Listener<TreePanelEvent<ModelData>>() {
		@Override
		public void handleEvent(TreePanelEvent<ModelData> event) {
			ModelData selected = event.getItem();
			String type = selected.get("type");
			if ("device".equals(type)) {
				// 通过设备名称获取设备所有端口信息
				String deviceName = selected.get("name");
				String deviceCode = selected.get("code");
				String deviceType = selected.get("deviceType");
				updateDeviceTableBoardInfo(deviceCode, deviceName, deviceType);
			}
		}
	};

	private String code = "";
	
	private void updateDeviceTableBoardInfo(String deviceCode,
			String deviceName, String deviceType) {
		this.code = deviceCode;
		opticalDeviceService.getBoardsByHostDeviceID(deviceCode,getBoards);
	}

	private AsyncCallback<List<TerminalUnit>> getBoards = new AsyncCallback<List<TerminalUnit>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！" + caught.getMessage());
		}

		@Override
		public void onSuccess(List<TerminalUnit> result) {
			if (result != null) {
				spaceResourceManagerTable.setBoards(code,result);
			} else {
				Window.alert("未获取到数据！！！");
			}
		}
	};
	
	private Menu getContextMenu() {
		Menu contextMenu = new Menu();
		// 增加区域
		MenuItem addDistrictitem = new MenuItem("增加区域",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						AddDistrictInfo addDistrict = new AddDistrictInfo();
						addDistrict.center();
						addDistrict.show();
					}
				});
		contextMenu.add(addDistrictitem);
		// 增加局站
		MenuItem addStationitem = new MenuItem("增加局站",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						StationInfo stationInfo = new StationInfo();
						stationInfo.center();
						stationInfo.show();
					}
				});
		contextMenu.add(addStationitem);
		// 增加机房
		MenuItem addRoomItem = new MenuItem("增加机房",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent event) {
						MachineRoomInfo roomInfo = new MachineRoomInfo();
						roomInfo.center();
						roomInfo.show();
					}
				});
		contextMenu.add(addRoomItem);

		// 增加光设施
		MenuItem addOpticalDevice = new MenuItem("增加光设备",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						SelectDeviceToAdd selectDeviceToAdd = new SelectDeviceToAdd();
						selectDeviceToAdd.center();
						selectDeviceToAdd.show();
					}
				});
		contextMenu.add(addOpticalDevice);
		// 增加端口
		MenuItem addPort = new MenuItem("增加端口",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent event) {
						// 添加端口
						AddPortInfo addPortInfo = new AddPortInfo(
								spaceResourceManagerTable);
						addPortInfo.center();
						addPortInfo.show();
					}
				});
		contextMenu.add(addPort);
		// 增加板卡
		MenuItem addBoard = new MenuItem("增加板卡",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						AddBoardInfo addBoardInfo = new AddBoardInfo();
						addBoardInfo.center();
						addBoardInfo.show();
					}
				});
		contextMenu.add(addBoard);
		// 增加机框
		MenuItem addFrame = new MenuItem("增加机框",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						AddFrameInfo addFrameInfo = new AddFrameInfo();
						addFrameInfo.center();
						addFrameInfo.show();
					}
				});
		contextMenu.add(addFrame);

		MenuItem terminalUnit = new MenuItem("成端",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						ModelData data = tree.getSelectionModel()
								.getSelectedItem();
						if ("device".equals(data.get("type"))) {
							String deviceName = data.get("name");
							Window.alert(deviceName);
							DeviceCableSelect newModule = new DeviceCableSelect(
									deviceName);
							newModule.show();
							newModule.center();
						}
						/*
						 * 1.新增模块（选择设备） 2.成端
						 */
					}
				});
		contextMenu.add(terminalUnit);

		MenuItem weldConn = new MenuItem("熔接",
				new SelectionListener<MenuEvent>() {
					@Override
					public void componentSelected(MenuEvent ce) {
						ModelData data = tree.getSelectionModel()
								.getSelectedItem();
						if ("device".equals(data.get("type"))) {
							String deviceName = data.toString();
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
						if ("device".equals(data.get("type"))) {
							AddJumpConnector addJumpConn = new AddJumpConnector();
							addJumpConn.center();
							addJumpConn.show();
						}
					}
				});
		contextMenu.add(jumpConn);

		return contextMenu;
	}

}
