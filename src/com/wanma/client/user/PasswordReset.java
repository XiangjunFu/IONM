package com.wanma.client.user;

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
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.UserService;
import com.wanma.client.services.UserServiceAsync;

public class PasswordReset extends DialogBox {

	private static PasswordResetUiBinder uiBinder = GWT
			.create(PasswordResetUiBinder.class);
	private UserServiceAsync userService = GWT.create(UserService.class);
	
	@UiField TextBox userName;
	@UiField PasswordTextBox password;
	@UiField PasswordTextBox confirmPassword;
	@UiField Button submit;
	@UiField Button cancel;

	interface PasswordResetUiBinder extends UiBinder<Widget, PasswordReset> {
	}

	public PasswordReset(String name) {
		setWidget(uiBinder.createAndBindUi(this));
		userName.setText(name);
		userName.setEnabled(false);
	}
	
	@UiHandler("submit")
	void onSubmitClick(ClickEvent event){
		String tempName = userName.getText();
		String tempPassword = password.getText();
		String tempConfirmPassword = confirmPassword.getText();
		if(verifyAddInfo(tempName) && verifyAddInfo(tempPassword) && verifyAddInfo(tempConfirmPassword)){
			if(tempPassword.equals(tempConfirmPassword)){
				userService.updateUserPassword(tempName,tempPassword,new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						if(result.booleanValue()){
							Window.alert("用户密码修改成功！！");
							hide();
						}else{
							Window.alert("密码修改失败！！");
						}
					}
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("密码修改失败！！");
					}
				});
			} else {
				Window.alert("两次输入密码不匹配！！");
			}
		}else{
			Window.alert("填写信息有误！！");
		}
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event){
		this.hide();
	}
	/**
	 * 验证信息是否为空
	 * @param addInfo
	 * @return 为空，返回false
	 */
	private boolean verifyAddInfo(String addInfo){
		return !(addInfo == null || "".equals(addInfo));
	}
	
}
