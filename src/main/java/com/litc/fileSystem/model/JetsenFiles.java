package com.litc.fileSystem.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.model.BaseId;

/**
 * 文件
 */
@Entity
@Table(name = "jc_file_main")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JetsenFiles{
	private String uuid;
	private String name;
	private Long fileSize;
	private String fileType;//保存类型
	private String type;//文件类别
	private String storageType;
	private String storagePath;
	private String md5;
	private String bookid;//所属图书//@Transient
	private String message_id;//所属图书//@Transient
	private Boolean newAttachement;//是否是新附件//@Transient
	private String orgCode;
	private Date time;//文件上传时间

	@Id
	@Column(name = "uuid", length = 50)
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "fileSize")
	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "fileType", length = 50)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "storageType", length = 10)
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	@Column(name = "storagePath")
	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	@Column(name = "md5",length = 255)
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Column(name = "type",length = 50)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@Transient
	public Boolean getNewAttachement() {
		return newAttachement;
	}

	public void setNewAttachement(Boolean newAttachement) {
		this.newAttachement = newAttachement;
	}
	@Transient
	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}
	@Transient
	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	
	@Column(name = "org_code", length = 50)
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
