package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.StationService;
import com.wanma.client.services.StationServiceAsync;
import com.wanma.domain.District;
import com.wanma.domain.Station;

public class StationInfo extends DialogBox {

	private static StationInfoUiBinder uiBinder = GWT
			.create(StationInfoUiBinder.class);
	@UiField TextBox name;//局站名称 *
	@UiField TextBox briefName;//缩写 *
	@UiField TextBox assemblyType;//拼装类型（名称）
	@UiField DateBox createDate;//创建时间
	@UiField TextBox code;//编码 *
	@UiField TextBox namePinyin;//名称拼音 *
	@UiField TextBox assemblyNumber;//拼装号码
	@UiField TextBox zipCode;//区号
	@UiField DateBox updateDate;//修改时间
	@UiField TextBox coverage;//覆盖面积
	@UiField TextBox structureArea;//建筑面积
	@UiField ListBox localOrRemote;//本地/长途
	@UiField TextBox floorArea;//楼层面积
	@UiField ListBox status;//状态
	@UiField TextBox address;//地址 *
	@UiField TextBox notes;//备注
	@UiField Button save;//保存按钮
	@UiField Button cancel;//取消按钮
	@UiField ListBox assetType;//资产类型
	@UiField ListBox region;

	interface StationInfoUiBinder extends UiBinder<Widget, StationInfo> {
	}

	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	public StationInfo() {
		setWidget(uiBinder.createAndBindUi(this));
		
		status.insertItem("0", 0);
		status.insertItem("1", 1);
		
		assetType.insertItem("0", 0);
		assetType.insertItem("1", 1);
		
		//区域初始化:区域名称列表
		stationService.getDistricts(new AsyncCallback<List<District>>(){
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！");
			}
			@Override
			public void onSuccess(List<District> result) {
				if(result.size() > 0){
					for(int i = 0;i<result.size();i++){
						region.insertItem(result.get(i).getDistrictName(), i);
					}
				}else{
					Window.alert("请先添加区域...");
				}
			}
		});
		
		setSize("612px", "490px");
		setGlassEnabled(true);
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event){
		this.hide();
	}
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event){
		if(verifyEmpty(name.getText()) ||
				verifyEmpty(zipCode.getText()) ||
				verifyEmpty(region.getItemText(region.getSelectedIndex())) ||
				verifyEmpty(code.getText()) || verifyEmpty(namePinyin.getText()) || verifyEmpty(address.getText())){
			Window.alert("某些字段不能为空，请检查后在保存...");
			return;
		}
		Station s = new Station();
		
		s.setName(name.getText());
		s.setDistrictName(region.getItemText(region.getSelectedIndex()));
		s.setNotes(notes.getText());
		s.setZipCode(zipCode.getText());
		s.setCode(code.getText());
		s.setAddress(address.getText());
		s.setAssemblyNumber(assemblyNumber.getText());
		s.setAssemblyType(assemblyType.getText());
		s.setCoverage(Integer.parseInt(coverage.getText()));
		s.setFloors(Integer.parseInt(floorArea.getText()));
		s.setStructureArea(Integer.parseInt(structureArea.getText()));
		s.setAssetType(assetType.getItemText(assetType.getSelectedIndex()));
		s.setPinyinName(namePinyin.getText());
		s.setCreateTime(createDate.getValue());
		s.setReviseTime(updateDate.getValue());
		s.setBriefName(briefName.getText());
		s.setPhysicalStatue(status.getItemText(status.getSelectedIndex()));
		
		stationService.addStation(s, new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Window.alert("添加局站成功，局站名称：" + result);
				hide();
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("添加失败！");
			}
		});
	}
	
	private boolean verifyEmpty(String text){
		return text == null || "".equals(text);
	}

}
