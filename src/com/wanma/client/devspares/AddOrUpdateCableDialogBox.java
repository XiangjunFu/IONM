package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.domain.Cable;

public class AddOrUpdateCableDialogBox extends DialogBox {

	private static AddOrUpdateCableDialogBoxUiBinder uiBinder = GWT
			.create(AddOrUpdateCableDialogBoxUiBinder.class);
	@UiField
	TextBox cableName;
	@UiField
	TextBox cableCode;
	@UiField
	TextBox assemblyName;
	@UiField
	TextBox assemblyCode;
	@UiField
	DateBox createTime;
	@UiField
	DateBox reviseTime;
	@UiField
	ListBox topology;
	@UiField
	ListBox cableLevel;
	@UiField
	TextBox notes;
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	Button update;

	private CellTable<Cable> cableTable;
	
	private CableServiceAsync cableService = GWT.create(CableService.class);

	interface AddOrUpdateCableDialogBoxUiBinder extends
			UiBinder<Widget, AddOrUpdateCableDialogBox> {
	}

	public AddOrUpdateCableDialogBox(CellTable<Cable> cableGrid) {
		setWidget(uiBinder.createAndBindUi(this));
		update.setVisible(false);
		topology.insertItem("topo00", 0);
		topology.insertItem("topo01", 1);
		cableLevel.insertItem("level00", 0);
		cableLevel.insertItem("level01", 1);
		
		this.cableTable = cableGrid;
		
		setSize("608px", "370px");
		setGlassEnabled(true);
	}

	public AddOrUpdateCableDialogBox(String name, CellTable<Cable> cableGrid) {
		setWidget(uiBinder.createAndBindUi(this));
		topology.insertItem("topo00", 0);
		topology.insertItem("topo01", 1);
		cableLevel.insertItem("level00", 0);
		cableLevel.insertItem("level01", 1);
		save.setVisible(false);
		cableService.getCableByCableName(name, new AsyncCallback<Cable>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("未获取到信息！");
			}

			@Override
			public void onSuccess(Cable result) {
				if (result != null) {
					cableName.setText(result.getName());
					cableName.setEnabled(false);
					cableCode.setText(result.getCode());
					cableCode.setEnabled(false);
					assemblyName.setText(result.getAssemblyName());
					assemblyCode.setText(result.getAssemblyNumber());
					createTime.setValue(result.getCreateTime());
					reviseTime.setValue(result.getReviseTime());
					notes.setText(result.getNotes());

					int index = getListBoxIndexByItemtext(topology,
							result.getTopology());
					topology.setSelectedIndex(index);
					index = getListBoxIndexByItemtext(cableLevel,
							result.getCableType());
					cableLevel.setSelectedIndex(index);
				}
			}
		});
		
		this.cableTable = cableGrid;
		setSize("608px", "370px");
		setGlassEnabled(true);
	}

	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		if (verifyEmpty(cableCode.getText())
				|| verifyEmpty(cableName.getText())
				|| verifyEmpty(cableLevel.getItemText(cableLevel
						.getSelectedIndex()))) {
			Window.alert("字段不能为空，请检查...");
			return;
		}
		Cable cable = new Cable();

		cable.setAssemblyName(assemblyName.getText());
		cable.setAssemblyNumber(assemblyCode.getText());
		cable.setCableType(cableLevel.getItemText(cableLevel.getSelectedIndex()));
		cable.setCode(cableCode.getText());
		cable.setCreateTime(createTime.getValue());
		cable.setReviseTime(reviseTime.getValue());
		cable.setName(cableName.getText());
		cable.setNotes(notes.getText());
		cable.setTopology(topology.getItemText(topology.getSelectedIndex()));
		// if ("保存".equals(this.save.getText())) {
		cableService.addCable(cable, new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if (result != null || !"".equals(result)) {
					Window.alert("光缆添加成功!");
					updateCableTable();
					hide();
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("光缆添加失败！");
				return;
			}
		});
		// }
		/*
		 * else if ("更新".equals(this.save.getText())) {
		 * 
		 * }
		 */
	}

	@UiHandler("update")
	void onUpdateClick(ClickEvent event) {
		if (verifyEmpty(cableCode.getText())
				|| verifyEmpty(cableName.getText())
				|| verifyEmpty(cableLevel.getItemText(cableLevel
						.getSelectedIndex()))) {
			Window.alert("字段不能为空，请检查...");
			return;
		}
		Cable cable = new Cable();

		cable.setAssemblyName(assemblyName.getText());
		cable.setAssemblyNumber(assemblyCode.getText());
		cable.setCableType(cableLevel.getItemText(cableLevel.getSelectedIndex()));
		cable.setCode(cableCode.getText());
		cable.setCreateTime(createTime.getValue());
		cable.setReviseTime(reviseTime.getValue());
		cable.setName(cableName.getText());
		cable.setNotes(notes.getText());
		cable.setTopology(topology.getItemText(topology.getSelectedIndex()));
		cableService.updateCable(cable, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("修改失败！");
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("修改成功！");
				updateCableTable();
				hide();
			}
		});
	}

	@UiHandler("cancel")
	void onCancel(ClickEvent event) {
		this.hide();
	}

	private boolean verifyEmpty(String text) {
		return text == null || "".equals(text);
	}

	private int getListBoxIndexByItemtext(ListBox listBox, String text) {
		for (int i = 0; i < listBox.getItemCount(); i++) {
			if (listBox.getItemText(i).equals(text)) {
				return i;
			}
		}
		return -1;
	}
	
	private void updateCableTable(){
		cableService.getCables(new AsyncCallback<List<Cable>>() {
			@Override
			public void onSuccess(List<Cable> result) {
				if (result == null) {
					return;
				}
				// 为表格添加数据：数据列表List类型
				cableTable.setRowCount(result.size(), true);
				cableTable.setRowData(0, result);// 从0开始添加数据
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取光缆信息失败！");
				return;
			}
		});
	}
}
