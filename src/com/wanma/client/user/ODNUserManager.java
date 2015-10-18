package com.wanma.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ODNUserManager extends Composite {

	private static ODNUserManagerUiBinder uiBinder = GWT
			.create(ODNUserManagerUiBinder.class);

	interface ODNUserManagerUiBinder extends UiBinder<Widget, ODNUserManager> {
	}

	@UiField
	ODNSecurityLeftPanel odnSecurityLeftPanel;
	
	@UiField
	UserManagerTable userManagerTable;
	
	public ODNUserManager() {
		initWidget(uiBinder.createAndBindUi(this));
		odnSecurityLeftPanel.setUserManagerTable(userManagerTable);
	}
}
