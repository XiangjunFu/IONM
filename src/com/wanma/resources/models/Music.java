/*
 * Sencha GXT 2.3.0 - Sencha for GWT
 * Copyright(c) 2007-2013, Sencha, Inc.
 * licensing@sencha.com
 * 
 * http://www.sencha.com/products/gxt/license/
 */
 package com.wanma.resources.models;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

public class Music extends BaseTreeModel {

  public Music() {

  }

  public Music(String name) {
    set("name", name);
  }

  public Music(String name, String author, String genre) {
    set("name", name);
    set("author", author);
    set("genre", genre);
  }

  public String getName() {
    return (String) get("name");
  }

  public String getAuthor() {
    return (String) get("author");
  }

  public String getGenre() {
    return (String) get("genre");
  }

  public String toString() {
    return getName();
  }
}
