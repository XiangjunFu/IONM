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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.wanma.client.services.TerminalWeldJumpService;
import com.wanma.client.services.TerminalWeldJumpServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.WeldConnector;

public class AddWeldConnector extends DialogBox {

	private static AddWeldConnectorUiBinder uiBinder = GWT
			.create(AddWeldConnectorUiBinder.class);
	@UiField
	Button weldbtn;
	@UiField
	Button cancel;
	@UiField
	TextBox meType;
	@UiField
	TextBox meCode;
	@UiField
	TextBox aCableSpan;
	@UiField
	TextBox zCableSpan;
	@UiField
	TextBox aFiber;
	@UiField
	TextBox zFiber;

	interface AddWeldConnectorUiBinder extends
			UiBinder<Widget, AddWeldConnector> {
	}

	private TerminalWeldJumpServiceAsync terminalWeldJumpService = GWT.create(TerminalWeldJumpService.class);
	
	private String deviceName = "";

	public AddWeldConnector(String deviceName) {
		setWidget(uiBinder.createAndBindUi(this));

		this.deviceName = deviceName;
		setSize("371px", "201px");
		setGlassEnabled(true);
	}

	@UiHandler("weldbtn")
	void onWeldbtnClick(ClickEvent event) {
		WeldConnector weldConn = new WeldConnector();
		weldConn.setAcableSectionCode(aCableSpan.getText());
		weldConn.setZcableSectionCode(zCableSpan.getText());
		if(StringVerifyUtils.verifyEmpty(aFiber.getText())){weldConn.setAfiberNo(Integer.parseInt(aFiber.getText()));}
		if(StringVerifyUtils.verifyEmpty(zFiber.getText())){weldConn.setZfiberNo(Integer.parseInt(zFiber.getText()));}
		weldConn.setMeCode(meCode.getText());
		weldConn.setMeType(meType.getText());
		
		terminalWeldJumpService.addWeldConnector(weldConn,new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("添加失败！");
			}
			@Override
			public void onSuccess(Integer result) {
				if (result != null) {
					Window.alert("添加成功！");
					hide();
				}
			}
		});
		
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}
}
