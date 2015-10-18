/*
 * Sencha GXT 2.3.0 - Sencha for GWT
 * Copyright(c) 2007-2013, Sencha, Inc.
 * licensing@sencha.com
 * 
 * http://www.sencha.com/products/gxt/license/
 */
package com.wanma.resources.models;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

public class Folder extends BaseTreeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2678299983158690005L;
	private static int ID = 0;

	public Folder() {
		set("id", ID++);
	}

	public Folder(String name) {
		set("id", ID++);
		set("name", name);
	}

	public Folder(String name,String type,String code){
		set("id",ID++);
		set("name",name);
		set("type",type);
		set("code",code);
	}
	
	public Folder(String name, BaseTreeModel[] children) {
		this(name);
		for (int i = 0; i < children.length; i++) {
			add(children[i]);
		}
	}

	public Folder(String name,String code,String type,BaseTreeModel[] children){
		this(name,type,code);
		for (int i = 0; i < children.length; i++) {
			add(children[i]);
		}
	}
	
	public Integer getId() {
		return (Integer) get("id");
	}

	public String getName() {
		return (String) get("name");
	}

	public String toString() {
		return getName();
	}
	
	public String getType(){
		return (String)get("type");
	}
	
	public String getCode(){
		return (String)get("code");
	}

}
