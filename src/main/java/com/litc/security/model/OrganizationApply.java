package com.litc.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.security.repository.IComponent;

@Entity
@Table(name = IComponent.ORGANIZATION_APPLY)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OrganizationApply extends BaseId{

	private String orgName;
	private String orgCode;
	private String beforeCode;//临时机构代码
	private String loginName;
	private String orgType;
	private String postalcode;
	private String telephone;
	private String orgContacter;
	private String orgContactPhone;
	private String orgContactEmail;
	private String province;
	private String city;
	private String county;
	private String orgAddress;
	private Date modifyTime;
	private Integer orgStatus;
	private String firstOrgName;
	private String secondOrgName;
	private String url;
	private String orgContent;//机构简介
	private String isbn;//isbn
	private String upperCode;//上级机构码
	private String orgWebsit;//网址
	private String orgEconomic;//经济类型
	private String passWord;//用户密码
	private String ftpName;//ftp用户名
	private String ftpAddress;//ftp地址
	private String ftpPassWord;//ftp密码
	
	public String getBeforeCode() {
		return beforeCode;
	}

	public void setBeforeCode(String beforeCode) {
		this.beforeCode = beforeCode;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getFtpName() {
		return ftpName;
	}

	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}

	public String getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(String ftpAddress) {
		this.ftpAddress = ftpAddress;
	}

	public String getFtpPassWord() {
		return ftpPassWord;
	}

	public void setFtpPassWord(String ftpPassWord) {
		this.ftpPassWord = ftpPassWord;
	}

	public String getOrgWebsit() {
		return orgWebsit;
	}

	public void setOrgWebsit(String orgWebsit) {
		this.orgWebsit = orgWebsit;
	}

	public String getOrgEconomic() {
		return orgEconomic;
	}

	public void setOrgEconomic(String orgEconomic) {
		this.orgEconomic = orgEconomic;
	}

	public String getUpperCode() {
		return upperCode;
	}

	public void setUpperCode(String upperCode) {
		this.upperCode = upperCode;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	public String getOrgContent() {
		return orgContent;
	}

	public void setOrgContent(String orgContent) {
		this.orgContent = orgContent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFirstOrgName() {
		return firstOrgName;
	}

	public void setFirstOrgName(String firstOrgName) {
		this.firstOrgName = firstOrgName;
	}

	public String getSecondOrgName() {
		return secondOrgName;
	}

	public void setSecondOrgName(String secondOrgName) {
		this.secondOrgName = secondOrgName;
	}
	
	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getOrgContacter() {
		return orgContacter;
	}

	public void setOrgContacter(String orgContacter) {
		this.orgContacter = orgContacter;
	}

	public String getOrgContactPhone() {
		return orgContactPhone;
	}

	public void setOrgContactPhone(String orgContactPhone) {
		this.orgContactPhone = orgContactPhone;
	}

	public String getOrgContactEmail() {
		return orgContactEmail;
	}

	public void setOrgContactEmail(String orgContactEmail) {
		this.orgContactEmail = orgContactEmail;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Integer getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(Integer orgStatus) {
		this.orgStatus = orgStatus;
	}

}
