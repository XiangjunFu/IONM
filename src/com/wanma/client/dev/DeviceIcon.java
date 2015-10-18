package com.wanma.client.dev;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

import com.google.gwt.user.client.ui.Image;

public class DeviceIcon {

	public int height; // ͼ��ĸ� ��
	public int width;
	private String deviceID;
	public int x;
	public int y; // ��ͼ�ϵ�λ��
	private String description; // ˵������
	public String typeName;// �������� �� ͼ���ļ�����ͬ
	private Image myImage;
	public static Image fdtImg;
	public static Image odfImg;
	public static Image obdImg;
	public static Image connectorImg;
	static {
		fdtImg = new Image();
		fdtImg.setUrl("./images/iFDT.png");
		odfImg = new Image();
		odfImg.setUrl("./images/iODF.png");
		obdImg = new Image();
		obdImg.setUrl("./images/iOBD.png");
		connectorImg = new Image();
		connectorImg.setUrl("./images/iConnector.png");
	}

	public DeviceIcon(int x, int y, String typeName) {
		this.x = x;
		this.y = y;
		this.typeName = typeName;
		if (typeName.equalsIgnoreCase("iFDT")) {
			width = 53;
			height = 78;

		} else if (typeName.equalsIgnoreCase("iODF")) {
			width = 53;
			height = 100;
		} else if (typeName.equalsIgnoreCase("iOBD")) {
			width = 53;
			height = 49;
		}
	}

	// �����Ƿ�ѡ�и�
	public boolean isHit(int pX, int pY) {
		if ((pX >= x && pX <= x + width) && (pY >= y && pY <= y + height))
			return true;
		else
			return false;
	}

	public void drawSelf(Context2d mycontext) {
		if (mycontext == null)
			return;
		if (typeName.equalsIgnoreCase("iFDT"))
			mycontext.drawImage(ImageElement.as(fdtImg.getElement()), x, y);
		else if (typeName.equalsIgnoreCase("iODF"))
			mycontext.drawImage(ImageElement.as(odfImg.getElement()), x, y);
		else if (typeName.equalsIgnoreCase("iOBD"))
			mycontext.drawImage(ImageElement.as(obdImg.getElement()), x, y);
		mycontext.setFillStyle("blue");
		mycontext.strokeRect(x, y, width, height);
	}

	public void eraseSelf(Context2d mycontext) {
		if (mycontext == null)
			return;
		mycontext.clearRect(x - 1, y - 1, width + 2, height + 2);
	}

	public void reLocateSelf(Context2d mycontext, int x, int y) {
		if (mycontext == null)
			return;
		mycontext.clearRect(this.x - 1, this.y - 1, width + 2, height + 2);
		this.x = x;
		this.y = y;
		drawSelf(mycontext);
	}

	// update position in database;
	public void update() {

	}

	public String getId() {
		return this.deviceID;
	}

	public void setId(String id) {
		this.deviceID = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String des) {
		this.description = des;
	}
}
