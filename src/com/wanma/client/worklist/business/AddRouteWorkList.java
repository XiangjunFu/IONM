package com.wanma.client.worklist.business;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.client.services.BusinessWorkListServiceAsync;
import com.wanma.domain.ExecuteOrder;
import com.wanma.domain.JobOrder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.CheckBox;

public class AddRouteWorkList extends DialogBox {

	private static AddRouteWorkListUiBinder uiBinder = GWT
			.create(AddRouteWorkListUiBinder.class);
	@UiField
	Button done;
	@UiField
	Button cancel;
	@UiField
	HTML bWorkListAName;
	@UiField
	HTML bworkListZName;
	@UiField
	ListBox previewA;
	@UiField
	ListBox previewZ;
	@UiField
	TextBox routeAName;
	@UiField
	Button selectADevice;
	@UiField
	Button selectZDevice;
	@UiField
	TextBox routeZName;
	@UiField Button preAdd;
	@UiField Button remove;
	@UiField Button clear;
	@UiField CheckBox type;

	interface AddRouteWorkListUiBinder extends
			UiBinder<Widget, AddRouteWorkList> {
	}

	private BusinessWorkListServiceAsync businessListService = GWT.create(BusinessWorkListService.class);
	
	private BusinessWorkListDetail businessWorkListDetail;

	private BusinessWorkListManagerTable businessWorkListManagerTable = null;
	
	private JobOrder jobOrder = null;

	public AddRouteWorkList(
			JobOrder jobOrder,BusinessWorkListManagerTable businessWorkListManagerTable) {
		setWidget(uiBinder.createAndBindUi(this));
		
		this.jobOrder = jobOrder;
		this.businessWorkListManagerTable = businessWorkListManagerTable;
		
		bWorkListAName.setHTML(jobOrder.getAmeCode());
		bworkListZName.setHTML(jobOrder.getZmeCode());

		setSize("743px", "420px");
		setGlassEnabled(true);
		
		type.setValue(true);
		
		previewA.addClickHandler(previewAClickhandler);
		previewZ.addClickHandler(previewZClickhandler);
	}
	
