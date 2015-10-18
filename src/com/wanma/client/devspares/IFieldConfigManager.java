package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class IFieldConfigManager extends Composite {

	private static IFieldConfigManagerUiBinder uiBinder = GWT
			.create(IFieldConfigManagerUiBinder.class);

	
	interface IFieldConfigManagerUiBinder extends
			UiBinder<Widget, IFieldConfigManager> {
	}
	
	public IFieldConfigManager(String loginName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
