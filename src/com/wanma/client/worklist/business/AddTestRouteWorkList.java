package com.wanma.client.worklist.business;

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
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.client.services.BusinessWorkListServiceAsync;
import com.wanma.domain.ExecuteOrder;
import com.google.gwt.user.client.ui.CheckBox;

public class AddTestRouteWorkList extends DialogBox {

	private static AddTestRouteWorkListUiBinder uiBinder = GWT
			.create(AddTestRouteWorkListUiBinder.class);
	@UiField
	TextBox zRoute;
	@UiField
	TextBox aRoute;
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	Button selectA;
	@UiField
	Button selectZ;
	@UiField CheckBox opType;

	private BusinessWorkListServiceAsync businessListService = GWT.create(BusinessWorkListService.class);
	
	interface AddTestRouteWorkListUiBinder extends
			UiBinder<Widget, AddTestRouteWorkList> {
	}

	private BusinessWorkListManagerTable businessWorkListManagerTable = null;
	
	public AddTestRouteWorkList(BusinessWorkListManagerTable businessWorkListManagerTable) {
		setWidget(uiBinder.createAndBindUi(this));
		
		this.businessWorkListManagerTable = businessWorkListManagerTable;
		
		setSize("450px", "196px");
		setGlassEnabled(true);
	}

	@UiHandler("save")
	public void onSaveClick(ClickEvent event) {
		ExecuteOrder executeOrder = new ExecuteOrder();
		String tempAroute = aRoute.getText();// 设备名称,设备编码,板卡编码,端口编码,type
		String tempZroute = zRoute.getText();// 设备名称，设备编码,板卡编码,端口编码,type
		String[] apreivew = tempAroute.split(",");//
		String[] zpreview = tempZroute.split(",");

		executeOrder.setJobOderCode("special");
		executeOrder.setAmeCode(apreivew[1]);
		executeOrder.setAcard(apreivew[2]);// 板卡编码：。。/。。
		executeOrder.setAportCode(apreivew[3]);// 端口编码：./././.

		executeOrder.setZmeCode(zpreview[1]);
		executeOrder.setZcard(zpreview[2]);
		executeOrder.setZportCode(zpreview[3]);

		boolean op = opType.getValue().booleanValue();
		executeOrder.setType(op?"1":"2");
		executeOrder.setStatus((short) 1);

		businessListService.addExecuteOrder(executeOrder,
				addExecuteOrderCallback);
	}

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
				Window.alert("路由工单已经创建过！！！");
			}
			businessWorkListManagerTable.updateBusinessWorkList();
			hide();
		}
	};
	
	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event) {
		this.hide();
	}

	@UiHandler("selectA")
	public void onSelectAClick(ClickEvent event) {
		SelectDevice selectADev = new SelectDevice(this, "TESTA");
		selectADev.center();
		selectADev.show();
	}

	@UiHandler("selectZ")
	public void onSelectZClick(ClickEvent event) {
		SelectDevice selectZDev = new SelectDevice(this, "TESTZ");
		selectZDev.center();
		selectZDev.show();
	}

	public void setARouteText(String text) {
		this.aRoute.setText(text);
	}

	public void setZRouteText(String text) {
		this.zRoute.setText(text);
	}
}