	private ClickHandler previewAClickhandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			int index = previewA.getSelectedIndex();
			if(previewZ.getItemCount() == 0){
				return;
			}
			if(-1 == index){
				index = 0;
			}
			previewZ.setItemSelected(index, true);
		}
	};

	private ClickHandler previewZClickhandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			int index = previewZ.getSelectedIndex();
			if(previewZ.getItemCount() == 0){
				return;
			}
			if(-1 == index){
				index = 0;
			}
			previewA.setItemSelected(index, true);
		}
	};
	
	@UiHandler("selectADevice")
	public void onSelectADeviceClick(ClickEvent event) {
		// 选择A设备，通过“AR”标识
		SelectDevice selectADevice = new SelectDevice(this, "AR");
		selectADevice.center();
		selectADevice.show();
	}

	@UiHandler("selectZDevice")
	public void onSelectZDeviceClick(ClickEvent event) {
		// 选择Z设备，端口，通过“ZR”标识
		SelectDevice selectADevice = new SelectDevice(this, "ZR");
		selectADevice.center();
		selectADevice.show();
	}

	@UiHandler("done")
	public void onDoneClick(ClickEvent event) {
		//验证路由工单列表
		if (!verifyRouteWorkListInfo()) {
			Window.alert("产生的路由工单表有误，请检查...");
			return;
		}
		// 保存路由工单列表
		String jobOrderCode = jobOrder.getCode();
		int length = previewA.getItemCount();
		for (int i = 0; i < length; i++) {
			ExecuteOrder executeOrder = new ExecuteOrder();
			String tempPreviewA = previewA.getItemText(i);//设备名称,设备编码,板卡编码,端口编码,type
			String tempPreviewZ = previewZ.getItemText(i);//设备名称，设备编码,板卡编码,端口编码,type
			String[] apreivew = tempPreviewA.split(",");//
			String[] zpreview = tempPreviewZ.split(",");
			
			executeOrder.setName(jobOrderCode + "_" + i++);
			
			executeOrder.setJobOderCode(jobOrderCode);
			executeOrder.setAmeCode(apreivew[1]);
			executeOrder.setAcard(apreivew[2]);//板卡编码：。。/。。
			executeOrder.setAportCode(apreivew[3]);//端口编码：./././.
			
			executeOrder.setZmeCode(zpreview[1]);
			executeOrder.setZcard(zpreview[2]);
			executeOrder.setZportCode(zpreview[3]);
			
			executeOrder.setType(apreivew[4]);
			executeOrder.setStatus((short)1);
			
			businessListService.addExecuteOrder(executeOrder,addExecuteOrderCallback);
		}
		businessListService.updateJobOrderStatus(jobOrderCode,2,new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("更新业务工单状态失败！！！");
			}
			@Override
			public void onSuccess(Void result) {
			}
		});//已创建路由
		//
		if(businessWorkListManagerTable != null){
			businessWorkListManagerTable.updateBusinessWorkList();
			if(businessWorkListDetail == null){
				this.businessWorkListDetail = businessWorkListManagerTable.getBusinessWorkListDetail();
			}
		}
		//更新业务工单详细信息，业务工单的路由工单列表
		businessWorkListDetail.setJobOrder(jobOrder);
		businessListService.getAllExecuteOrderByJobOrder(jobOrder.getCode(), allExecuteOrdersCallBack);
		this.hide();
	}

	//可能用不上了，不应该在WorkListDetail对象的外边对其中的数据进行更新
	//谁拥有数据，谁就应该提供对数据的操作方法
	private AsyncCallback<List<ExecuteOrder>> allExecuteOrdersCallBack = new AsyncCallback<List<ExecuteOrder>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(List<ExecuteOrder> result) {
			if(result != null){
				businessWorkListDetail.setExecuteOrders(result);
			}
		}
	};
	//
	private AsyncCallback<String> addExecuteOrderCallback = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！！！");
		}
		@Override
		public void onSuccess(String result) {
			if(result == null){
				Window.alert("添加失败！！！");
			}
			if("existedExecuteOrder".equals(result)){
				Window.alert("部分路由工单已经创建过！！！");
			}
		}
	};
	//对路由工单预览列表进行检查
	private boolean verifyRouteWorkListInfo() {
		if (previewA.getItemCount() != previewZ.getItemCount()) {
			return false;
		}
		int length = previewA.getItemCount();
		String zDevice = previewZ.getItemText(0);
		for (int i = 1; i < length; i++) {
			String tempPreviewA = previewA.getItemText(i);
			String tempPreviewZ = previewZ.getItemText(i);
			if(!tempPreviewA.split(",")[4].equals(tempPreviewZ.split(",")[4])){
				Window.alert("工单类型有误！！！");
				return false;
			}
			if (!tempPreviewA.equals(zDevice)) {
				return false;
			}
			zDevice = tempPreviewZ;
		}
		//if (!zDevice.equals(routeZName.getText())) {
		//	return false;
		//}
		return true;
	}

	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event) {
		this.hide();
	}
	
	public void setADeviceText(String text){
		this.routeAName.setText(text);
	}
	
	public void setZDeviceText(String text){
		this.routeZName.setText(text);
	}
	
	/**
	 * @param event
	 */
	@UiHandler("preAdd")
	public void onPreAddClick(ClickEvent event){
		String routeA = this.routeAName.getText();
		String routeZ = this.routeZName.getText();
		if("".equals(routeA)||"".equals(routeZ)){
			Window.alert("请先选择设备...");
			return;
		}
		if(type.getValue()){
			
		}
		boolean typeValue = type.getValue().booleanValue();
		String str = typeValue ? ",1":",0";
		routeA = routeA + str;
		routeZ = routeZ + str;
		int countA = previewA.getItemCount();
		int countZ = previewZ.getItemCount();
		previewA.insertItem(routeA, countA);
		previewZ.insertItem(routeZ, countZ);
		this.routeAName.setText("");
		this.routeZName.setText("");
		type.setValue(true);
	}
	
	@UiHandler("remove")
	public void onRemoveClick(ClickEvent event){
		int selectedIndexA = previewA.getSelectedIndex();
		int selectedIndexZ = previewZ.getSelectedIndex();
		if(selectedIndexA == selectedIndexZ){
			previewA.removeItem(selectedIndexA);
			previewZ.removeItem(selectedIndexZ);
		}else{
			Window.alert("请确保所选中记录为同一行...");
		}
	}
	
	@UiHandler("clear")
	public void onClearClick(ClickEvent event){
		previewA.clear();
		previewZ.clear();
	}

	public void setBusinessWorkListDetail(
			BusinessWorkListDetail businessWorkListDetail) {
		this.businessWorkListDetail = businessWorkListDetail;
	}
}
