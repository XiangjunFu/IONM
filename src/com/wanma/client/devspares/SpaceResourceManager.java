package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;

public class SpaceResourceManager extends Composite {

	private static SpaceResourceManagerUiBinder uiBinder = GWT
			.create(SpaceResourceManagerUiBinder.class);

	interface SpaceResourceManagerUiBinder extends
			UiBinder<Widget, SpaceResourceManager> {
	}

	@UiField
	SpaceResourceLeftPanel spaceResourceLeftPanel;
	@UiField
	SpaceResourceManagerTable spaceResourceManagerTable;
	@UiField
	DeviceBoardDetail deviceBoardDetail;
	
	public SpaceResourceManager() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		spaceResourceLeftPanel.setSpaceResourceManagerTable(spaceResourceManagerTable);
		//
		spaceResourceManagerTable.setDeviceBoardDetail(deviceBoardDetail);
	}

}
