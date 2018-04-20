package com.litc.system.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.litc.model.BaseId;

/**
 * 工作流节点
 * 
 * @author liyw
 * 
 */

@Entity
@Table(name = "sys_workflow_node")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysWorkFlowNode extends BaseId {
	private String name;
	private String authorityKey;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "authority_key")
	public String getAuthorityKey() {
		return authorityKey;
	}

	public void setAuthorityKey(String authorityKey) {
		this.authorityKey = authorityKey;
	}

}
