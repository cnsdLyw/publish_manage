package com.litc.common.help.model;

public class ProcessModel {
	private String orgCode;
	private String orgName;
	private int status;
	private Integer mun;
	private Integer allotNum;
	private Integer processNum;
	private Integer completeNum;

	public ProcessModel() {
		super();
	}

	public ProcessModel(String orgCode, String orgName, int status, Integer mun) {
		super();
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.status = status;
		this.mun = mun;
	}
	
	public ProcessModel(Integer processNum, Integer completeNum,String orgCode, String orgName) {
		super();
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.processNum = processNum;
		this.completeNum = completeNum;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getMun() {
		return mun;
	}

	public void setMun(Integer mun) {
		this.mun = mun;
	}

	public Integer getAllotNum() {
		return allotNum;
	}

	public void setAllotNum(Integer allotNum) {
		this.allotNum = allotNum;
	}

	public Integer getProcessNum() {
		return processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
	}

	public Integer getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(Integer completeNum) {
		this.completeNum = completeNum;
	}

}
