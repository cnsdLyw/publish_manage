package com.litc.security.common.page;

/**
 * 分页查询列和数据对象
 * 
 * @author liyw
 * 
 */
public class PageParam {

	private String searchColumn;
	private String searchValue;
	private String searchType;

	public PageParam() {
		super();
	}

	public PageParam(String searchColumn, String searchValue) {
		super();
		this.searchColumn = searchColumn;
		this.searchValue = searchValue;
	}

	public PageParam(String searchColumn, String searchValue, String searchType) {
		super();
		this.searchColumn = searchColumn;
		this.searchValue = searchValue;
		this.searchType = searchType;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

}
