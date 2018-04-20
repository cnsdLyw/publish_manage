package com.litc.system.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.security.model.BaseId;
import com.litc.security.repository.IComponent;

@Entity
@Table(name = IComponent.STORAGE_DEVICE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class StorageDevice extends BaseId {

	private String ftpName;
	private String ftpCode;
	private String localFolderPath;
	private String ftpUrl;
	//private int ftpPort;
	private String ftpUser;
	private String ftpPassword;
	private String remark;

	public String getFtpName() {
		return ftpName;
	}

	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}

	public String getFtpCode() {
		return ftpCode;
	}

	public void setFtpCode(String ftpCode) {
		this.ftpCode = ftpCode;
	}

	public String getLocalFolderPath() {
		return localFolderPath;
	}

	public void setLocalFolderPath(String localFolderPath) {
		this.localFolderPath = localFolderPath;
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
/*
	public int getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(int ftpPort) {
		this.ftpPort = ftpPort;
	}
*/
	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
