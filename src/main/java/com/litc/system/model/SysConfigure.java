package com.litc.system.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.security.model.BaseId;
import com.litc.security.repository.IComponent;

@Entity
@Table(name = IComponent.SYS_CONFIGURE)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysConfigure extends BaseId {
	// private SysConfigGroup configGroup; // 配置分类
	private Long groupId;// 配置分类ID
	private String configureName; // 配置名称
	private String configureCode; // 配置编码
	private String configureValue; // 配置值
	private String remark; // 备注

	/*
	 * @ManyToOne(fetch=FetchType.LAZY)
	 * @JoinColumn(name="typeid")
	 * @Cache(usage = CacheConcurrencyStrategy.READ_WRITE) 
	 * public SysConfigGroup
	 * getConfigGroup() { return configGroup; }
	 * public void setConfigGroup(SysConfigGroup configGroup) { this.configGroup
	 * = configGroup; }
	 */
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getConfigureName() {
		return configureName;
	}

	public void setConfigureName(String configureName) {
		this.configureName = configureName;
	}

	public String getConfigureCode() {
		return configureCode;
	}

	public void setConfigureCode(String configureCode) {
		this.configureCode = configureCode;
	}

	public String getConfigureValue() {
		return configureValue;
	}

	public void setConfigureValue(String configureValue) {
		this.configureValue = configureValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
