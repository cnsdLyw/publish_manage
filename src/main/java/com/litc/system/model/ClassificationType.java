package com.litc.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.model.BaseId;

/**
 * 分类类别表
 */
@Entity
@Table(name = "sys_classification_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClassificationType extends BaseId {
	private String name;
	private String code;
	private String remark;

	@Column(length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
