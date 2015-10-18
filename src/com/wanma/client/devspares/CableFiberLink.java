package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.domain.Cable;
import com.wanma.domain.CableSpan;
import com.wanma.domain.Fiber;

public class CableFiberLink extends DialogBox {

	private static CableFiberLinkUiBinder uiBinder = GWT
			.create(CableFiberLinkUiBinder.class);

	@UiField
	StackLayoutPanel stackLayoutPanel;

	// =====光缆管理=====
	@UiField
	TextBox cableName;// 光缆名称
	@UiField
	ListBox cableLevel;// 光缆级别
	@UiField
	CheckBox fuzzyQuery;// 模糊查询
	@UiField
	Button query;// 查询按钮
	@UiField
	CellTable<Cable> cableGrid;// 光缆列表
	@UiField
	Button addCable;// 增加光缆
	@UiField
	Button deleteCable;// 删除光缆
	@UiField
	Button updateCable;// 修改光缆

	// ====光缆段管理=====
	@UiField
	CellTable<CableSpan> fiberPartTable;// 光缆段列表
	@UiField
	Button addFiberPart;// 添加光缆段
	@UiField
	Button deleteFiberPart;// 删除光缆段
	@UiField
	Button updateFiberPart;// 修改光缆段
	// 纤芯管理
	@UiField
	CellTable<Fiber> fiberTable;
	@UiField
	Button addFiber;
	@UiField
	Button updateFiber;
	@UiField
	Button deleteFiber;
	@UiField Button close;

	private CableServiceAsync cableService = GWT.create(CableService.class);

	interface CableFiberLinkUiBinder extends UiBinder<Widget, CableFiberLink> {
	}

