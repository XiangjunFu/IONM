package com.wanma.client.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.wanma.domain.UserOdn;

public class UserManagerTable extends Composite {

	private static UserManagerUiBinder uiBinder = GWT
			.create(UserManagerUiBinder.class);

	interface UserManagerUiBinder extends UiBinder<Widget, UserManagerTable> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	@UiField
	FlexTable table;
	@UiField
	FlexTable header;
	@UiField
	SelectionStyle selectionStyle;

	private int selectedRow = -1;

	public UserManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));
		initTable();
		table.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				if (cell != null) {
					int index = cell.getRowIndex();
					selectRow(index);
				}
			}
		});
	}

	private void initTable() {
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "100px");
		header.getColumnFormatter().setWidth(1, "100px");
		header.getColumnFormatter().setWidth(2, "100px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "100px");
		header.getColumnFormatter().setWidth(5, "100px");

		header.setText(0, 0, "用户名称");
		header.setText(0, 1, "真实姓名");
		header.setText(0, 2, "电话号码");
		header.setText(0, 3, "施工组名");
		header.setText(0, 4, "电子邮件");
		header.setText(0, 5, "用户类型");
		// header.getCellFormatter().setHorizontalAlignment(0, 3,
		// HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		table.getColumnFormatter().setWidth(0, "100px");
		table.getColumnFormatter().setWidth(1, "100px");
		table.getColumnFormatter().setWidth(2, "100px");
		table.getColumnFormatter().setWidth(3, "100px");
		table.getColumnFormatter().setWidth(4, "100px");
		table.getColumnFormatter().setWidth(5, "100px");
	}

	public void updateUserInfo(UserOdn user) {
		table.removeAllRows();
		table.setText(0, 0, user.getUserName());
		table.setText(0, 1, user.getRealName());
		table.setText(0, 2, user.getPhoneNum());
		table.setText(0, 3, user.getGroupName());
		table.setText(0, 4, user.getEmail());
		table.setText(0, 5, user.getUserType());
		selectRow(0);
	}

	public void updateUserInfos(List<UserOdn> users) {
		table.removeAllRows();
		for (int i = 0; i < users.size(); i++) {
			UserOdn user = users.get(i);
			table.setText(i, 0, user.getUserName());
			table.setText(i, 1, user.getRealName());
			table.setText(i, 2, user.getPhoneNum());
			table.setText(i, 3, user.getGroupName());
			table.setText(i, 4, user.getEmail());
			table.setText(i, 5, user.getUserType());
		}
		selectRow(0);
	}

	// 高亮选中行
	private void selectRow(int row) {
		/**
		 * 1、去除当前选中的行的高亮 2、高亮第row行
		 */
		styleRow(selectedRow, false);
		styleRow(row, true);
		selectedRow = row;
	}

	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			String style = selectionStyle.selectedRow();

			if (selected) {
				table.getRowFormatter().addStyleName(row, style);
			} else {
				table.getRowFormatter().removeStyleName(row, style);
			}
		}
	}

}
