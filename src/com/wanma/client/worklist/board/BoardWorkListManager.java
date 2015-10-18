package com.wanma.client.worklist.board;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class BoardWorkListManager extends Composite {

	private static WorkListManagerUiBinder uiBinder = GWT
			.create(WorkListManagerUiBinder.class);

	interface WorkListManagerUiBinder extends UiBinder<Widget, BoardWorkListManager> {
	}

	@UiField
	BoardWorkListLeftPanel boardWorkListLeftPanel;
	@UiField
	BoardWorkListManagerTable boardWorkListManagerTable;
	@UiField
	BoardWorkListDetail boardWorkListDetail;
	
	public BoardWorkListManager() {
		initWidget(uiBinder.createAndBindUi(this));
		//左侧边栏通过Table对象可以对其中数据进行变更
		boardWorkListLeftPanel.setBoardWorkListManagerTable(boardWorkListManagerTable);
		//workListLeftPanel.setWorkListDetail(workListDetail);
		//Table对象可以通过WorkListDetail对象对工单详细信息进行设置
		boardWorkListManagerTable.setBoardWorkListDetail(boardWorkListDetail);
		//workListDetail对象中设置Table对象，可以在对workListDetail中操作时更新table中数据，例如：指派工单后工单状态的变更
		boardWorkListDetail.setBoardWorkListManagerTable(boardWorkListManagerTable);
	}
}
