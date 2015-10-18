package com.wanma.client.dev;

import java.util.List;
import java.util.Iterator;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.domain.Port;

public class BoardWin {
	public static final int BUSY = 1;
	public static final int IDLE = 2;
	public static final int IN = 3;
	public static final int OUT = 4;
	public static final int ERR = 5;
	public static final int PREJUMP = 6;
	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);

	public int index; // the index in the cabinet
	public String boardID; //
	public int portNum;// the amount of ports

	public int xPosition;
	public int yPosition;

	public int firstPortX; // the first port location
	public int firstPortY;
	public Image boardImg;
	public static Image busyLedImg;
	public static Image idleLedImg;
	public static Image errLedImg;
	public static Image yellowLedImg;
	public static Image warningImg;
	public List<Port> portList;
	public int[] ports;
	public CabinetPanel parent;
	private Context2d context;
	
	public BoardWin(int index, String boardID, int portNum, int xPosition,
			int yPosition) {
		// this.firstPortX = 15;
		// this.firstPortY = 5;
		this.firstPortX = 200;
		this.firstPortY = 120;
		this.index = index;
		this.boardID = boardID;
		this.portNum = portNum;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		ports = new int[portNum];
		for (int i = 0; i < portNum; i++) {
			ports[i] = IDLE;
		}

		boardImg = new Image();
		boardImg.setUrl("./images/wanmaSlot.png");
		busyLedImg = new Image();
		busyLedImg.setUrl("./images/busyLed.png");
		idleLedImg = new Image();
		idleLedImg.setUrl("./images/idleLed.png");
		errLedImg = new Image();
		errLedImg.setUrl("./images/errLed.png");
		yellowLedImg = new Image();
		yellowLedImg.setUrl("./images/yellowLed.png");
		warningImg = new Image();
		warningImg.setUrl("./images/WarningLed.png"); 
	}
	

	public void getPortStatus() {

	}

	public void drawSelf(Context2d mycontext) {
		this.context = mycontext;
		mycontext.clearRect(xPosition, yPosition, boardImg.getWidth(), boardImg.getHeight());
		
		mycontext.drawImage(ImageElement.as(boardImg.getElement()), xPosition, yPosition);
		opticalDeviceService.getPortsByBoardCode(boardID, "",
				new AsyncCallback<List<Port>>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("read data err!");
					}

					@Override
					public void onSuccess(List<Port> result) {
						if (result != null) {
							portList = result;
							for (Iterator<Port> iterator = portList.iterator(); iterator
									.hasNext();) {
								Port tmPort = iterator.next();
								int portIndex = tmPort.getPortNo();
								String serviceStatue = tmPort
										.getServiceStatue();
								if ("2".equals(serviceStatue)) {
									ports[portIndex - 1] = BUSY;
								} else if ("1".equals(serviceStatue)) {
									ports[portIndex - 1] = IDLE;
								} else if("3".equals(serviceStatue)){
									ports[portIndex - 1] = IN;
								}else if("4".equals(serviceStatue)){
									ports[portIndex - 1] = OUT;
								}else if("5".equals(serviceStatue)){
									ports[portIndex - 1] = PREJUMP;
								}else{
									ports[portIndex - 1] = ERR;
								}
							}
							flashPort(context);
						}
					}
				});
	}

	public void flashPort(Context2d mycontext) {
		getPortStatus();
		int tempX = xPosition + 15;
		int tempY = yPosition + 4;
		for (int i = 0; i < 4; i++) {
			mycontext.clearRect(xPosition, yPosition, 8, 8);
			if (ports[i] == BUSY) {
				// mycontext.drawImage(ImageElement.as(busyLedImg.getElement()),
				// firstPortX + i * 6, firstPortY);
				mycontext.drawImage(ImageElement.as(busyLedImg.getElement()),
						tempX + i * 13, tempY);
			} else if (ports[i] == IDLE) {
				mycontext.drawImage(ImageElement.as(idleLedImg.getElement()),
						tempX + i * 13, tempY);
			} else if(ports[i] == IN){
				mycontext.drawImage(ImageElement.as(warningImg.getElement()),
						tempX + i * 13, tempY);
			}else if(ports[i] == OUT){
				mycontext.drawImage(ImageElement.as(warningImg.getElement()),
						tempX + i * 13, tempY);
			}else if(ports[i] == PREJUMP){
				mycontext.drawImage(ImageElement.as(yellowLedImg.getElement()),
						tempX + i * 13 + 0.7, tempY);
			}else{
				mycontext.drawImage(ImageElement.as(errLedImg.getElement()),
						tempX + i * 13, tempY);
			}
		}
		int xTemp = tempX + 2;
		for (int i = 4; i < portNum; i++) {
			mycontext.clearRect(xPosition, yPosition, 8, 8);
			if (ports[i] == BUSY) {
				mycontext.drawImage(ImageElement.as(busyLedImg.getElement()),
						xTemp + i * 13 + 0.7, tempY);
			} else if (ports[i] == IDLE) {
				mycontext.drawImage(ImageElement.as(idleLedImg.getElement()),
						xTemp + i * 13 + 0.7, tempY);
			} else if(ports[i] == IN){
				mycontext.drawImage(ImageElement.as(warningImg.getElement()),
						xTemp + i * 13 + 0.7, tempY);
			}else if(ports[i] == OUT){
				mycontext.drawImage(ImageElement.as(warningImg.getElement()),
						xTemp + i * 13 + 0.7, tempY);
			}else if(ports[i] == PREJUMP){
				mycontext.drawImage(ImageElement.as(yellowLedImg.getElement()),
						xTemp + i * 13 + 0.7, tempY);
			}else{
				mycontext.drawImage(ImageElement.as(errLedImg.getElement()),
						xTemp + i * 13 + 0.7, tempY);
			}
		}
	}

}
