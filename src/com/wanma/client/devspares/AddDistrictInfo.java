package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.StationService;
import com.wanma.client.services.StationServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.District;

public class AddDistrictInfo extends DialogBox {

	private static AddDistrictInfoUiBinder uiBinder = GWT
			.create(AddDistrictInfoUiBinder.class);
	@UiField
	TextBox districtName;
	@UiField
	TextBox zipCode;
	@UiField
	Button save;
	@UiField
	Button cancel;

	interface AddDistrictInfoUiBinder extends UiBinder<Widget, AddDistrictInfo> {
	}

	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	private Widget widget = uiBinder.createAndBindUi(this);

	public AddDistrictInfo() {
		setWidget(widget);
	
		setText("添加区域...");
		setSize("440px", "190px");
		setGlassEnabled(true);
	}

	@UiHandler("save")
	void onSaveClick(ClickEvent event){
		String tempDistrictName = districtName.getText();
		String tempZipCode = zipCode.getText();
		if(StringVerifyUtils.verifyEmpty(tempDistrictName) && StringVerifyUtils.verifyEmpty(tempZipCode)){
			District district = new District();
			district.setDistrictName(tempDistrictName);
			district.setZipCode(tempZipCode);
			stationService.addDistrict(district, new AsyncCallback<String>() {
				@Override
				public void onSuccess(String result) {
					if(result != null){
						Window.alert("添加成功！");
						hide();
					}else{
						Window.alert("添加失败！");
						hide();
					}
				}
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("添加失败！");
					hide();
				}
			});
		}else{
			Window.alert("部分字段不能为空，请检查...");
			return;
		}
	}
	
	@UiHandler("cancel")
	void onCancel(ClickEvent event){
		this.hide();
	}
	
}
