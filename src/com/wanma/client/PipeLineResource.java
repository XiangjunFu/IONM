package com.wanma.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;

public class PipeLineResource extends Composite {

	private static PipeLineResourceUiBinder uiBinder = GWT
			.create(PipeLineResourceUiBinder.class);

	@UiField(provided=true) CellTable<Object> cellTable = new CellTable<Object>();
	@UiField Button test;

	interface PipeLineResourceUiBinder extends
			UiBinder<Widget, PipeLineResource> {
	}

	public PipeLineResource() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
