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
import com.wanma.client.services.TerminalWeldJumpService;
import com.wanma.client.services.TerminalWeldJumpServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.JumperConnector;

public class AddJumpConnector extends DialogBox {

	private static AddJumpConnectorUiBinder uiBinder = GWT
			.create(AddJumpConnectorUiBinder.class);
	@UiField
	TextBox aPort;
	@UiField
	TextBox zPort;
	@UiField
	Button jumpConn;
	@UiField
	Button cancel;
	@UiField
	TextBox aMeType;
	@UiField
	TextBox aMeCode;
	@UiField
	TextBox zMeCode;
	@UiField
	TextBox zMeType;

	interface AddJumpConnectorUiBinder extends
			UiBinder<Widget, AddJumpConnector> {
	}

	private TerminalWeldJumpServiceAsync terminalWeldJumpService = GWT
			.create(TerminalWeldJumpService.class);

	public AddJumpConnector() {
		setWidget(uiBinder.createAndBindUi(this));

		setSize("678px", "261px");
		setGlassEnabled(true);
	}

	@UiHandler("jumpConn")
	void onJumpConnClick(ClickEvent event) {
		Window.alert("fsdjbsdfbsdfb");
		if (StringVerifyUtils.verifyEmpty(aMeType.getText())
				&& StringVerifyUtils.verifyEmpty(aMeCode.getText())
				&& StringVerifyUtils.verifyEmpty(zMeCode.getText())
				&& StringVerifyUtils.verifyEmpty(zMeType.getText())) {
			JumperConnector jumpConn = new JumperConnector();

			jumpConn.setAmeType(aMeType.getText());
			jumpConn.setAmeCode(aMeCode.getText());
			jumpConn.setZmeCode(zMeCode.getText());
			jumpConn.setAportCode(aPort.getText());
			jumpConn.setZmeType(zMeType.getText());
			jumpConn.setZportCode(zPort.getText());

			terminalWeldJumpService.addJumpConnector(jumpConn,
					new AsyncCallback<Integer>() {
						@Override
						public void onSuccess(Integer result) {
							if (result != null) {
								Window.alert("添加成功！");
								hide();
							}
						}
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("添加失败！");
						}
					});
		}else{
			Window.alert("部分字段不能为空！请检查...");
		}

	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}
}
