package com.litc.system.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.litc.model.BaseId;

/**
 * 工作流
 * @author liyw
 *
 */

@Entity
@Table(name = "sys_workflow")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysWorkFlow extends BaseId {
	private String name;
	private Set<SysWorkFlowTransfer> processWorKFlowTransfers ;
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="porcessWorKFlow")
	@OrderBy("id")
	@JsonBackReference
	public Set<SysWorkFlowTransfer> getProcessWorKFlowTransfers() {
		return processWorKFlowTransfers;
	}

	public void setProcessWorKFlowTransfers(
			Set<SysWorkFlowTransfer> processWorKFlowTransfers) {
		this.processWorKFlowTransfers = processWorKFlowTransfers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
