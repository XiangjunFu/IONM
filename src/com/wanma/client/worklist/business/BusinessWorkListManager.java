package com.wanma.client.worklist.business;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BusinessWorkListManager extends Composite {

	private static WorkListManagerUiBinder uiBinder = GWT
			.create(WorkListManagerUiBinder.class);

	interface WorkListManagerUiBinder extends UiBinder<Widget, BusinessWorkListManager> {
	}

	@UiField
	BusinessWorkListLeftPanel businessWorkListLeftPanel;
	@UiField
	BusinessWorkListManagerTable businessWorkListManagerTable;
	@UiField
	BusinessWorkListDetail businessWorkListDetail;
	
	public BusinessWorkListManager() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		businessWorkListLeftPanel.setWorkListManagerTable(businessWorkListManagerTable);
		//
		businessWorkListManagerTable.setWorkListDetail(businessWorkListDetail);
		//
		businessWorkListDetail.setWorkListManagerTable(businessWorkListManagerTable);
	}
}
