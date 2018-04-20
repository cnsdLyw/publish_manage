package com.litc.service;

import java.util.List;

import com.litc.model.DataModel;

public interface DataModelService {
	
	public void getTableData();

	public List<DataModel> getTableColumns(String tableName);
	
}
