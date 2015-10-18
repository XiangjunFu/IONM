package com.wanma.client.dev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.extjs.gxt.ui.client.widget.BoxComponent;
import com.extjs.gxt.ui.client.widget.Label;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.domain.Port;
import com.wanma.domain.TerminalUnit;

public class CabinetPanel {

	private int width; // 500
	private int height;//
	private int firstSlotX; // 219px
	private int firstSlotY;// 119px
	private int slotWidth;//
	private int slotHeight;// 20px
	private int slotdNum;//
	private int slotNumInFrame; //
	private String cabinetType;
	private String deviceID; // device code
	private List<BoardWin> boardList;
	public HorizontalPanel devicePanel;
	public Canvas canvas;
	public Context2d context;
	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);

	public static Image fdtImg;
	public static Image odfImg;
	public static Image obdImg;
	static {
		fdtImg = new Image();
		fdtImg.setUrl("./images/iFDTBK.png");
		odfImg = new Image();
		odfImg.setUrl("./images/iODFBK.png");
		obdImg = new Image();
		obdImg.setUrl("./images/iOBDBK.png");
	}

	private Timer timer = new Timer() {
		@Override
		public void run() {
			drawSlot(context);
		}
	};

	public void getSlotStatus() {
		opticalDeviceService.getBoardsByHostDeviceID(deviceID,"2",
				new AsyncCallback<List<TerminalUnit>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<TerminalUnit> result) {
						boardList.clear();
						for (Iterator<TerminalUnit> iterator = result
								.iterator(); iterator.hasNext();) {
							TerminalUnit boardTmp = iterator.next();
							String boardID = boardTmp.getTerminalUnitCode();
							int index = Integer.parseInt(boardTmp
									.getTerminalUnitSeq());
							int portNum = 12;
							int xPosition = firstSlotX;
							int yPosition = firstSlotY + (index - 1)
									* slotHeight;
							BoardWin boardWinTmp = new BoardWin(index, boardID,
									portNum, xPosition, yPosition);
							boardList.add(boardWinTmp);
						}
					}
				});
	}

	public HorizontalPanel getDevicePanel(String cabinetType, String deviceID) {

		this.cabinetType = cabinetType;
		boardList = new ArrayList<BoardWin>();
		if (cabinetType.equalsIgnoreCase("iFDT")) {
			width = 500;
			height = 750;
			firstSlotX = 204;
			firstSlotY = 118;
			slotWidth = 204;
			slotHeight = 18;
			slotdNum = 24;
			slotNumInFrame = 12;
		} else {
			width = 500;
			height = 750;
			firstSlotX = 204;
			firstSlotY = 118;
			slotWidth = 204;
			slotHeight = 18;
			slotdNum = 24;
			slotNumInFrame = 12;
		}
		this.deviceID = deviceID;
		devicePanel = new HorizontalPanel();
		devicePanel.setHeight("800px");
		devicePanel.setWidth("1000px");
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			return null;
		}
		canvas.setStyleName("mainCanvas");
		canvas.setWidth(width + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceHeight(height);
		context = canvas.getContext2d();
		canvas.setVisible(true);
		devicePanel.add(canvas);
		if (cabinetType.equalsIgnoreCase("iFDT")) {
			context.drawImage(ImageElement.as(fdtImg.getElement()), 0, 0);
		}
		timer.schedule(1000);
		timer.scheduleRepeating(5000);// 设置为6秒扫一次
		timer.run();

		canvas.addClickHandler(handler);

		return devicePanel;
	}

	public void drawSlot(Context2d mycontext) {
		
		mycontext.setFillStyle("#578A7E");
		mycontext.fillRect(firstSlotX, firstSlotY, slotWidth, slotHeight*17);
		getSlotStatus();
		for (Iterator<BoardWin> iterator = boardList.iterator(); iterator
				.hasNext();) {
			BoardWin board = iterator.next();
			board.drawSelf(mycontext);
		}
	}

	// test if it hit a board ?
	public BoardWin hitBoardWin(int x, int y) {
		for (Iterator<BoardWin> iterator = boardList.iterator(); iterator
				.hasNext();) {
			BoardWin board = iterator.next();
			int bWidth = board.boardImg.getWidth();
			int bHeight = board.boardImg.getHeight();
			if ((x >= board.xPosition && x <= board.xPosition + bWidth)
					&& (y >= board.yPosition && y <= board.yPosition + bHeight))
				return board;
		}
		return null;
	}
	
	private ClickHandler handler = new ClickHandler() {
		public void onClick(ClickEvent event) {
			int mx = event.getX();
			int my = event.getY();
			final BoardWin bd;
			bd = hitBoardWin(mx, my);
			if (bd != null) {
				//
				opticalDeviceService.getBoardByBoardCode(bd.boardID,
						new AsyncCallback<TerminalUnit>() {
							String boardInfo;
							@Override
							public void onFailure(Throwable caught) {
								Window.alert(" Communicating with Server failed ");
							}
							@Override
							public void onSuccess(TerminalUnit result) {
								final DialogBox boardInfoDlg=new DialogBox();
								boardInfoDlg.setHeight("300px");
								boardInfoDlg.setSize("400px", "300px");
								
								boardInfo = "板卡名称:"
										+ result.getTerminalUnitName()
										+ "  " + "板卡编码:"
										+ result.getTerminalUnitCode()
										+ "  " + "子框编号:"
										+ result.getFrameNum() ;
								Label boardInfoLa=new Label(boardInfo);
								boardInfoLa.setSize("300px", "20px");
								boardInfoLa.setPagePosition(20, 20);
								boardInfoDlg.add(boardInfoLa);
								
								opticalDeviceService.getPortsByBoardCode(
										bd.boardID,"",
										new AsyncCallback<List<Port>>() {
											@Override
											public void onFailure(
													Throwable caught) {
												Window.alert("read data err!");
											}

											@Override
											public void onSuccess(
													List<Port> result) {
												int i=0;
												for (Iterator<Port> iterator = result
														.iterator(); iterator
														.hasNext();) {
													Port tmPort = iterator
															.next();
													boardInfo ="端口:"
															+ tmPort.getPortNo()
															+ "  "
															+ "端口名:"
															+ tmPort.getName()
															+ "  "
															+ "端口状态:"
															+ tmPort.getServiceStatue();
													Label boardInfoLa=new Label(boardInfo);
													boardInfoLa.setSize("300px", "20px");
													boardInfoLa.setPagePosition(20, 20+(++i*20));
													boardInfoDlg.add(boardInfoLa);		
												}
												//Window.alert(boardInfo);
												Button okB=new Button("关闭窗口");
												okB.addClickHandler(new ClickHandler(){

													@Override
													public void onClick(
															ClickEvent event) {
														boardInfoDlg.hide();
														
													}
													
												});
												boardInfoDlg.add(okB);
												boardInfoDlg.show();
											}

										});
							}
						});

			}
		}
	};
}
