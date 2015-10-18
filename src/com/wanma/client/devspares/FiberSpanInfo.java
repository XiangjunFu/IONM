package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.dev.DeviceIcon;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.domain.CableSpan;

public class FiberSpanInfo extends DialogBox {

	private static FiberSpanInfoUiBinder uiBinder = GWT
			.create(FiberSpanInfoUiBinder.class);

	interface FiberSpanInfoUiBinder extends UiBinder<Widget, FiberSpanInfo> {
	}

	private CableServiceAsync cableService = GWT.create(CableService.class);
	@UiField
	TextBox fiberSpanName;
	@UiField
	TextBox fiberSpanCode;
	@UiField
	TextBox fiberNum;
	@UiField
	TextBox length;

	@UiField
	Label aName;
	@UiField
	Label zName;
	@UiField
	Button cancel;
	@UiField
	Button save;

	public boolean addCableSpanSuccess;
	public String aDeviceCode;
	public String zDeviceCode;

	public FiberSpanInfo(DeviceIcon aDevice, DeviceIcon zDevice) {
		setWidget(uiBinder.createAndBindUi(this));
		aName.setText("A端设备名称:" + aDevice.getDescription());
		zName.setText("z端设备名称:" + zDevice.getDescription());
		aDeviceCode = aDevice.getId();
		zDeviceCode = zDevice.getId();
		addCableSpanSuccess = false;
		setGlassEnabled(true);
		setSize("388px", "300px");
	}

	@UiHandler("save")
	void onSaveClick(ClickEvent click) {
		CableSpan cableSpan = new CableSpan();
		String name = fiberSpanName.getText();
		String code = fiberSpanCode.getText();
		int fiberN = 0;
		int len = 0;
		try {
			fiberN = Integer.parseInt(fiberNum.getText());
			len = Integer.parseInt(length.getText());
		} catch (NumberFormatException e) {
			return;
		}
		if (code.equals("")) {
			return;
		}
		cableSpan.setCode(code);
		cableSpan.setName(name);
		cableSpan.setAmeCode(aDeviceCode);
		cableSpan.setZmeCode(zDeviceCode);
		cableSpan.setLength(len);
		cableSpan.setFiberNumber(fiberN);
		cableService.addCableSpan(cableSpan, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				addCableSpanSuccess = false;
				Window.alert("添加光缆缎失败！！！");
				return;
			}

			@Override
			public void onSuccess(String result) {
				if(result != null && !"".equals(result)){
					addCableSpanSuccess = true;
					hide();
				}
			}
		});
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		addCableSpanSuccess = false;
		this.hide();
	}
}
