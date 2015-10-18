package com.wanma.client.alarm;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class AlarmManager extends Composite {

	private static AlarmManagerUiBinder uiBinder = GWT
			.create(AlarmManagerUiBinder.class);

	interface AlarmManagerUiBinder extends UiBinder<Widget, AlarmManager> {
	}

	@UiField
	AlarmLeftPanel alarmLeftPanel;
	@UiField
	AlarmManagerTable alarmManagerTable;
	
	public AlarmManager() {
		initWidget(uiBinder.createAndBindUi(this));
		//左侧边栏通过Table对象可以对其中数据进行变更
		alarmLeftPanel.setAlarmManagerTable(alarmManagerTable);
		//workListLeftPanel.setWorkListDetail(workListDetail);
		//Table对象可以通过WorkListDetail对象对工单详细信息进行设置
	}
}
