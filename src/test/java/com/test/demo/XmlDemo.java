package com.test.demo;

public class XmlDemo {
	private int iNum;
	private int step;
	private String strName;
	private String parentPath;

	private String teamNum;
	private String cnonixTable;
	private String onixList;

	public XmlDemo() {
		super();
	}

	public XmlDemo(int step, String strName) {
		super();
		this.step = step;
		this.strName = strName;
	}

	public XmlDemo(int iNum, int step, String strName) {
		super();
		this.iNum = iNum;
		this.step = step;
		this.strName = strName;
	}

	public int getiNum() {
		return iNum;
	}

	public void setiNum(int iNum) {
		this.iNum = iNum;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getParentPath() {
		return parentPath;
	}

	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(String teamNum) {
		this.teamNum = teamNum;
	}

	public String getCnonixTable() {
		return cnonixTable;
	}

	public void setCnonixTable(String cnonixTable) {
		this.cnonixTable = cnonixTable;
	}

	public String getOnixList() {
		return onixList;
	}

	public void setOnixList(String onixList) {
		this.onixList = onixList;
	}

}
