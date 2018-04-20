package com.litc.system.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.security.model.BaseId;
import com.litc.security.repository.IComponent;

@Entity
@Table(name = IComponent.SYS_CONFIGURE_GROUP)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysConfigGroup extends BaseId {
	
	private Long parentId; // 上级配置组ID
	private String groupName; // '配置组名称
	private String remark; // 备注

	// private Set<SysConfigure> set ;
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
