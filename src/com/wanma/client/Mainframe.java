package com.wanma.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.dev.OdnMap;

public class Mainframe extends Composite {

	private static MainframeUiBinder uiBinder = GWT.create(MainframeUiBinder.class);

	@UiField
	DockLayoutPanel dockLayoutPanel;
	@UiField
	SplitLayoutPanel splitLayoutPanel;
	
	@UiField
	Toppanel topPanel;
	
	interface MainframeUiBinder extends UiBinder<Widget, Mainframe> {
	}

	public Mainframe(String loginName) {
		initWidget(getDockLayoutPanel());
		topPanel.loginNameLabel.setText(loginName);
		//deviceSourceLeftPanel.setDeviceManagerTable(deviceManagerTable);
		OdnMap map = new OdnMap();
		DockLayoutPanel doc =  map.get(splitLayoutPanel);
		splitLayoutPanel.addWest(doc, 855);
		//map.drawMap();
		
	}
	
	public DockLayoutPanel getDockLayoutPanel(){
		DockLayoutPanel dock = (DockLayoutPanel)uiBinder.createAndBindUi(this);
		return dock;
	}

	public SplitLayoutPanel getSplitLayoutPanel(){
		return splitLayoutPanel;
	}
	
}