	/**
	 * 对话框初始化
	 */
	public CableFiberLink() {
		setWidget(uiBinder.createAndBindUi(this));

		// 表格数据列表
		cableService.getCables(new AsyncCallback<List<Cable>>() {
			@Override
			public void onSuccess(List<Cable> result) {
				if (result == null) {
					return;
				}
				// 为表格添加数据：数据列表List类型
				cableGrid.setRowCount(result.size(), true);
				cableGrid.setRowData(0, result);// 从0开始添加数据
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取光缆信息失败！");
				return;
			}
		});
		// 初始化表格头部，添加列名
		initCableTable();

		final SingleSelectionModel<Cable> selectionModel = new SingleSelectionModel<Cable>();
		cableGrid.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Cable selected = selectionModel.getSelectedObject();
						if (selected != null) {
							//Window.alert("You selected: " + selected.getName());
						}
					}
				});
		
		// 初始化光缆段信息表
		
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

		initCableSpanTable();

		//初始化纤芯信息表
		cableService.getAllFibers(new AsyncCallback<List<Fiber>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！");
				return;
			}

			@Override
			public void onSuccess(List<Fiber> result) {
				if (result != null) {
					fiberTable.setRowCount(result.size());
					fiberTable.setRowData(0, result);
				}
			}
		});
		initFiberTable();
		
		setSize("800px", "504px");
		setGlassEnabled(true);
	}

	// 初始化表格：添加表头
	private void initCableTable() {
		//
		cableGrid.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		TextColumn<Cable> nameColumn = new TextColumn<Cable>() {
			@Override
			public String getValue(Cable object) {
				return object.getName();
			}
		};
		cableGrid.addColumn(nameColumn, "光缆名称");
		//
		TextColumn<Cable> codeColumn = new TextColumn<Cable>() {
			@Override
			public String getValue(Cable object) {
				return object.getCode();
			}
		};
		cableGrid.addColumn(codeColumn, "光缆编码");
		//
		TextColumn<Cable> levelColumn = new TextColumn<Cable>() {
			@Override
			public String getValue(Cable object) {
				return object.getCableType();
			}
		};
		cableGrid.addColumn(levelColumn, "光缆级别");
	}

	// 初始化光缆段列表
	private void initCableSpanTable() {
		//
		fiberPartTable
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		TextColumn<CableSpan> nameColumn = new TextColumn<CableSpan>() {
			@Override
			public String getValue(CableSpan object) {
				return object.getName();
			}
		};
		fiberPartTable.addColumn(nameColumn, "光缆段名称");
		
		TextColumn<CableSpan> codeColumn = new TextColumn<CableSpan>() {
			@Override
			public String getValue(CableSpan object) {
				return object.getCode();
			}
		};
		fiberPartTable.addColumn(codeColumn, "光缆编码");
		
		//
		TextColumn<CableSpan> fiberNumColumn = new TextColumn<CableSpan>() {
			@Override
			public String getValue(CableSpan object) {
				return object.getFiberNumber().toString();
			}
		};
		fiberPartTable.addColumn(fiberNumColumn, "纤芯数");
		//
		TextColumn<CableSpan> aNodeColumn = new TextColumn<CableSpan>() {
			@Override
			public String getValue(CableSpan object) {
				return object.getAmeCode();
			}
		};
		fiberPartTable.addColumn(aNodeColumn, "起始端");

		TextColumn<CableSpan> zNodeColumn = new TextColumn<CableSpan>() {
			@Override
			public String getValue(CableSpan object) {
				return object.getZmeCode();
			}
		};
		fiberPartTable.addColumn(zNodeColumn, "终止端");

		TextColumn<CableSpan> cableNameColumn = new TextColumn<CableSpan>() {
			@Override
			public String getValue(CableSpan object) {
				return object.getParentCode();
			}
		};
		fiberPartTable.addColumn(cableNameColumn, "所属光缆");

	}

	private void initFiberTable() {
		//
		fiberTable
				.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		TextColumn<Fiber> nameColumn = new TextColumn<Fiber>() {
			@Override
			public String getValue(Fiber object) {
				return object.getName();
			}
		};
		fiberTable.addColumn(nameColumn, "纤芯名称");
		
		TextColumn<Fiber> codeColumn = new TextColumn<Fiber>() {
			@Override
			public String getValue(Fiber object) {
				return object.getCode();
			}
		};
		fiberTable.addColumn(codeColumn, "纤芯编码");
		
		//
		TextColumn<Fiber> parentColumn = new TextColumn<Fiber>() {
			@Override
			public String getValue(Fiber object) {
				return object.getParentCode();
			}
		};
		fiberTable.addColumn(parentColumn, "所属光缆段");
		//
		TextColumn<Fiber> lengthColumn = new TextColumn<Fiber>() {
			@Override
			public String getValue(Fiber object) {
				return object.getLength().toString();
			}
		};
		fiberTable.addColumn(lengthColumn, "长度");
		
		TextColumn<Fiber> aNodeColumn = new TextColumn<Fiber>() {
			@Override
			public String getValue(Fiber object) {
				return object.getAmeCode();
			}
		};
		fiberTable.addColumn(aNodeColumn, "起始端");
		
		TextColumn<Fiber> zNodeColumn = new TextColumn<Fiber>() {
			@Override
			public String getValue(Fiber object) {
				return object.getZmeCode();
			}
		};
		fiberTable.addColumn(zNodeColumn, "终止端");
	}

	@UiHandler("addCable")
	// 添加一条新的光缆
	void onAddCableClick(ClickEvent event) {
		AddOrUpdateCableDialogBox addFiberLink = new AddOrUpdateCableDialogBox(cableGrid);
		addFiberLink.center();
		addFiberLink.show();
	}

	@UiHandler("deleteCable")
	void onDeleteClick(ClickEvent event) {
		int rowIndex = cableGrid.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		// 删除所选光缆
		TableRowElement rowElement = cableGrid.getRowElement(rowIndex);
		String cableName = rowElement.getCells().getItem(0).getInnerText();
		cableService.deleteCable(cableName, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("删除失败！");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result.booleanValue()) {
					Window.alert("删除成功！");
					updateCableTable();
				} else {
					Window.alert("删除失败！");
				}
			}
		});
	}

	@UiHandler("updateCable")
	void onUpdateClick(ClickEvent event) {
		int rowIndex = cableGrid.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		TableRowElement rowElement = cableGrid.getRowElement(rowIndex);
		String cableName = rowElement.getCells().getItem(0).getInnerText();
		// 更新所选光缆:获取数据、更改、提交
		AddOrUpdateCableDialogBox addFiberLink = new AddOrUpdateCableDialogBox(cableName,cableGrid);
		addFiberLink.center();
		addFiberLink.show();
	}

	// =======光缆段操作 光缆段按钮相关操作事件=====
	@UiHandler("addFiberPart")
	// 添加光缆段事件
	void onAddFiberPart(ClickEvent event) {
		int rowIndex = cableGrid.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		TableRowElement rowElement = cableGrid.getRowElement(rowIndex);
		String cableCode = rowElement.getCells().getItem(1).getInnerText();
		AddOrUpdateCableSpan addCableSpan = new AddOrUpdateCableSpan(cableCode,fiberPartTable);
		addCableSpan.center();
		addCableSpan.show();
	}

	@UiHandler("deleteFiberPart")
	// 删除光缆段事件：同删除光缆
	void onDeleteFiberPart(ClickEvent event) {
		int rowIndex = fiberPartTable.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		TableRowElement rowElement = fiberPartTable.getRowElement(rowIndex);
		String code = rowElement.getCells().getItem(1).getInnerText();
		cableService.deleteCableSpan(code, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("删除失败！");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result.booleanValue()) {
					Window.alert("删除成功！");
					updateFiberPartTable();
				} else {
					Window.alert("删除失败！");
				}
			}
		});
	}

	@UiHandler("updateFiberPart")
	// 修改光缆段事件：同修改光缆
	void onUpdateClickEvent(ClickEvent event) {
		int rowIndex = fiberPartTable.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		TableRowElement rowElement = fiberPartTable.getRowElement(rowIndex);
		String code = rowElement.getCells().getItem(1).getInnerText();
		// 获取数据，填充，修改，提交
		AddOrUpdateCableSpan addCableSpan = new AddOrUpdateCableSpan(code,"span",fiberPartTable);// "span"字段是为标识为光缆段参数，避免与光缆混淆
		addCableSpan.center();
		addCableSpan.show();
	}

	// =======纤芯管理======
	@UiHandler("addFiber")
	void onSaveFiberClick(ClickEvent event){
		int rowIndex = fiberPartTable.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		TableRowElement rowElement = fiberPartTable.getRowElement(rowIndex);
		String fiberPartCode = rowElement.getCells().getItem(1).getInnerText();
		FiberInfo fiberInfo = new FiberInfo(fiberPartCode,fiberTable);
		fiberInfo.center();
		fiberInfo.show();
	}
	
	@UiHandler("updateFiber")
	void onUpdateFiberClick(ClickEvent event){
		int rowIndex = fiberPartTable.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		TableRowElement rowElement = fiberPartTable.getRowElement(rowIndex);
		String code = rowElement.getCells().getItem(1).getInnerText();
		// 获取数据，填充，修改，提交
		FiberInfo fiberInfo = new FiberInfo(code,"update",fiberTable);// "update"字段是为标识为更新
		fiberInfo.center();
		fiberInfo.show();
	}
	
	@UiHandler("deleteFiber")
	void onDeleteFiberClick(ClickEvent event){
		int rowIndex = fiberTable.getKeyboardSelectedRow();
		if (rowIndex == -1) {
			return;
		}
		TableRowElement rowElement = fiberTable.getRowElement(rowIndex);
		String code = rowElement.getCells().getItem(1).getInnerText();
		cableService.deleteFiber(code, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("删除失败！");
			}
			@Override
			public void onSuccess(Boolean result) {
				if (result.booleanValue()) {
					Window.alert("删除成功！");
					updateFiberTable();
				} else {
					Window.alert("删除失败！");
				}
			}
		});
	}
	
	private void updateCableTable(){
		cableService.getCables(new AsyncCallback<List<Cable>>() {
			@Override
			public void onSuccess(List<Cable> result) {
				if (result == null) {
					return;
				}
				// 为表格添加数据：数据列表List类型
				cableGrid.setRowCount(result.size(), true);
				cableGrid.setRowData(0, result);// 从0开始添加数据
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取光缆信息失败！");
				return;
			}
		});
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
	
	private void updateFiberTable(){
		cableService.getAllFibers(new AsyncCallback<List<Fiber>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！");
				return;
			}

			@Override
			public void onSuccess(List<Fiber> result) {
				if (result != null) {
					fiberTable.setRowCount(result.size());
					fiberTable.setRowData(0, result);
				}
			}
		});
	}
	
	@UiHandler("close")
	public void onCloseClick(ClickEvent event){
		this.hide();
	}
}
