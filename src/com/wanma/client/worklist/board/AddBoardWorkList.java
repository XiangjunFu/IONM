package com.wanma.client.worklist.board;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.BoardWorkListService;
import com.wanma.client.services.BoardWorkListServiceAsync;
import com.wanma.client.services.CheckWorkListService;
import com.wanma.client.services.CheckWorkListServiceAsync;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.BoardTask;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Obd;
import com.wanma.domain.RouteCheckTask;
import com.google.gwt.user.client.ui.CheckBox;

public class AddBoardWorkList extends DialogBox {

	private static AddWorkListDialogBoxUiBinder uiBinder = GWT
			.create(AddWorkListDialogBoxUiBinder.class);
	@UiField
	ListBox deviceType;
	@UiField
	ListBox deviceNameCode;
	@UiField
	TextBox workListName;
	@UiField
	TextBox workListCode;
	@UiField
	TextBox deviceType2;
	@UiField
	TextBox deviceCode;
	@UiField
	TextBox deviceName;
	@UiField
	DateBox deadline;
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	TextBox stationCode;
	@UiField
	TextBox hostRoomCode;
	@UiField
	TextBox address;
	@UiField ListBox boardType;
	@UiField TextBox boardNum;
	@UiField CheckBox opType;

	private final String deviceFailure = "获取设备信息失败！！！";
	private final String deviceToolTip = "未获取设备信息！！！";

	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);

	private BoardWorkListServiceAsync boardWorkListService = GWT.create(BoardWorkListService.class);
	
	interface AddWorkListDialogBoxUiBinder extends
			UiBinder<Widget, AddBoardWorkList> {
	}

	//创建配置工单时
	public AddBoardWorkList() {
		setWidget(uiBinder.createAndBindUi(this));

		setSize("560px", "338px");
		setGlassEnabled(true);
		
		commonInit();
		
		deviceType.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int index = deviceType.getSelectedIndex();
				if (0 == index) {
					Window.alert("请选择设备类型...");
					return;
				}
				/**
				 * 将所选类型设备在列表列出
				 */
				getDevicesByTypeIndex(index);
			}
		});

		deviceNameCode.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int type = deviceType.getSelectedIndex();
				String deviceNameAndCode = deviceNameCode
						.getItemText(deviceNameCode.getSelectedIndex());
				//String deviceName = deviceNameAndCode.split("#")[0];
				String deviceCode = deviceNameAndCode.split("#")[1];
				updateWorkListInfo(type, deviceCode);
			}
		});
	}
	
	////添加Ifdt时创建配置工单时构造函数
	public AddBoardWorkList(String deviceCode){
		setWidget(uiBinder.createAndBindUi(this));

		setSize("560px", "338px");
		setGlassEnabled(true);
		
		commonInit();
		
		opticalDeviceService.getDeviceCommonInfosByDeviceCode(deviceCode, deviceCommonInfoCallback);
	}
	
	private AsyncCallback<DeviceCommonInfo> deviceCommonInfoCallback = new AsyncCallback<DeviceCommonInfo>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(DeviceCommonInfo device) {
			if(device != null){
				deviceNameCode.insertItem(device.getDeviceName() + "#" + device.getDeviceCode(),0);
				deviceNameCode.setEnabled(false);
				deviceType2.setText(device.getDeviceType());
				deviceCode.setText(device.getDeviceCode());
				deviceName.setText(device.getDeviceName());
				stationCode.setText(device.getStationCode());
				hostRoomCode.setText(device.getDeviceCode());
			}
		}
	};
	
	@UiHandler("save")
	public void onSaveClick(ClickEvent event) {
		// 保存工单信息
		String tempWorkListCode = workListCode.getText();
		String tempWorkListName = workListName.getText();
		if(!StringVerifyUtils.verifyEmpty(tempWorkListCode) || !StringVerifyUtils.verifyEmpty(tempWorkListCode)){
			Window.alert("部分字段不能为空！！！");
			return;
		}
		
		BoardTask boardTask = new BoardTask();
		boardTask.setCode(tempWorkListCode);
		boardTask.setName(tempWorkListName);
		boardTask.setCreatetime(new Date());
		boardTask.setDeviceCode(this.deviceCode.getText());
		boardTask.setDeviceName(this.deviceName.getText());
		boardTask.setDeviceType(this.deviceType2.getText());
		boardTask.setStatus(1);//预配置
		
		boardTask.setBoardNum(boardNum.getText());
		boardTask.setBoardType(boardType.getSelectedIndex()+"");
		boolean type = opType.getValue().booleanValue();
		boardTask.setOpType(type?"1":"2");
		
		boardWorkListService.addBoardTask(boardTask,new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("保存失败！！！");
			}
			@Override
			public void onSuccess(Integer result) {
				if(result != null && result != -1){
					hide();
				}else{
					Window.alert("保存失败！！！");
				}
			}
		});
	}

	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event) {
		this.hide();
	}

	/**
	 * 
	 * @param type
	 */
	private void updateWorkListInfo(int type, String paramDeviceCode) {
		switch (type) {
		case 1:
			opticalDeviceService.getIfdtByCode(paramDeviceCode,
					new AsyncCallback<Ifdt>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(deviceFailure);
						}

						@Override
						public void onSuccess(Ifdt result) {
							if (result != null) {
								deviceType2.setText(result.getType());
								deviceCode.setText(result.getCode());
								deviceName.setText(result.getName());
								stationCode.setText(result.getStationCode());
								hostRoomCode.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;
/*		case 2:
			opticalDeviceService.getIodfInfoByCode(paramDeviceCode,
					new AsyncCallback<Iodf>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(deviceFailure);
						}

						@Override
						public void onSuccess(Iodf result) {
							if (result != null) {
								deviceType2.setText(result.getType());
								deviceCode.setText(result.getCode());
								deviceName.setText(result.getName());
								stationCode.setText(result.getStationCode());
								hostRoomCode.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;*/
		case 2:
			opticalDeviceService.getIfatInfoByCode(paramDeviceCode,
					new AsyncCallback<Ifat>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(deviceFailure);
						}

						@Override
						public void onSuccess(Ifat result) {
							if (result != null) {
								deviceType2.setText("IFAT");
								deviceCode.setText(result.getCode());
								deviceName.setText(result.getName());
								stationCode.setText(result.getStationCode());
								hostRoomCode.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;
		case 3:
			opticalDeviceService.getObdInfoByCode(paramDeviceCode,
					new AsyncCallback<Obd>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(deviceFailure);
						}

						@Override
						public void onSuccess(Obd result) {
							if (result != null) {
								deviceType2.setText("OBD");
								deviceCode.setText(result.getCode());
								deviceName.setText(result.getName());
								stationCode.setText(result.getStationCode());
								hostRoomCode.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;
		}
	}

	/**
	 * 
	 * @param index
	 */
	private void getDevicesByTypeIndex(int index) {
		switch (index) {
		case 1:
			deviceNameCode.clear();
			opticalDeviceService.getIfdts(new AsyncCallback<List<Ifdt>>() {
				@Override
				public void onSuccess(List<Ifdt> result) {
					if (result != null) {
						for (int i = 0; i < result.size(); i++) {
							Ifdt ifdt = result.get(i);
							deviceNameCode.insertItem(ifdt.getName() + "#"
									+ ifdt.getCode(), i);
						}
					} else {
						Window.alert(deviceToolTip);
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(deviceFailure);
				}
			});
			break;
		case 2:
			deviceNameCode.clear();
			opticalDeviceService.getIfats(new AsyncCallback<List<Ifat>>() {
				@Override
				public void onSuccess(List<Ifat> result) {
					if (result != null) {
						for (int i = 0; i < result.size(); i++) {
							Ifat ifat = result.get(i);
							deviceNameCode.insertItem(ifat.getName() + "#"
									+ ifat.getCode(), i);
						}
					} else {
						Window.alert(deviceToolTip);
					}
				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(deviceFailure);
				}
			});
			break;
		case 3:
			deviceNameCode.clear();
			opticalDeviceService.getObds(new AsyncCallback<List<Obd>>() {
				@Override
				public void onSuccess(List<Obd> result) {
					if (result != null) {
						for (int i = 0; i < result.size(); i++) {
							Obd obd = result.get(i);
							deviceNameCode.insertItem(
									obd.getName() + "#" + obd.getCode(), i);
						}
					} else {
						Window.alert(deviceToolTip);
					}
				}
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(deviceFailure);
				}
			});
			break;
		}
	}

	private void commonInit(){
		deviceType.insertItem("", 0);
		deviceType.insertItem("光纤交接箱(IFDT)", 1);
		deviceType.insertItem("光分纤盒(IFAT)", 2);
		deviceType.insertItem("分光器(OBD)", 3);

		deviceType2.setEnabled(false);
		deviceCode.setEnabled(false);
		deviceName.setEnabled(false);
		stationCode.setEnabled(false);
		hostRoomCode.setEnabled(false);
		
	}
}
