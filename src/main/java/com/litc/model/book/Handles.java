package com.litc.model.book;

import java.util.ArrayList;
import java.util.List;


public class Handles {
	 private List<Handle> handleList =  new ArrayList<Handle>();

	 
	public List<Handle> getHandleList() {
		return handleList;
	}

	public void setHandleList(List<Handle> handleList) {
		this.handleList = handleList;
	}
	 
	public void addHandleList(Handle handle) {
		this.handleList.add(handle) ;
	}
	
	
}
