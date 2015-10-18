package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.domain.CableSpan;

public class AddOrUpdateCableSpan extends DialogBox {

	private static AddOrUpdateCableSpanUiBinder uiBinder = GWT
			.create(AddOrUpdateCableSpanUiBinder.class);
	@UiField
	TextBox name;// 名称
	@UiField
	TextBox code;// 编码
	@UiField
	TextBox assemblyName;// 拼装名称
	@UiField
	TextBox assemblyCode;// 拼装编码
	@UiField
	ListBox cableStructure;// 光缆结构
	@UiField
	TextBox cableLength;// 光缆长度
	@UiField
	TextBox fibernumber;// 光纤数
	@UiField
	TextBox fiberLength;// 光纤长度
	@UiField
	ListBox fiberType;// 光纤类型
	@UiField
	ListBox fiberMode;// 光纤模式
	@UiField
	TextBox note;// 备注
	@UiField
	ListBox buildWay;// 敷设方式
	@UiField
	TextBox remainLength;// 盘留长度
	@UiField
	TextBox remainPosition;// 盘留位置
	@UiField
	TextBox collector;// 数据采集人
	@UiField
	DateBox collectData;// 数据采集
	@UiField
	TextBox lightSpectrum;// 光谱名
	@UiField
	TextBox localNet;// 本地网
	@UiField
	Label localRemoteLabel;// 标签
	@UiField
	Label deviceStatuslabel;// 标签
	@UiField
	Label coreLossLabel;// 标签
	@UiField
	ListBox projectStatus;// 工程状态
	@UiField
	TextBox projectNo;// 工程编号
	@UiField
	TextArea ANode;// A端节点
	@UiField
	TextArea ZNode;// Z端节点
	@UiField
	ListBox deviceStatus;// 设备状态
	@UiField
	ListBox localRemote;// 本地远程
	@UiField
	TextBox coreLoss;// 纤芯损耗

	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	Button update;

	private String cableCode = "";

	private CellTable<CableSpan> fiberPartTable;
	
	private CableServiceAsync cableService = GWT.create(CableService.class);

	interface AddOrUpdateCableSpanUiBinder extends
			UiBinder<Widget, AddOrUpdateCableSpan> {
	}

	public AddOrUpdateCableSpan(String cableCode, CellTable<CableSpan> fiberPartTable) {
		setWidget(uiBinder.createAndBindUi(this));

		// 设置要添加光缆段的光缆名称
		this.cableCode = cableCode;
		this.fiberPartTable = fiberPartTable;
		// listbox init
		localRemote.insertItem("0", 0);
		localRemote.insertItem("1", 1);

		cableStructure.insertItem("0", 0);
		cableStructure.insertItem("1", 1);

		fiberType.insertItem("0", 0);
		fiberType.insertItem("1", 0);

		fiberMode.insertItem("0", 0);
		fiberMode.insertItem("1", 1);

		buildWay.insertItem("0", 0);
		buildWay.insertItem("1", 1);

		projectStatus.insertItem("0", 0);
		projectStatus.insertItem("1", 1);

		deviceStatus.insertItem("0", 0);
		deviceStatus.insertItem("1", 1);

		update.setVisible(false);

		setSize("589px", "560px");
		setGlassEnabled(true);
	}

