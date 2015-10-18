package com.wanma.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class SystemConfigManager extends Composite {

	private static SystemConfigManagerUiBinder uiBinder = GWT
			.create(SystemConfigManagerUiBinder.class);
	@UiField 
	Button button;

	interface SystemConfigManagerUiBinder extends
			UiBinder<Widget, SystemConfigManager> {
	}

	public SystemConfigManager(String loginName) {
		initWidget(uiBinder.createAndBindUi(this));
		//topPanel.loginNameLabel.setText(loginName);
	}

	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
	}
}
