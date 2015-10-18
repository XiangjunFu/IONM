package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;

public class SelectDeviceToAdd extends DialogBox {

	private static SelectDeviceToAddUiBinder uiBinder = GWT
			.create(SelectDeviceToAddUiBinder.class);
	@UiField
	ListBox selectedDevice;
	@UiField
	Button submit;
	@UiField
	Button cancel;

	interface SelectDeviceToAddUiBinder extends
			UiBinder<Widget, SelectDeviceToAdd> {
	}

	private IFDTInfo ifdt = null;

	private IODFInfo iodf = null;

	private IFATInfo ifat = null;

	private OBDInfo obd = null;

	public SelectDeviceToAdd() {
		setWidget(uiBinder.createAndBindUi(this));

		selectedDevice.insertItem("光纤交接箱(IFDT)", 0);
		selectedDevice.insertItem("光纤配线架(IODF)", 1);
		selectedDevice.insertItem("光分纤盒(IFAT)", 2);
		selectedDevice.insertItem("分光器(OBD)", 3);

		setText("请选择要添加的设备...");
		setSize("400px", "200px");
		setGlassEnabled(true);
	}

	@UiHandler("submit")
	void onSubmitClick(ClickEvent event) {
		int selectedIndex = selectedDevice.getSelectedIndex();
		switch (selectedIndex) {
		case 0:
			// Window.alert("0" + selectedDevice.getItemText(0));
			if (ifdt == null) {
				ifdt = new IFDTInfo();
			}
			ifdt.center();
			ifdt.show();
			this.hide();
			break;
		case 1:
			if (iodf == null) {
				iodf = new IODFInfo();
			}
			iodf.center();
			iodf.show();
			this.hide();
			break;
		case 2:
			if (ifat == null) {
				ifat = new IFATInfo();
			}
			ifat.center();
			ifat.show();
			this.hide();
			break;
		case 3:
			if (obd == null) {
				obd = new OBDInfo();
			}
			obd.center();
			obd.show();
			this.hide();
			break;
		}
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}
}
