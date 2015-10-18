package com.wanma.client.worklist.business;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.client.services.BusinessWorkListServiceAsync;
import com.wanma.client.services.GroupService;
import com.wanma.client.services.GroupServiceAsync;
import com.wanma.client.services.WorkListService;
import com.wanma.client.services.WorkListServiceAsync;
import com.wanma.domain.UserOdn;
import com.wanma.domain.WorkGroup;

public class SelectGroupAndUser extends DialogBox {

	private static SelectGroupAndUserUiBinder uiBinder = GWT
			.create(SelectGroupAndUserUiBinder.class);
	@UiField ListBox group;
	@UiField ListBox user;
	@UiField Button assign;
	@UiField Button cancel;
	
	private GroupServiceAsync groupService = GWT.create(GroupService.class);
	
	private BusinessWorkListServiceAsync businessWorkListService = GWT.create(BusinessWorkListService.class);
	
	interface SelectGroupAndUserUiBinder extends
			UiBinder<Widget, SelectGroupAndUser> {
	}

	private String routeTaskCode = "";
	
	private BusinessWorkListManagerTable businessWorkListManagerTable = null;
	
	private BusinessWorkListDetail businessWorkListDetail;
	
	private String jobOrderCode = "";
	
	public SelectGroupAndUser(String routeTaskCode,
			BusinessWorkListManagerTable businessWorkListManagerTable,BusinessWorkListDetail businessWorkListDetail,String jobOrderCode) {
		setWidget(uiBinder.createAndBindUi(this));
		
		setSize("450px","239px");
		setGlassEnabled(true);
		
		groupService.getWorkGroupIds(new AsyncCallback<List<WorkGroup>>() {
			@Override
			public void onSuccess(List<WorkGroup> result) {
				if(result != null){
					group.clear();
					group.insertItem("", 0);
					for(int i = 0;i < result.size();i++){
						WorkGroup workGroup = result.get(i);
						group.insertItem( workGroup.getId().getGroupName(),i+1);
					}
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取施工组信息失败！！！");
			}
		});
		
		this.routeTaskCode = routeTaskCode;
		this.businessWorkListManagerTable = businessWorkListManagerTable;
		this.businessWorkListDetail = businessWorkListDetail;
		this.jobOrderCode = jobOrderCode;
		
		group.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				int index = group.getSelectedIndex();
				if(index <= 0){
					Window.alert("请选择施工组...");
					return;
				}
				String groupName = group.getItemText(index);
				groupService.getUsersByGroup(groupName, new AsyncCallback<List<UserOdn>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("施工人员信息获取失败！！！");
					}
					@Override
					public void onSuccess(List<UserOdn> result) {
						user.clear();
						if(result != null){
							for(int i = 0;i<result.size();i++){
								UserOdn userOdn = result.get(i);
								user.insertItem(userOdn.getRealName() + "#" + userOdn.getUserName(), i);
							}
						}else{
							Window.alert("施工人员信息未获取到！！！");
						}
					}
				});
			}
		});
	}

	//路由工单指派，将施工人员以及施工组写入数据库
	@UiHandler("assign")
	public void onAssignClick(ClickEvent event){
		String groupName = group.getItemText(group.getSelectedIndex());
		String name = user.getItemText(user.getSelectedIndex());
		String realName = name.split("#")[0];
		String userName = name.split("#")[1];
		//workListService.assignConfigTask(businessWorkListCode,groupName,userName,assignTask);
		businessWorkListService.assignExecuteOrder(routeTaskCode,groupName,userName,jobOrderCode,assignTask);
	}
	
	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event){
		this.hide();
	}
	
	private AsyncCallback<Boolean> assignTask = new AsyncCallback<Boolean>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("失败！！！");
		}
		@Override
		public void onSuccess(Boolean result) {
			if(result.booleanValue()){
				hide();
				/**
				 * 成功是否要涉及到业务工单状态的更改
				 * 什么时候更改，其路由工单全部指派完毕后进行更改？？？
				 */
				
				//刷新表格显示全部工单
				businessWorkListManagerTable.updateBusinessWorkList();
			}else{
				Window.alert("失败！！！");
			}
		}
	};
}
