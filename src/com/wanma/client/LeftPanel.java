package com.wanma.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Tree;

public class LeftPanel extends Composite {

	private static LeftPanelUiBinder uiBinder = GWT
			.create(LeftPanelUiBinder.class);
	@UiField Tree tree;

	interface LeftPanelUiBinder extends UiBinder<Widget, LeftPanel> {
	}
	
	public LeftPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

}
