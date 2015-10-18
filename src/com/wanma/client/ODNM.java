package com.wanma.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.login.LoginService;
import com.wanma.client.login.LoginServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.UserOdn;

public class ODNM implements EntryPoint {

	private static ODNMUiBinder uiBinder = GWT
			.create(ODNMUiBinder.class);

	private LoginServiceAsync loginService = GWT.create(LoginService.class);
	@UiField
	TextBox username;
	@UiField
	PasswordTextBox password;
	@UiField
	Button login;
	@UiField
	Button reset;

	public void onModuleLoad() {
		HTMLPanel outer = (HTMLPanel) uiBinder.createAndBindUi(this);
		/*
		login.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				loginService.checkLogin(username.getText(), password.getText(), new AsyncCallback<Boolean>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("调用失败！！！");
					}
					@Override
					public void onSuccess(Boolean result) {
						if(!result){
							Window.alert("用户名或是密码错误！！！");
						}else{
							Mainframe mainframe = new Mainframe(username.getText());
							RootLayoutPanel root = RootLayoutPanel.get();
							root.clear();
							root.add(mainframe);
						}
					}
				});
				
				loginService.getUser(username.getText(), new AsyncCallback<UserOdn>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("调用失败！！！");
					}
					@Override
					public void onSuccess(UserOdn user) {
						
					}
				});
			}
		});
		*/
		username.setFocus(true);
		login.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ENTER){
					onLoginBtnClick(null);
				}
			}
		});
		reset.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				username.setText("");
				password.setText("");
			}
		});
		RootLayoutPanel root = RootLayoutPanel.get();
		root.add(outer);
	}
	
	@UiHandler("login")
	void onLoginBtnClick(ClickEvent event){
		if(!StringVerifyUtils.verifyEmpty(username.getText()) || !StringVerifyUtils.verifyEmpty(password.getText())){
			Window.alert("输入不能为空！！！");
			return;
		}
		loginService.checkLogin(username.getText(), password.getText(), new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("调用失败！！！");
			}
			@Override
			public void onSuccess(Boolean result) {
				if(!result){
					Window.alert("用户名或是密码错误！！！");
				}else{
					Mainframe mainframe = new Mainframe(username.getText());
					RootLayoutPanel root = RootLayoutPanel.get();
					root.clear();
					root.add(mainframe);
					//SpaceResourceManager spaceResourceManager = new SpaceResourceManager();
					//RootLayoutPanel root = RootLayoutPanel.get();
					//Mainframe mainFrame = (Mainframe) root.getWidget(root.getAbsoluteTop());
					//SplitLayoutPanel splitLayoutPanel = mainFrame.getSplitLayoutPanel();
					//splitLayoutPanel.clear();
					//splitLayoutPanel.add(spaceResourceManager);
					//root.clear();
					//root.add(mainFrame);
				}
			}
		});
		
		loginService.getUser(username.getText(), new AsyncCallback<UserOdn>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("调用失败！！！");
			}
			@Override
			public void onSuccess(UserOdn user) {
				
			}
		});
	}
	
	
	interface ODNMUiBinder extends UiBinder<Widget, ODNM> {
	}

	public ODNM() {
	}
	
	public static native void redirect(String url)/*-{
		$wnd.location = url;
	}-*/;
	
	
}
