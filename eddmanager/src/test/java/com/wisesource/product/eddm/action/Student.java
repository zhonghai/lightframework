package com.wisesource.product.eddm.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
	private String	name;
	private Map map = new HashMap();
	private List list = new ArrayList();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	
}
