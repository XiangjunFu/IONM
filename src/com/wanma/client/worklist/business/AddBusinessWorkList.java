package com.wanma.client.worklist.business;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.client.services.BusinessWorkListServiceAsync;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.domain.JobOrder;
import com.google.gwt.user.client.ui.CheckBox;

public class AddBusinessWorkList extends DialogBox {

	private static AddBusinessWorkListUiBinder uiBinder = GWT
			.create(AddBusinessWorkListUiBinder.class);
	@UiField
	TextBox workListName;
	@UiField
	TextBox workListCode;
	@UiField
	TextBox workOrderType;
	@UiField
	TextBox customer;
	@UiField
	TextBox customerOrder;
	@UiField
	DateBox deadline;
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	TextBox businessType;
	@UiField
	TextBox businessNo;
	@UiField Button selectADevice;
	@UiField Button selectZDevice;
	@UiField TextBox aDevice;
	@UiField TextBox zDevice;
	@UiField CheckBox toCreateRouteWL;

	private final String deviceFailure = "获取设备信息失败！！！";
	private final String deviceToolTip = "未获取设备信息！！！";

	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);

	private BusinessWorkListServiceAsync businessWorkListService = GWT.create(BusinessWorkListService.class);
	
	interface AddBusinessWorkListUiBinder extends
			UiBinder<Widget, AddBusinessWorkList> {
	}

	private BusinessWorkListManagerTable businessWorkListManagerTable;
	
	//创建业务工单时
	public AddBusinessWorkList(BusinessWorkListManagerTable businessWorkListManagerTable) {
		setWidget(uiBinder.createAndBindUi(this));
		this.businessWorkListManagerTable = businessWorkListManagerTable;
		setSize("560px", "338px");
		setGlassEnabled(true);
	}	
	
	private JobOrder jobOrder = null;
	@UiHandler("save")
	public void onSaveClick(ClickEvent event) {
		jobOrder = new JobOrder();
		String jobOrderName = this.workListName.getText();
		String jobOrderCode = this.workListCode.getText();
		String workOrderType = this.workOrderType.getText();
		String customer = this.customer.getText();
		String customerOrder = this.customerOrder.getText();
		String businessType = this.businessType.getText();
		String businessNo = this.businessNo.getText();
		String aDevice = this.aDevice.getText();//设备名称,设备编码,板卡编码,端口编码
		String zDevice = this.zDevice.getText();//设备名称,设备编码,板卡编码,端口编码
		Date deadline = this.deadline.getValue();
		
		//进行输入信息验证
		
		jobOrder.setCode(jobOrderCode);
		//jobOrder.setWorkOrderType();
		jobOrder.setBusinessNo(Integer.parseInt(businessNo));
		jobOrder.setBusinessType(businessType);
		jobOrder.setCustomer(customer);
		jobOrder.setCustomerOrder(Integer.parseInt(customerOrder));
		jobOrder.setName(jobOrderName);
		jobOrder.setDeadline(deadline);
		jobOrder.setStatus((short)1);
		jobOrder.setCreateTime(new Date());
		
		String[] aDeviceInfos = aDevice.split(",");
		jobOrder.setAmeCode(aDeviceInfos[0]);//设备编码
		jobOrder.setAmeType(aDeviceInfos[1]);//设备类型
		jobOrder.setAcard(aDeviceInfos[2]);//a端板卡编码
		jobOrder.setAportCode(aDeviceInfos[3]);//端口编码
		
		String[] zDeviceInfos = zDevice.split(",");
		jobOrder.setZmeCode(zDeviceInfos[0]);//设备编码
		jobOrder.setZmeType(zDeviceInfos[1]);//设备类型
		jobOrder.setZcard(zDeviceInfos[2]);//z端板卡编码
		jobOrder.setZportCode(zDeviceInfos[3]);//端口编码
		
		businessWorkListService.addBusinessWorkList(jobOrder,addBusinessWorkListCallBack);
	}

	private AsyncCallback<Integer> addBusinessWorkListCallBack = new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！！！");
		}
		@Override
		public void onSuccess(Integer result) {
			if(result != null && result != -1){
				hide();
				if(toCreateRouteWL.getValue()){
					AddRouteWorkList addRouteWorkList = new AddRouteWorkList(jobOrder,businessWorkListManagerTable);
					addRouteWorkList.center();
					addRouteWorkList.show();
				}
				businessWorkListManagerTable.updateBusinessWorkList();
			}
		}
	};
	
	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event) {
		this.hide();
	}

	@UiHandler("selectADevice")
	public void onSelectADeviceClick(ClickEvent event){
		SelectDevice selectADevice = new SelectDevice(this,"AB");
		selectADevice.center();
		selectADevice.show();
	}
	
	@UiHandler("selectZDevice")
	public void onSelectZDeviceClick(ClickEvent event){
		SelectDevice selectZDevice = new SelectDevice(this,"ZB");
		selectZDevice.center();
		selectZDevice.show();
	}
	
	public void setADeviceText(String text){
		this.aDevice.setText(text);
	}
	
	public void setZDeviceText(String text){
		this.zDevice.setText(text);
	}
	
	/**
	 * 
	 * @param type

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
								workOrderType.setText(result.getType());
								customer.setText(result.getCode());
								customerOrder.setText(result.getName());
								businessType.setText(result.getStationCode());
								businessNo.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;
		case 2:
			opticalDeviceService.getIodfInfoByCode(paramDeviceCode,
					new AsyncCallback<Iodf>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(deviceFailure);
						}

						@Override
						public void onSuccess(Iodf result) {
							if (result != null) {
								workOrderType.setText(result.getType());
								customer.setText(result.getCode());
								customerOrder.setText(result.getName());
								businessType.setText(result.getStationCode());
								businessNo.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;
		case 3:
			opticalDeviceService.getIfatInfoByCode(paramDeviceCode,
					new AsyncCallback<Ifat>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(deviceFailure);
						}

						@Override
						public void onSuccess(Ifat result) {
							if (result != null) {
								workOrderType.setText("IFAT");
								customer.setText(result.getCode());
								customerOrder.setText(result.getName());
								businessType.setText(result.getStationCode());
								businessNo.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;
		case 4:
			opticalDeviceService.getObdInfoByCode(paramDeviceCode,
					new AsyncCallback<Obd>() {
						@Override
						public void onFailure(Throwable caught) {
							Window.alert(deviceFailure);
						}

						@Override
						public void onSuccess(Obd result) {
							if (result != null) {
								workOrderType.setText("OBD");
								customer.setText(result.getCode());
								customerOrder.setText(result.getName());
								businessType.setText(result.getStationCode());
								businessNo.setText(result.getCode());
							} else {
								Window.alert(deviceToolTip);
							}
						}
					});
			break;
		}
	}
	 */
	/**
	 * 
	 * @param index

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
			opticalDeviceService.getIodfs(new AsyncCallback<List<Iodf>>() {
				@Override
				public void onSuccess(List<Iodf> result) {
					if (result != null) {
						for (int i = 0; i < result.size(); i++) {
							Iodf iodf = result.get(i);
							deviceNameCode.insertItem(iodf.getName() + "#"
									+ iodf.getCode(), i);
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
		case 4:
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
	 */
	private void commonInit(){
		
	}
}
