package com.wanma.client.worklist.config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;

public class ConfigIodfOrOthDev extends DialogBox {

	private static ConfigIodfOrOthDevUiBinder uiBinder = GWT
			.create(ConfigIodfOrOthDevUiBinder.class);
	@UiField ListBox deviodf;
	@UiField Button select;
	@UiField Button cancel;

	interface ConfigIodfOrOthDevUiBinder extends
			UiBinder<Widget, ConfigIodfOrOthDev> {
	}

	public ConfigIodfOrOthDev() {
		setWidget(uiBinder.createAndBindUi(this));
		
		deviodf.insertItem("IODF", 0);
		deviodf.insertItem("其他设备", 1);
		
		setSize("450px","200px");
		setGlassEnabled(true);
	}
	
	@UiHandler("select")
	public void onSelectClick(ClickEvent event){
		int index = deviodf.getSelectedIndex();
		switch (index) {
			case 0:
				AddIODFConfigTask addIODFConfig = new AddIODFConfigTask();
				addIODFConfig.center();
				addIODFConfig.show();
				this.hide();
				break;
			case 1:
				AddWorkListDialogBox addWorkList = new AddWorkListDialogBox();
				addWorkList.center();
				addWorkList.show();
				this.hide();
				break;
		}
	}
	
	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event){
		this.hide();
	}

}
