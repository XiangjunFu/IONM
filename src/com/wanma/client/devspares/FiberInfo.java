package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.domain.Fiber;

public class FiberInfo extends DialogBox {

	private static FiberInfoUiBinder uiBinder = GWT
			.create(FiberInfoUiBinder.class);
	@UiField
	TextBox fiberName;
	@UiField
	TextBox fiberCode;
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	Button update;
	@UiField
	TextBox logicalNo;
	@UiField
	TextBox fiberSque;
	@UiField
	TextBox length;
	@UiField
	TextBox spectrum;
	@UiField
	ListBox aMeType;
	@UiField
	ListBox zMeType;
	@UiField
	TextBox aMeCode;
	@UiField
	TextBox zMeCode;
	@UiField
	TextBox aPortCode;
	@UiField
	TextBox zPortCode;
	@UiField
	TextBox serviceStatue;

	String fiberPartCode = "";
	private CellTable<Fiber> fiberTable;
	private CableServiceAsync cableService = GWT.create(CableService.class);

	interface FiberInfoUiBinder extends UiBinder<Widget, FiberInfo> {
	}

	public FiberInfo(String fiberPartCode, CellTable<Fiber> fiberTable) {
		setWidget(uiBinder.createAndBindUi(this));
		update.setVisible(false);
		this.fiberPartCode = fiberPartCode;
		aMeType.insertItem("0", 0);
		aMeType.insertItem("1", 1);

		zMeType.insertItem("0", 0);
		zMeType.insertItem("1", 1);

		this.fiberTable = fiberTable;
		setSize("538px", "359px");
		setGlassEnabled(true);
	}

	public FiberInfo(String code, String update, CellTable<Fiber> fiberTable) {
		setWidget(uiBinder.createAndBindUi(this));
		save.setVisible(false);
		cableService.getFiberByFiberCode(code, new AsyncCallback<Fiber>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("未能获取到纤芯信息！");
			}

			@Override
			public void onSuccess(Fiber f) {
				if (f != null) {
					fiberName.setText(f.getName());
					fiberCode.setText(f.getCode());
					logicalNo.setText(f.getLogicalNo() + "");
					length.setText(f.getLength() + "");
					fiberSque.setText(f.getFiberSque() + "");
					spectrum.setText(f.getSpectrum());
					int index = getListBoxIndexByItemtext(aMeType,
							f.getAmeType());
					aMeType.setSelectedIndex(index);
					index = getListBoxIndexByItemtext(zMeType, f.getZmeType());
					zMeType.setSelectedIndex(index);

					aMeCode.setText(f.getAmeCode());
					aPortCode.setText(f.getAportCode());

					zMeCode.setText(f.getZmeCode());
					zPortCode.setText(f.getZportCode());

					serviceStatue.setText(f.getServiceStatue());
				}else{
					Window.alert("获取信息为空!请重试...");
					hide();
				}
			}
		});

		this.fiberTable = fiberTable;
		setSize("538px", "359px");
		setGlassEnabled(true);
	}

	@UiHandler("save")
	void onSaveClick(ClickEvent click) {
		Fiber fiber = new Fiber();
		String name = fiberName.getText();
		String code = fiberCode.getText();
		fiber.setName(name);
		fiber.setCode(code);

		fiber.setAmeType(aMeType.getItemText(aMeType.getSelectedIndex()));
		fiber.setAmeCode(aMeCode.getText());
		fiber.setAportCode(aPortCode.getText());

		fiber.setZmeCode(zMeCode.getText());
		fiber.setZmeType(zMeType.getItemText(zMeType.getSelectedIndex()));
		fiber.setZportCode(zPortCode.getText());

		if (verifyEmpty(logicalNo.getText())) {
			fiber.setLogicalNo(Integer.parseInt(logicalNo.getText()));
		}
		if (verifyEmpty(fiberSque.getText())) {
			fiber.setFiberSque(Integer.parseInt(fiberSque.getText()));
		}
		if (verifyEmpty(length.getText())) {
			fiber.setLength(Integer.parseInt(length.getText()));
		}
		fiber.setSpectrum(spectrum.getText());
		fiber.setServiceStatue(serviceStatue.getText());

		fiber.setParentCode(fiberPartCode);

		// if ("保存".equals(save.getText())) {
		cableService.addFiber(fiber, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("添加失败！");
			}

			@Override
			public void onSuccess(String result) {
				if (result != null) {
					Window.alert("添加成功！");
					updateFiberTable();
					hide();
				} else {
					Window.alert("添加失败！");
				}
			}
		});
		// } else if ("更新".equals(save.getText())) {
		// }

	}

	@UiHandler("update")
	void onUpdateClick(ClickEvent event) {
		Fiber fiber = new Fiber();
		String name = fiberName.getText();
		String code = fiberCode.getText();
		fiber.setName(name);
		fiber.setCode(code);

		fiber.setAmeType(aMeType.getItemText(aMeType.getSelectedIndex()));
		fiber.setAmeCode(aMeCode.getText());
		fiber.setAportCode(aPortCode.getText());

		fiber.setZmeCode(zMeCode.getText());
		fiber.setZmeType(zMeType.getItemText(zMeType.getSelectedIndex()));
		fiber.setZportCode(zPortCode.getText());

		if (verifyEmpty(logicalNo.getText())) {
			fiber.setLogicalNo(Integer.parseInt(logicalNo.getText()));
		}
		if (verifyEmpty(fiberSque.getText())) {
			fiber.setFiberSque(Integer.parseInt(fiberSque.getText()));
		}
		if (verifyEmpty(length.getText())) {
			fiber.setLength(Integer.parseInt(length.getText()));
		}
		fiber.setSpectrum(spectrum.getText());
		fiber.setServiceStatue(serviceStatue.getText());
		fiber.setParentCode(fiberPartCode);

		cableService.updateFiber(fiber, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("更新失败！");
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("更新成功！");
				updateFiberTable();
				hide();
			}
		});
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}

	private boolean verifyEmpty(String text) {
		return !(text == null || "".equals(text));
	}

	private int getListBoxIndexByItemtext(ListBox listBox, String text) {
		for (int i = 0; i < listBox.getItemCount(); i++) {
			if (listBox.getItemText(i).equals(text)) {
				return i;
			}
		}
		return -1;
	}

	private void updateFiberTable() {
		cableService.getAllFibers(new AsyncCallback<List<Fiber>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！");
				return;
			}

			@Override
			public void onSuccess(List<Fiber> result) {
				if (result != null) {
					fiberTable.setRowCount(result.size());
					fiberTable.setRowData(0, result);
				}
			}
		});
	}
}
