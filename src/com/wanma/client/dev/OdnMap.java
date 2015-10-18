package com.wanma.client.dev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wanma.client.devspares.FiberSpanInfo;
import com.wanma.client.devspares.IFDTInfo;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.domain.CableSpan;
import com.wanma.domain.DeviceCommonInfo;

public class OdnMap {

	private SplitLayoutPanel parent = null;

	private boolean isDraging = false;
	private boolean mouseDown = false;
	private static int mx = 0;
	private static int my = 0; // current mouse position
	private static double threshold = 5; //
	private String btnName = ""; // current pressed button's name
	private Canvas canvas;
	private Context2d context;

	private Image connectorImg;

	private int canvasX; // the relative position of canvas to parent window
	private int canvasY;

	private ImageElement odfImgElement;

	private List<DeviceIcon> deviceList; // ����豸ͼ��
	private List<Connector> conList; // ���������
	private List<DeviceIcon> unDeploiedDeviceList;
	private Connector choicedConnector;
	private DeviceIcon choicedDevice = null;
	private DeviceIcon oldChoicedDevice = null;
	private PopupPanel pop;
	private int re;

	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);
	private CableServiceAsync cableService = GWT.create(CableService.class);

	public DeviceIcon hitDevice(int x, int y) {
		if (deviceList == null)
			return null;
		for (Iterator<DeviceIcon> iterator = deviceList.iterator(); iterator
				.hasNext();) {
			DeviceIcon temp = iterator.next();
			if (temp.isHit(x, y))
				return temp;
		}
		return null;
	}

	// is the click mouse pickup a connector line?,yes return a connector
	public Connector hitConnector(int x, int y) {
		if (conList == null)
			return null;
		for (Iterator<Connector> iterator = conList.iterator(); iterator.hasNext();) {
			Connector temp = iterator.next();
			int xMax;
			int xMin;
			int yMax;
			int yMin;
			if (temp.aX >= temp.zX) {
				xMax = temp.aX;
				xMin = temp.zX;
			} else {
				xMax = temp.zX;
				xMin = temp.aX;
			}
			if (temp.aY >= temp.zY) {
				yMax = temp.aY;
				yMin = temp.zY;
			} else {
				yMax = temp.zY;
				yMin = temp.aY;
			}
			//
			if (x <= xMax && x >= xMin && y <= yMax && y >= yMax) {
				//
				double dla = Math.sqrt((x - temp.aX) ^ 2 + (y - temp.aY) ^ 2)
						+ Math.sqrt((x - temp.zX) ^ 2 + (y - temp.zY) ^ 2)
						- temp.length;
				if (dla < OdnMap.threshold) {
					return temp;
				}
			} else {
				continue;
			}
		}
		return null;
	}

	public void load() {
		opticalDeviceService
				.getAllDeviceInfo(new AsyncCallback<List<DeviceCommonInfo>>() {
					@Override
					public void onFailure(Throwable caught) {
						return;
					}

					@Override
					public void onSuccess(List<DeviceCommonInfo> result) {
						deviceList.clear();
						unDeploiedDeviceList.clear();
						for (Iterator<DeviceCommonInfo> iterator = result
								.iterator(); iterator.hasNext();) {
							DeviceCommonInfo temp = iterator.next();
							int x = temp.getXposition();
							int y = temp.getYposition();
							DeviceIcon tempIcon = new DeviceIcon(x, y, temp
									.getDeviceType());
							tempIcon.setId(temp.getDeviceCode());
							tempIcon.setDescription(temp.getDeviceName());
							if (x != 0 || y != 0) {
								deviceList.add(tempIcon);
							} else {
								unDeploiedDeviceList.add(tempIcon);
							}
						}
						drawMap();
					}
				});

		// now get the cables which link devices
		cableService.getAllCableSpan(new AsyncCallback<List<CableSpan>>() {
			@Override
			public void onFailure(Throwable caught) {
				// Auto-generated method stub
				return;
			}

			@Override
			public void onSuccess(List<CableSpan> result) {
				// the following may be should write in data service using DAO
				conList.clear();
				for (Iterator<CableSpan> iterator = result.listIterator(); iterator
						.hasNext();) {
					CableSpan tempSpan = iterator.next();
					String aID = tempSpan.getAmeCode();
					String zID = tempSpan.getZmeCode();
					if (isInDeviceList(aID) && isInDeviceList(zID)) {
						if ((getDeviceIcon(aID) != null)
								&& (getDeviceIcon(zID) != null)) {
							Connector tempConnector = new Connector(
									getDeviceIcon(aID), getDeviceIcon(zID));
							conList.add(tempConnector);
						}
					}
				}
			}
		});
	}

	public DeviceIcon getDeviceIcon(String ID) {
		for (int i = 0; i < deviceList.size(); i++) {
			if (ID.equals(deviceList.get(i).getId()))
				return deviceList.get(i);
		}
		return null;
	}

	// judge if the device is in the list.
	public boolean isInDeviceList(String ID) {
		for (int i = 0; i < deviceList.size(); i++) {
			if (ID.equals(deviceList.get(i).getId()))
				return true;
		}
		return false;
	}

	// redraw the whole map
	public void drawMap() {
		if (deviceList == null)
			return;
		if (context == null)
			return;
		context.clearRect(1, 1, canvas.getCoordinateSpaceWidth() - 2,
				canvas.getCoordinateSpaceHeight() - 2);
		for (Iterator<DeviceIcon> iterator = deviceList.iterator(); iterator
				.hasNext();) {
			DeviceIcon temp = iterator.next();
			temp.drawSelf(context);
		}
		for (Iterator<Connector> iterator = conList.iterator(); iterator
				.hasNext();) {
			Connector tempCon = iterator.next();
			tempCon.drawSelf(context);
		}
	}

	//
	public void clickDevice(String deviceID) {
	}

	public void setPopImg(String deviceType) {
		if (pop == null)
			return;
		pop.clear();
		if (deviceType.equalsIgnoreCase("iFDT"))
			pop.add(DeviceIcon.fdtImg);
		else if (deviceType.equalsIgnoreCase("iOBD"))
			pop.add(DeviceIcon.obdImg);
		else if (deviceType.equalsIgnoreCase("iODF"))
			pop.add(DeviceIcon.odfImg);

	}

	//
	public void drawConnector(DeviceIcon a, DeviceIcon z, boolean newline) {
		if (context == null)
			return;
		if (!newline)
			context.setGlobalCompositeOperation("xor");
		context.beginPath();
		// first judge the relative position of the two DeviceIcon,
		if (a.x >= z.x && a.y <= z.y) { // a is at z's right top position
			if ((a.y >= z.y + z.height / 2) && (a.x >= z.x + z.width / 2)) {
				context.moveTo(z.x + z.width / 2, z.y);
				context.lineTo(a.x, a.y + a.height / 2);
			} else if ((a.y <= z.y - z.height / 2) && (a.x >= z.x + z.width)) {
				context.moveTo(z.x + z.width, z.y + z.height / 2);
				context.lineTo(a.x, a.y + a.height / 2);
			} else {
				context.moveTo(z.x + z.width / 2, z.y);
				context.lineTo(a.x + a.width / 2, a.y + a.height);
			}

		} else if (z.x >= a.x && z.y <= a.y) {// z is at a's right top positon
			if ((a.y >= a.y + z.height / 2) && (z.x >= a.x + a.width / 2)) {
				context.moveTo(a.x + a.width / 2, a.y);
				context.lineTo(z.x, z.y + a.height / 2);
			} else if ((z.y <= a.y - a.height / 2) && (z.x >= a.x + a.width)) {
				context.moveTo(a.x + a.width, a.y + a.height / 2);
				context.lineTo(z.x, z.y + z.height / 2);
			} else {
				context.moveTo(a.x + a.width / 2, a.y);
				context.lineTo(z.x + z.width / 2, z.y + z.height);
			}
		} else if ((a.x <= z.x) && (a.y <= z.y)) {
			if ((a.y >= z.y + z.height / 2) && (a.x <= z.x - z.width / 2)) {
				context.moveTo(a.x + a.width, a.y + a.height / 2);
				context.lineTo(z.x + z.width / 2, z.y);
			} else if (a.x > z.x - z.width / 2) {
				context.moveTo(a.x + a.width / 2, a.y + a.height);
				context.lineTo(z.x + z.width / 2, z.y);
			} else {
				context.moveTo(a.x + a.width, a.y + a.height / 2);
				context.lineTo(z.x, z.y + z.height / 2);
			}

		} else {

			if ((z.y >= a.y + a.height / 2) && (z.x <= a.x - a.width / 2)) {
				context.moveTo(z.x + z.width, z.y + z.height / 2);
				context.lineTo(a.x + a.width / 2, a.y);
			} else if (z.x > a.x - a.width / 2) {
				context.moveTo(z.x + z.width / 2, z.y + z.height);
				context.lineTo(a.x + a.width / 2, a.y);
			} else {
				context.moveTo(z.x + z.width, z.y + z.height / 2);
				context.lineTo(a.x, a.y + a.height / 2);
			}
		}
		context.setLineWidth(1);
		context.setFillStyle("green");
		context.stroke();
		context.setGlobalCompositeOperation("source-over");
	}

	public DockLayoutPanel get(SplitLayoutPanel split) {

		this.parent = split;

		final DockLayoutPanel myOdnMap = new DockLayoutPanel(Unit.PX);
		myOdnMap.setHeight("800px");
		pop = new PopupPanel();
		pop.unsinkEvents(Event.ONMOUSEDOWN | Event.ONMOUSEMOVE
				| Event.ONMOUSEUP);
		deviceList = new ArrayList<DeviceIcon>();
		unDeploiedDeviceList = new ArrayList<DeviceIcon>();
		conList = new ArrayList<Connector>();
		VerticalPanel verticalIconPanel = new VerticalPanel();

		DeviceIcon tempDevice = new DeviceIcon(0, 0, "iFDT");

		Image i1 = new Image();
		i1.setUrl("./images/iFDT.png");
		Image i2 = new Image();
		i2.setUrl("./images/iODF.png");
		Image i3 = new Image();
		i3.setUrl("./images/iOBD.png");
		Image i4 = new Image();
		i4.setUrl("./images/iConnector.png");

		verticalIconPanel.add(i1);
		verticalIconPanel.add(i2);
		verticalIconPanel.add(i3);
		verticalIconPanel.add(i4);

		isDraging = false;
		mouseDown = false;
		i1.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (btnName.equalsIgnoreCase("")) {
					btnName = "iFDT";
					canvas.setFocus(true);
				} else
					btnName = "";
			}
		});
		i2.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (btnName.equalsIgnoreCase("")) {
					btnName = "iODF";
					canvas.setFocus(true);
				} else
					btnName = "";
			}
		});
		i3.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (btnName.equalsIgnoreCase("")) {
					btnName = "iOBD";
					canvas.setFocus(true);
				} else
					btnName = "";
			}
		});

		i4.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (btnName.equals("")) {
					btnName = "Connector";
					canvas.setFocus(true);
				} else
					btnName = "";
			}
		});
		myOdnMap.addWest(verticalIconPanel, 130);
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			Window.alert("Html5 in this explorer is not supported!");
			return null;
		}
		canvas.setStyleName("mainCanvas");
		int canvasWidth = 1000;
		canvas.setWidth(canvasWidth + "px");
		canvas.setCoordinateSpaceWidth(canvasWidth);
		canvas.setHeight(800 + "px");
		canvas.setCoordinateSpaceHeight(800);
		context = canvas.getContext2d();
		canvas.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				mx = event.getX();
				my = event.getY();
				oldChoicedDevice = choicedDevice;
				choicedDevice = hitDevice(mx, my);
				choicedConnector = hitConnector(mx, my);
				if (choicedDevice != null) {
					String deviceCode = choicedDevice.getId();
					opticalDeviceService.getDeviceCommonInfosByDeviceCode(
							deviceCode, deviceCommonInfoCallback);
				}
			}

		});
		canvas.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				mouseDown = true;
				if (btnName.equals("") || btnName.equals("Connector")) {
					mx = event.getX();
					my = event.getY();
					oldChoicedDevice = choicedDevice;
					choicedDevice = hitDevice(mx, my);
					choicedConnector = hitConnector(mx, my);
				}
			}
		});
		canvas.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				mx = event.getX();
				my = event.getY();
				// if is it dragging?
				isDraging = mouseDown;
				if (isDraging) {
					if (btnName.equals("")) {
						if (choicedDevice != null) {
							choicedDevice.eraseSelf(context);
							setPopImg(choicedDevice.typeName);
							canvasX = canvas.getAbsoluteLeft();
							canvasY = canvas.getAbsoluteTop();
							pop.setPopupPosition(mx + canvasX + 10, my
									+ canvasY + 10);
							pop.show();
						}
					}
				} else {
					if (!btnName.equals("") && !btnName.equals("Connector")) {
						setPopImg(btnName);
						canvasX = canvas.getAbsoluteLeft();
						canvasY = canvas.getAbsoluteTop();
						pop.setPopupPosition(mx + 10 + canvasX, my + 10
								+ canvasY);
						pop.show();
					}
				}
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				mouseDown = false;
				pop.hide();
				mx = event.getX();
				my = event.getY();
				// if dragging action is finished( release mouse)
				if (isDraging) {
					if (btnName.equals("")) {
						if (choicedDevice != null) {
							choicedDevice.x = mx;
							choicedDevice.y = my;
							choicedDevice.drawSelf(context);
							newDBPositon(choicedDevice);
							drawMap(); // redraw the map
							choicedDevice = null;
							oldChoicedDevice = null;
						}
					}
				} else {
					if (!btnName.equals("") && !btnName.equals("Connector")) {
						DeviceIcon newdevice = new DeviceIcon(mx, my, btnName);
						newdevice.drawSelf(context);

						if (Window.confirm("add a new device?")) {
							if (btnName.equalsIgnoreCase("iFDT")) {
								IFDTInfo dia = new IFDTInfo(mx, my);
								dia.show();
								if (!dia.isAddSuccess) {
									Window.alert("fail to add new device!");
									newdevice.eraseSelf(context);
								} else {
									deviceList.add(newdevice);
								}
							}
						} else if (Window
								.confirm("just depoly existed device to the topgraph map?")) {

						} else {
							newdevice.eraseSelf(context);
						}
						choicedDevice = null;
						oldChoicedDevice = null;
					} else { // draw a line
						pop.hide();
						if ((choicedDevice != null)
								&& (oldChoicedDevice != null)
								&& (choicedDevice != oldChoicedDevice)) {
							Connector connector1 = new Connector(choicedDevice,
									oldChoicedDevice);

							FiberSpanInfo addCableSpanDlg = new FiberSpanInfo(
									oldChoicedDevice, choicedDevice);
							addCableSpanDlg.show();
							if (addCableSpanDlg.addCableSpanSuccess) {
								drawConnector(choicedDevice, oldChoicedDevice,
										true);
								conList.add(connector1);
							} else {

							}

							drawConnector(choicedDevice, oldChoicedDevice, true);
							conList.add(connector1);
							choicedDevice = null;
							oldChoicedDevice = null;
						}
					}
				}
				isDraging = false;
				btnName = "";
			}
		});
		canvas.setVisible(true);
		myOdnMap.add(canvas);
		canvasX = canvas.getAbsoluteLeft();
		canvasY = canvas.getAbsoluteTop();
		context.strokeRect(0, 0, canvas.getCoordinateSpaceWidth(),
				canvas.getCoordinateSpaceHeight());
		load();
		return myOdnMap;
	}

	private AsyncCallback<DeviceCommonInfo> deviceCommonInfoCallback = new AsyncCallback<DeviceCommonInfo>() {
		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(DeviceCommonInfo result) {
			if (result != null) {
				CabinetPanel panel = new CabinetPanel();
				HorizontalPanel horizontalPanel = panel.getDevicePanel(
						result.getDeviceType(), result.getDeviceCode());
				// parent.remove(2);
				// parent.remove(3);
				int index = parent.getWidgetCount();
				if (index > 2) {
					parent.remove(2);
					parent.addEast(horizontalPanel, 500);
				} else {
					parent.addEast(horizontalPanel, 500);
				}
			}
		}
	};

	public int newDBPositon(DeviceIcon device) {
		opticalDeviceService.upDatePosition(device.getId(), device.x, device.y,
				new AsyncCallback<Integer>() {
					@Override
					public void onFailure(Throwable caught) {
						re = -1;
					}

					@Override
					public void onSuccess(Integer result) {
						re = result;
					}
				});
		return re;
	}

}
