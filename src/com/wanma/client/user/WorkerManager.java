package com.wanma.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WorkerManager extends Composite {

	private static WorkerManagerUiBinder uiBinder = GWT
			.create(WorkerManagerUiBinder.class);

	@UiField
	SystemLeftPanel systemLeftPanel;
	@UiField
	UserManagerTable userManagerTable;
	
	interface WorkerManagerUiBinder extends UiBinder<Widget, WorkerManager> {
	}

	public WorkerManager() {
		initWidget(uiBinder.createAndBindUi(this));
		systemLeftPanel.setUserManagerTable(userManagerTable);
	}
}
