package com.litc.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.model.BaseId;

/**
 * 分类表
 */
@Entity
@Table(name = "sys_classification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Classification{
	private Short classLevel;
	private String classCode;
	private String className;
	private String parentCode;
	private String classKey;
	private boolean hasLeaf;
	private String remark;

	@Column(name="class_level",length=6)
	public Short getClassLevel() {
		return classLevel;
	}

	public void setClassLevel(Short classLevel) {
		this.classLevel = classLevel;
	}

	@Id
	@Column(name="class_code",length=100)
	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	@Column(name="class_name",length=100)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name="parent_code",length=100)
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	@Column(name="class_key",length=36)
	public String getClassKey() {
		return classKey;
	}

	public void setClassKey(String classKey) {
		this.classKey = classKey;
	}

	@Column(name="hasLeaf")
	public boolean isHasLeaf() {
		return hasLeaf;
	}

	public void setHasLeaf(boolean hasLeaf) {
		this.hasLeaf = hasLeaf;
	}

	@Column(length=200)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
