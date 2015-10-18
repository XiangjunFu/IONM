package com.wanma.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.wanma.client.alarm.AlarmManager;
import com.wanma.client.dev.DeviceManager;
import com.wanma.client.dev.OdnMap;
import com.wanma.client.devspares.CableFiberLink;
import com.wanma.client.devspares.IFieldConfigManager;
import com.wanma.client.devspares.SpaceResourceManager;
import com.wanma.client.user.ODNUserManager;
import com.wanma.client.user.WorkerManager;
import com.wanma.client.worklist.board.BoardWorkListManager;
import com.wanma.client.worklist.business.BusinessWorkListManager;
import com.wanma.client.worklist.check.CheckWorkListManager;
import com.wanma.client.worklist.config.WorkListManager;
import com.wanma.client.worklist.frame.FrameWorkListManager;

public class Toppanel extends Composite {

	private static ToppanelUiBinder uiBinder = GWT
			.create(ToppanelUiBinder.class);

	@UiField
	Label loginNameLabel;

	// ============管线资源===============
	@UiField
	MenuItem planData;// 规划数据
	@UiField
	MenuItem pipelineSource;// 管线资源
	@UiField
	MenuItem lightOptical;// 光缆段
	// ===========资源管理=================
	@UiField
	MenuItem resourceView;// 资源视图
	@UiField
	MenuItem opticalTopo;// 光路拓扑
	@UiField
	MenuItem devicesTopo;// 设备拓扑
	@UiField
	MenuItem portView;// 浏览端口信息
	@UiField
	MenuItem sparePortView;// 备用端口查询
	@UiField
	MenuItem lightRouteManager;// 业务光路由管理
	@UiField
	MenuItem printDeviceLabel;// 标签打印
	// ===============设备管理================
	@UiField
	MenuItem iodnDeviceManager;// iodn设备管理
	// ============工单管理===================
	@UiField
	MenuItem businessWorkList;// 业务工单管理
	@UiField
	MenuItem configWorkList;// 配置工单管理
	@UiField
	MenuItem checkWorkList;// 巡检工单管理
	@UiField
	MenuItem boardWorkList;//
	@UiField
	MenuItem frameWorkList;

	// ===============系统====================
	@UiField
	MenuItem workerManagerItem;// 施工人员管理
	@UiField
	MenuItem iFieldManagerItem;// IField管理
	@UiField
	MenuItem systemConfigItem;// 系统配置管理
	@UiField
	MenuItem alarmManagerItem;// 日志管理
	@UiField
	MenuItem odnUserManager;// ODN用户管理

	interface ToppanelUiBinder extends UiBinder<Widget, Toppanel> {
	}

	public Toppanel() {
		initWidget(uiBinder.createAndBindUi(this));
		// 系统配置页跳转
		systemConfigItem.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				SystemConfigManager systemConfigManager = new SystemConfigManager(
						loginNameLabel.getText());
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(systemConfigManager);
				root.clear();
				root.add(mainFrame);
			}
		});
		// IField配置跳转
		iFieldManagerItem.setScheduledCommand(new ScheduledCommand() {

			@Override
			public void execute() {
				IFieldConfigManager iFieldConfigManager = new IFieldConfigManager(
						loginNameLabel.getText());
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(iFieldConfigManager);
				root.clear();
				root.add(mainFrame);
			}
		});
		// 施工人员管理页跳转
		workerManagerItem.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				WorkerManager workerManager = new WorkerManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(workerManager);
				root.clear();
				root.add(mainFrame);
			}
		});
		// ODN用户管理页跳转
		odnUserManager.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				ODNUserManager odnUserManager = new ODNUserManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(odnUserManager);
				root.clear();
				root.add(mainFrame);
			}
		});
		// 光缆段对话框
		lightOptical.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				CableFiberLink cableFiberLink = new CableFiberLink();
				cableFiberLink.center();
				cableFiberLink.show();
			}
		});

		// 资源视图
		resourceView.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				DeviceManager deviceManager = new DeviceManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(deviceManager);
				root.clear();
				root.add(mainFrame);
			}
		});

		// 设备管理同资源管理
		iodnDeviceManager.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				SpaceResourceManager spaceResourceManager = new SpaceResourceManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(spaceResourceManager);
				root.clear();
				root.add(mainFrame);
			}
		});

		// 配置工单入口
		configWorkList.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				WorkListManager workListManager = new WorkListManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(workListManager);
				root.clear();
				root.add(mainFrame);
			}
		});
		// 业务工单入口
		businessWorkList.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				BusinessWorkListManager businessWorkListManager = new BusinessWorkListManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(businessWorkListManager);
				root.clear();
				root.add(mainFrame);
			}
		});

		checkWorkList.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				CheckWorkListManager checkWorkListManager = new CheckWorkListManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(checkWorkListManager);
				root.clear();
				root.add(mainFrame);
			}
		});

		alarmManagerItem.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				AlarmManager alarmManager = new AlarmManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(alarmManager);
				root.clear();
				root.add(mainFrame);
			}
		});

		boardWorkList.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				BoardWorkListManager boardManager = new BoardWorkListManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(boardManager);
				root.clear();
				root.add(mainFrame);
			}
		});

		frameWorkList.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				FrameWorkListManager frameManager = new FrameWorkListManager();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				splitLayoutPanel.clear();
				splitLayoutPanel.add(frameManager);
				root.clear();
				root.add(mainFrame);
			}
		});
		devicesTopo.setScheduledCommand(new ScheduledCommand() {
			@Override
			public void execute() {
				OdnMap map = new OdnMap();
				RootLayoutPanel root = RootLayoutPanel.get();
				Mainframe mainFrame = (Mainframe) root.getWidget(root
						.getAbsoluteTop());
				SplitLayoutPanel splitLayoutPanel = mainFrame
						.getSplitLayoutPanel();
				DockLayoutPanel doc = map.get(splitLayoutPanel);
				splitLayoutPanel.clear();
				splitLayoutPanel.addWest(doc, 855);
				root.clear();
				root.add(mainFrame);
			}
		});
	}
}
