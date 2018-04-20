package com.litc.security.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.litc.security.repository.IComponent;

@Entity
@Table(name = IComponent.SYS_ORGANIZATION)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Organization {
	private String orgID;
	private String parentId;
	private String orgName;
	private String orgCode;
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
	private Set<User> users ;
	private Date modifyTime;
	private String url;//机构图片
	private String orgContent;//机构简介
	private String upperCode;//上级机构码
	private String orgWebsit;//网址
	private String orgEconomic;//经济类型
	private String loginName;//用户名
	private String passWord;//用户密码
	private String ftpName;//ftp用户名
	private String ftpAddress;//ftp地址
	private String ftpPassWord;//ftp密码
	private String isbn;//isbn
	private String firstOrgName;
	private String secondOrgName;
	private String orgServiceStatus; //机构服务状态
	private String parentName;//上级机构名称
	private String comment;//机构说明
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOrgContent() {
		return orgContent;
	}

	public void setOrgContent(String orgContent) {
		this.orgContent = orgContent;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	@Id
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="organization")
	@JsonBackReference
	public Set<User> getUsers() {
		return users;
	}
	@JsonBackReference
	public void setUsers(Set<User> users) {
		this.users = users;
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

	public String getOrgServiceStatus() {
		return orgServiceStatus;
	}

	public void setOrgServiceStatus(String orgServiceStatus) {
		this.orgServiceStatus = orgServiceStatus;
	}
}
