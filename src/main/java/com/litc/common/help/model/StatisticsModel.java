package com.litc.common.help.model;

import java.util.Date;

public class StatisticsModel {
	private Integer buyNum;// 采购数量
	private Integer sellNum;// 销售数量
	private Integer inventoryNum;// 库存数量
	private Integer borrowNum;
	private Integer num;
	private Date date;
	private String month;
	private String orgCode;
	private String orgName;
	private String province;
	
	public StatisticsModel() {
		super();
	}

	
	public StatisticsModel(Integer borrowNum, Date date, String month) {
		super();
		this.borrowNum = borrowNum;
		this.date = date;
		this.month = month;
	}


	public StatisticsModel(Integer sellNum, Date date, String orgCode,
			String orgName) {
		super();
		this.sellNum = sellNum;
		this.date = date;
		this.orgCode = orgCode;
		this.orgName = orgName;
	}


	public StatisticsModel(Integer buyNum, Integer sellNum,
			Integer inventoryNum, String month) {
		super();
		this.buyNum = buyNum;
		this.sellNum = sellNum;
		this.inventoryNum = inventoryNum;
		this.month = month;
	}

	
	public StatisticsModel(Integer num, String month, Date date) {
		super();
		this.num = num;
		this.date = date;
		this.month = month;
	}


	public StatisticsModel(Integer borrowNum, String province, String orgCode) {
		super();
		this.borrowNum = borrowNum;
		this.province = province;
		this.orgCode = orgCode;
	}

	public StatisticsModel(Integer buyNum, Integer sellNum,
			Integer inventoryNum, String orgCode, String province) {
		super();
		this.buyNum = buyNum;
		this.sellNum = sellNum;
		this.inventoryNum = inventoryNum;
		this.orgCode = orgCode;
		this.province = province;
	}


	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public Integer getInventoryNum() {
		return inventoryNum;
	}

	public void setInventoryNum(Integer inventoryNum) {
		this.inventoryNum = inventoryNum;
	}
	
	public Integer getBorrowNum() {
		return borrowNum;
	}

	public void setBorrowNum(Integer borrowNum) {
		this.borrowNum = borrowNum;
	}

	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
}
