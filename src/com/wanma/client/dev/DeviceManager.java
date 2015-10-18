package com.wanma.client.dev;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DeviceManager extends Composite {

	private static DeviceManagerUiBinder uiBinder = GWT
			.create(DeviceManagerUiBinder.class);

	interface DeviceManagerUiBinder extends UiBinder<Widget, DeviceManager> {
	}

	@UiField
	DeviceSourceLeftPanel deviceSourceLeftPanel;
	
	@UiField
	DeviceManagerTable deviceManagerTable;
	
	public DeviceManager() {
		initWidget(uiBinder.createAndBindUi(this));
		deviceSourceLeftPanel.setDeviceManagerTable(deviceManagerTable);
	}
}
