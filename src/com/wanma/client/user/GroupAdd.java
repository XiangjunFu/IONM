package com.wanma.client.user;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.services.GroupService;
import com.wanma.client.services.GroupServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.WorkGroup;
import com.wanma.domain.WorkGroupId;

public class GroupAdd extends DialogBox {

	interface Binder extends UiBinder<Widget, GroupAdd> {
	}

	private static final Binder binder = GWT.create(Binder.class);

	private GroupServiceAsync groupService = GWT.create(GroupService.class);
	
	@UiField
	Button submit;
	@UiField
	Button cancel;
	
	@UiField
	TextBox groupName;
	@UiField 
	TextBox groupLeader;
	
	
	public GroupAdd() {
		setText("添加施工组");
		
		setWidget(binder.createAndBindUi(this));
		setSize("390px", "205px");
		setGlassEnabled(true);
	}
	
	@UiHandler("submit")
	void onSubmitClick(ClickEvent event){
		//Window.alert(groupName.getText() + " : " + groupLeader.getText());
		String tempGroupName = groupName.getText();
		String tempGroupLeader = groupLeader.getText();
		if(!StringVerifyUtils.verifyEmpty(tempGroupName) || !StringVerifyUtils.verifyEmpty(tempGroupLeader)){
			Window.alert("字段不能为空！！！");
			return;
		}
		WorkGroup group = new WorkGroup(new WorkGroupId(tempGroupName, tempGroupLeader), tempGroupLeader);
		groupService.addGroup(group, new AsyncCallback<WorkGroupId>() {
			@Override
			public void onSuccess(WorkGroupId id) {
				if(id != null){
					hide();
				}else{
					Window.alert("录入失败！！！");
				}
				
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("录入失败！！！");
			}
		});
	}
	
	@UiHandler("cancel")
	void onResetClick(ClickEvent event){
		this.hide();
	}
	
}
