package com.litc.security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * 供应商Model
 * 
 */


@Entity
@Table(name = "sly_supplier")
@SuppressWarnings("unused")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Supplier extends BaseId {
	private String companyName;// 公司名称
	private String supervisor;// 主管人
	private String supervisorPhone;// 主管人电话
	private String businessContacts;// //业务联系人
	private String businessContactsPhone;// //业务联系人电话
	private String businessContactsEmail;// //业务联系人邮箱
	private String industryClassification;// //行业分类
	private String province;// 省
	private String city;// 市
	private String county;// 县
	private String address;// //供应商地址
	private String postalCode;// //邮编
	private String website;// //网址
	private String telephone;// //公司电话
	private String fax;// //公司传真
	private String idCardObverse;// 主管人身份证正面附件路径
	private String idCardObverseName;// 主管人身份证正面附件名称
	private String idCardReverse;// 主管人身份证反面附件路径
	private String idCardReverseName;// 主管人身份证反面附件名称
	private String businessLicenceCopy;// 企业营业执照副本附件路径
	private String businessLicenceCopyName;// 企业营业执照副本附件名称
	private String certificateAuthorization;// 授权书附件路径
	private String certificateAuthorizationName;// 授权书附件名称
	private Integer status;//状态
	private String remark;//备注简介
	private Date lastModifyTime;//修改时间

	@Column(name = "company_name", length=100)
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "supervisor", length=30)
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	@Column(name = "supervisor_phone", length=16)
	public String getSupervisorPhone() {
		return supervisorPhone;
	}

	public void setSupervisorPhone(String supervisorPhone) {
		this.supervisorPhone = supervisorPhone;
	}

	@Column(name = "business_contacts", length=32)
	public String getBusinessContacts() {
		return businessContacts;
	}

	public void setBusinessContacts(String businessContacts) {
		this.businessContacts = businessContacts;
	}

	@Column(name = "business_contacts_phone", length=16)
	public String getBusinessContactsPhone() {
		return businessContactsPhone;
	}

	public void setBusinessContactsPhone(String businessContactsPhone) {
		this.businessContactsPhone = businessContactsPhone;
	}

	@Column(name = "business_contacts_email", length=50)
	public String getBusinessContactsEmail() {
		return businessContactsEmail;
	}

	public void setBusinessContactsEmail(String businessContactsEmail) {
		this.businessContactsEmail = businessContactsEmail;
	}
	
	@Column(name = "industry_classification", length=60)
	public String getIndustryClassification() {
		return industryClassification;
	}

	public void setIndustryClassification(String industryClassification) {
		this.industryClassification = industryClassification;
	}

	@Column(name = "province", length=20)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city", length=20)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "county", length=20)
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
	
	@Column(name = "address", length=100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "postal_code", length=16)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	@Column(name = "website", length=100)
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "telephone", length=16)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "fax", length=16)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "id_card_obverse", length=255)
	public String getIdCardObverse() {
		return idCardObverse;
	}

	public void setIdCardObverse(String idCardObverse) {
		this.idCardObverse = idCardObverse;
	}

	@Column(name = "id_card_obverse_name", length=255)	
	public String getIdCardObverseName() {
		return idCardObverseName;
	}

	public void setIdCardObverseName(String idCardObverseName) {
		this.idCardObverseName = idCardObverseName;
	}

	@Column(name = "id_card_reverse", length=255)
	public String getIdCardReverse() {
		return idCardReverse;
	}

	public void setIdCardReverse(String idCardReverse) {
		this.idCardReverse = idCardReverse;
	}

	@Column(name = "id_card_reverse_name", length=255)
	public String getIdCardReverseName() {
		return idCardReverseName;
	}

	public void setIdCardReverseName(String idCardReverseName) {
		this.idCardReverseName = idCardReverseName;
	}

	@Column(name = "business_licence_copy", length=255)
	public String getBusinessLicenceCopy() {
		return businessLicenceCopy;
	}

	public void setBusinessLicenceCopy(String businessLicenceCopy) {
		this.businessLicenceCopy = businessLicenceCopy;
	}
	
	@Column(name = "business_licence_copy_name", length=255)
	public String getBusinessLicenceCopyName() {
		return businessLicenceCopyName;
	}

	public void setBusinessLicenceCopyName(String businessLicenceCopyName) {
		this.businessLicenceCopyName = businessLicenceCopyName;
	}

	@Column(name = "certificate_authorization", length=255)
	public String getCertificateAuthorization() {
		return certificateAuthorization;
	}

	public void setCertificateAuthorization(String certificateAuthorization) {
		this.certificateAuthorization = certificateAuthorization;
	}
	
	@Column(name = "certificate_authorization_name", length=255)
	public String getCertificateAuthorizationName() {
		return certificateAuthorizationName;
	}

	public void setCertificateAuthorizationName(String certificateAuthorizationName) {
		this.certificateAuthorizationName = certificateAuthorizationName;
	}

	@Column(name = "status", length=2)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "remark", length=500)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "last_modify_time")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	
}
