package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class IodnConfig extends Composite {

	private static IodnConfigUiBinder uiBinder = GWT
			.create(IodnConfigUiBinder.class);

	interface IodnConfigUiBinder extends UiBinder<Widget, IodnConfig> {
	}

	public IodnConfig() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
