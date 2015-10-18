package com.wanma.client.user;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.wanma.client.services.GroupService;
import com.wanma.client.services.GroupServiceAsync;
import com.wanma.client.services.UserService;
import com.wanma.client.services.UserServiceAsync;
import com.wanma.domain.UserOdn;
import com.wanma.domain.WorkGroup;
import com.wanma.domain.WorkGroupId;

public class UserAdd extends DialogBox {

	interface Binder extends UiBinder<Widget, UserAdd> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private UserServiceAsync userService = GWT.create(UserService.class);
	private GroupServiceAsync groupService = GWT.create(GroupService.class);
	/**Buttons*/
	@UiField Button btn;//确定提交
	@UiField Button cancel;//取消
	/**User Info*/
	@UiField TextBox userName;//用户姓名
	@UiField TextBox userAccount;//用户帐号
	@UiField ListBox sex;//用户性别
	@UiField TextBox address;//用户地址
	@UiField TextBox number;//用户短号，  什么是短号？
	@UiField TextArea description;//用户描述
	@UiField PasswordTextBox password;//密码
	@UiField PasswordTextBox confirmPassword;//确认密码
	@UiField DateBox birthday;//生日
	@UiField TextBox telephone;//电话
	@UiField TextBox mobile;//手机
	@UiField ListBox groupName;//施工班组
	@UiField TextBox email;//电子邮箱
	
	/**===========*/
	//施工组名称集合
	List<WorkGroup> groups = null;
	
	public UserAdd() {
		setWidget(binder.createAndBindUi(this));
		
		// Use this opportunity to set the dialog's caption.
		setText("添加施工人员");
		sex.insertItem("男", 0);
		sex.insertItem("女", 1);
		sex.setSelectedIndex(0);
		
		//获取所有施工组名称
		groupService.getWorkGroupIds(new AsyncCallback<List<WorkGroup>>(){

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取施工组失败！！");
			}

			@Override
			public void onSuccess(List<WorkGroup> returnGroups) {
				if(returnGroups != null){
					groups = returnGroups;
					for(int i = 0;i<returnGroups.size();i++){
						groupName.insertItem(returnGroups.get(i).getId().getGroupName(), i);
					}
				}else{
					Window.alert("没有获取到施工组！！");
				}
			}
		});
		
		//setPixelSize(660, 450);
		setSize("660px", "450px");
		//setAnimationEnabled(true);
		setGlassEnabled(true);
	}
	
	@Override
	protected void onPreviewNativeEvent(NativePreviewEvent event) {
		super.onPreviewNativeEvent(event);
		NativeEvent evt = event.getNativeEvent();
		if (evt.getType().equals("keydown")) {
			// Use the popup's key preview hooks to close the dialog when either
			// enter or escape is pressed.
			switch (evt.getKeyCode()) {
				case KeyCodes.KEY_ENTER:
				case KeyCodes.KEY_ESCAPE:
					hide();
					break;
			}
		}
	}
	
	@UiHandler("btn")
	void onSubmitClicked(ClickEvent event) {
		//点击btn，验证必要信息，如正确，则提交
		String tempUserName = userName.getText();//不能为空
		String tempUserAccount = userAccount.getText();//不能为空
		String tempSex = sex.getItemText(sex.getSelectedIndex());//默认为男
		String tempAddress = address.getText();
		String tempNumber = number.getText();
		String tempDescription = description.getText();
		//可在此构建相应格式日期
		DateTimeFormat dataFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
		String tempBirthday = dataFormat.format(birthday.getValue());
		String tempTelephone = telephone.getText();
		String tempMobile = mobile.getText();//手机不能为空
		String tempGroupName = groupName.getItemText(groupName.getSelectedIndex());//施工组不能为空
		String tempEmail = email.getText();
		
		String tempPassword = password.getText();//密码不为空
		String tempConfirmPassword = confirmPassword.getText();//确认密码不为空
		
		if(verifyAddInfo(tempUserName) //用户名验证
				&& verifyAddInfo(tempUserAccount) //用户账户验证
				&& verifyAddInfo(tempMobile) //用户手机号验证
				&& verifyAddInfo(tempGroupName)//施工组验证
				&& verifyAddInfo(tempPassword)//密码验证
				&&verifyAddInfo(tempConfirmPassword)){//确认密码验证
			//不为空提示
			if(tempPassword.equals(tempConfirmPassword)){
				//录入用户信息
				UserOdn user = new UserOdn(tempUserAccount,
						tempMobile,tempUserName,tempGroupName,tempPassword,"userType",tempEmail,"");
				userService.addUser(user, new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result) {
						hide();
						Window.alert("录入成功！！！");
					}
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("添加错误！！" + caught.getMessage());
					}
				});
			}else{
				Window.alert("密码不一致！！！");
			}
		}else{
			Window.alert("输入有误，请检查！！！");
		}
	}
	
	@UiHandler("cancel")
	void onCancelClicked(ClickEvent event){
		//取消按钮操作,关闭对话框
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
