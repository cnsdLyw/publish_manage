package com.litc.common.help.model;

public class StatisticsExtModel {
	private long orderNum;// 采购数量
	private long sellNum;// 销售数量
	private long stockNum;// 库存数量
	private long returnNum;// 退货数量
	private long borrowNum;// 借阅数量
	private long num;
	private String orgCode;
	private String orgName;
	private String province;

	public StatisticsExtModel() {
		super();
	}

	public StatisticsExtModel(long num, String orgCode, String orgName) {
		super();
		this.num = num;
		this.orgCode = orgCode;
		this.orgName = orgName;
	}

	public StatisticsExtModel(long num, String orgCode, String orgName,
			String province) {
		super();
		this.num = num;
		this.orgCode = orgCode;
		this.orgName = orgName;
		this.province = province;
	}

	public StatisticsExtModel(long orderNum, long sellNum, long stockNum,
			long returnNum, long borrowNum, String orgCode, String province) {
		super();
		this.orderNum = orderNum;
		this.sellNum = sellNum;
		this.stockNum = stockNum;
		this.returnNum = returnNum;
		this.borrowNum = borrowNum;
		this.orgCode = orgCode;
		this.province = province;
	}

	public long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}

	public long getSellNum() {
		return sellNum;
	}

	public void setSellNum(long sellNum) {
		this.sellNum = sellNum;
	}

	public long getStockNum() {
		return stockNum;
	}

	public void setStockNum(long stockNum) {
		this.stockNum = stockNum;
	}

	public long getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(long returnNum) {
		this.returnNum = returnNum;
	}

	public long getBorrowNum() {
		return borrowNum;
	}

	public void setBorrowNum(long borrowNum) {
		this.borrowNum = borrowNum;
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
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
