package com.wanma.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UserInfo extends Composite {

	private static UserInfoUiBinder uiBinder = GWT
			.create(UserInfoUiBinder.class);

	interface UserInfoUiBinder extends UiBinder<Widget, UserInfo> {
	}

	public UserInfo() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