	public AddOrUpdateCableSpan(String cableSpancode, String str,CellTable<CableSpan> fiberPartTable) {
		setWidget(uiBinder.createAndBindUi(this));
		save.setVisible(false);
		cableService.getCableSpanByCode(cableSpancode,
				new AsyncCallback<CableSpan>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("获取信息失败！");
					}

					@Override
					public void onSuccess(CableSpan cableSpan) {
						Window.alert("获取光缆段信息！");
						name.setText(cableSpan.getName());//
						code.setText(cableSpan.getCode());
						assemblyName.setText(cableSpan.getAssemblyName());
						assemblyCode.setText(cableSpan.getAssemblyNumber());
						int index = getListBoxIndexByItemtext(cableStructure,
								cableSpan.getStructureType());
						cableStructure.setSelectedIndex(index);
						cableLength.setText(cableSpan.getLength().toString());
						fibernumber.setText(cableSpan.getFiberLength()
								.toString());

						index = getListBoxIndexByItemtext(fiberType,
								cableSpan.getFiberType());
						fiberType.setSelectedIndex(index);

						index = getListBoxIndexByItemtext(fiberMode,
								cableSpan.getFiberModel());
						fiberMode.setSelectedIndex(index);

						note.setText(cableSpan.getNotes());

						index = getListBoxIndexByItemtext(buildWay,
								cableSpan.getLayingMethod());
						buildWay.setSelectedIndex(index);

						remainLength.setText(cableSpan.getDiskRemainLength()
								.toString());
						remainPosition.setText(cableSpan.getDiskRemainType());
						collector.setText(cableSpan.getAcquisitoner());
						lightSpectrum.setText(cableSpan.getSpectrum());
						localNet.setText(cableSpan.getLocalNetwork());
						index = getListBoxIndexByItemtext(projectStatus,
								cableSpan.getProjectStatus());
						projectStatus.setSelectedIndex(index);
						projectNo.setText(cableSpan.getProjectNum());
						ANode.setText(cableSpan.getAmeCode());
						ZNode.setText(cableSpan.getZmeCode());
						index = getListBoxIndexByItemtext(deviceStatus,
								cableSpan.getPhyStatue());
						deviceStatus.setSelectedIndex(index);
						index = getListBoxIndexByItemtext(localRemote,
								cableSpan.getLoclaOrRemote() ? "0" : "1");
						localRemote.setSelectedIndex(index);
						coreLoss.setText(cableSpan.getDispersion().toString());
					}
				});
		
		this.fiberPartTable = fiberPartTable;
		
		setSize("589px", "560px");
		setGlassEnabled(true);
	}

	@UiHandler("save")
	// 保存光缆段信息
	void onSaveClick(ClickEvent event) {
		boolean temp = verifyInputEmpty(name.getText(), code.getText(),
				cableLength.getText(), fiberLength.getText(),
				fibernumber.getText(),
				fiberType.getItemText(fiberType.getSelectedIndex()),
				buildWay.getItemText(buildWay.getSelectedIndex()),
				fiberMode.getItemText(fiberMode.getSelectedIndex()),
				localRemote.getItemText(localRemote.getSelectedIndex()),
				deviceStatus.getItemText(deviceStatus.getSelectedIndex()));
		if (!temp) {
			Window.alert("部分字段不能为空！");
			return;
		}
		CableSpan cableSpan = new CableSpan();

		cableSpan.setAmeCode(ANode.getText());
		cableSpan.setName(name.getText());
		cableSpan.setAssemblyName(assemblyName.getText());
		cableSpan.setAssemblyNumber(assemblyCode.getText());
		cableSpan.setCode(code.getText());
		String diskRemainLength = remainLength.getText();
		if (diskRemainLength != null && !"".equals(diskRemainLength)) {
			cableSpan.setDiskRemainLength(Integer.parseInt(diskRemainLength));
		}
		cableSpan.setDiskRemainType(remainPosition.getText());
		String dispersion = coreLoss.getText();
		if (dispersion != null && !"".equals(dispersion)) {
			cableSpan.setDispersion(Double.valueOf(dispersion));
		}
		cableSpan.setAcquisitoner(collector.getText());
		cableSpan.setDataAcquisitionTime(collectData.getValue()
				.toLocaleString());
		String tempString = fiberLength.getText();
		if (tempString != null && !"".equals(tempString)) {
			cableSpan.setFiberLength(Integer.parseInt(tempString));
		}
		tempString = fibernumber.getText();
		if (tempString != null && !"".equals(tempString)) {
			cableSpan.setFiberNumber(Integer.parseInt(tempString));
		}
		cableSpan.setFiberModel(fiberMode.getItemText(fiberMode
				.getSelectedIndex()));
		cableSpan.setFiberType(fiberType.getItemText(fiberType
				.getSelectedIndex()));
		cableSpan.setParentCode(cableCode);
		cableSpan.setStructureType(cableStructure.getItemText(cableStructure
				.getSelectedIndex()));
		cableSpan.setNotes(note.getText());
		cableSpan.setSpectrum(lightSpectrum.getText());
		cableSpan.setLayingMethod(buildWay.getItemText(buildWay
				.getSelectedIndex()));
		cableSpan.setProjectNum(projectNo.getText());
		cableSpan.setProjectStatus(projectStatus.getItemText(projectStatus
				.getSelectedIndex()));
		tempString = cableLength.getText();
		if (tempString != null && !"".equals(tempString)) {
			cableSpan.setLength(Integer.parseInt(tempString));
		}
		cableSpan.setLoclaOrRemote(localRemote.getSelectedIndex() == 0 ? true
				: false);
		cableSpan.setLocalNetwork(localNet.getText());
		cableSpan.setPhyStatue(deviceStatus.getItemText(deviceStatus
				.getSelectedIndex()));

		// if("保存".equals(this.save.getText())){
		// 保存光缆段
		cableService.addCableSpan(cableSpan, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("添加失败！");
			}

			@Override
			public void onSuccess(String result) {
				if (result != null) {
					Window.alert("添加成功！");
					updateFiberPartTable();
					hide();
				} else {
					Window.alert("添加失败！");
				}
			}
		});
		// } else if("更新".equals(this.save.getText())){
		// }
	}

	@UiHandler("update")
	void onUpdateClick(ClickEvent event) {
		boolean temp = verifyInputEmpty(name.getText(), code.getText(),
				cableLength.getText(), fiberLength.getText(),
				fibernumber.getText(),
				fiberType.getItemText(fiberType.getSelectedIndex()),
				buildWay.getItemText(buildWay.getSelectedIndex()),
				fiberMode.getItemText(fiberMode.getSelectedIndex()),
				localRemote.getItemText(localRemote.getSelectedIndex()),
				deviceStatus.getItemText(deviceStatus.getSelectedIndex()));
		if (!temp) {
			Window.alert("部分字段不能为空！");
			return;
		}
		CableSpan cableSpan = new CableSpan();

		cableSpan.setAmeCode(ANode.getText());
		cableSpan.setName(name.getText());
		cableSpan.setAssemblyName(assemblyName.getText());
		cableSpan.setAssemblyNumber(assemblyCode.getText());
		cableSpan.setCode(code.getText());
		String diskRemainLength = remainLength.getText();
		if (diskRemainLength != null && !"".equals(diskRemainLength)) {
			cableSpan.setDiskRemainLength(Integer.parseInt(diskRemainLength));
		}
		cableSpan.setDiskRemainType(remainPosition.getText());
		String dispersion = coreLoss.getText();
		if (dispersion != null && !"".equals(dispersion)) {
			cableSpan.setDispersion(Double.valueOf(dispersion));
		}
		cableSpan.setAcquisitoner(collector.getText());
		cableSpan.setDataAcquisitionTime(collectData.getValue()
				.toLocaleString());
		String tempString = fiberLength.getText();
		if (tempString != null && !"".equals(tempString)) {
			cableSpan.setFiberLength(Integer.parseInt(tempString));
		}
		tempString = fibernumber.getText();
		if (tempString != null && !"".equals(tempString)) {
			cableSpan.setFiberNumber(Integer.parseInt(tempString));
		}
		cableSpan.setFiberModel(fiberMode.getItemText(fiberMode
				.getSelectedIndex()));
		cableSpan.setFiberType(fiberType.getItemText(fiberType
				.getSelectedIndex()));
		cableSpan.setParentCode(cableCode);
		cableSpan.setStructureType(cableStructure.getItemText(cableStructure
				.getSelectedIndex()));
		cableSpan.setNotes(note.getText());
		cableSpan.setSpectrum(lightSpectrum.getText());
		cableSpan.setLayingMethod(buildWay.getItemText(buildWay
				.getSelectedIndex()));
		cableSpan.setProjectNum(projectNo.getText());
		cableSpan.setProjectStatus(projectStatus.getItemText(projectStatus
				.getSelectedIndex()));
		tempString = cableLength.getText();
		if (tempString != null && !"".equals(tempString)) {
			cableSpan.setLength(Integer.parseInt(tempString));
		}
		cableSpan.setLoclaOrRemote(localRemote.getSelectedIndex() == 0 ? true
				: false);
		cableSpan.setLocalNetwork(localNet.getText());
		cableSpan.setPhyStatue(deviceStatus.getItemText(deviceStatus
				.getSelectedIndex()));

		// 更新光缆
		cableService.updateCableSpan(cableSpan, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("更新失败！");
			}

			@Override
			public void onSuccess(Void result) {
				Window.alert("更新成功！");
				updateFiberPartTable();
				hide();
			}
		});
	}

	@UiHandler("cancel")
	void onCancel(ClickEvent event) {
		this.hide();
	}

	private boolean verifyEmpty(String text) {
		return !(text == null || "".equals(text));
	}

	private boolean verifyInputEmpty(String name, String code,
			String cableLength, String fiberLength, String fibernumber,
			String fiberType, String buildWay, String fibermode,
			String localRemote, String deviceStatus) {
		return verifyEmpty(name) && verifyEmpty(code)
				&& verifyEmpty(cableLength) && verifyEmpty(fiberLength)
				&& verifyEmpty(fibernumber) && verifyEmpty(fiberType)
				&& verifyEmpty(buildWay) && verifyEmpty(fibermode)
				&& verifyEmpty(localRemote) && verifyEmpty(deviceStatus);
	}

	private int getListBoxIndexByItemtext(ListBox listBox, String text) {
		for (int i = 0; i < listBox.getItemCount(); i++) {
			if (listBox.getItemText(i).equals(text)) {
				return i;
			}
		}
		return -1;
	}
	
	private void updateFiberPartTable(){
		cableService.getAllCableSpan(new AsyncCallback<List<CableSpan>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！");
				return;
			}

			@Override
			public void onSuccess(List<CableSpan> result) {
				if (result != null) {
					fiberPartTable.setRowCount(result.size());
					fiberPartTable.setRowData(0, result);
				}
			}
		});
	}
}
