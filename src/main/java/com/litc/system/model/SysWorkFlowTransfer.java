package com.litc.system.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.litc.model.BaseId;

/**
 * 工作流流程表
 * 
 * @author liyw
 * 
 */

@Entity
@Table(name = "sys_workflow_transfer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SysWorkFlowTransfer extends BaseId {
	private SysWorkFlow porcessWorKFlow;
	private int step;
	private Long nodeId;
	private String nodeName;
	private String authorityKey;
	private Long lastNodeId;
	private Long nextNodeId;
	
	@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH }, optional = true)   
	@JoinColumn(name="workFlowId")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonManagedReference
	public SysWorkFlow getPorcessWorKFlow() {
		return porcessWorKFlow;
	}

	public void setPorcessWorKFlow(SysWorkFlow porcessWorKFlow) {
		this.porcessWorKFlow = porcessWorKFlow;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public Long getLastNodeId() {
		return lastNodeId;
	}

	public void setLastNodeId(Long lastNodeId) {
		this.lastNodeId = lastNodeId;
	}

	public Long getNextNodeId() {
		return nextNodeId;
	}

	public void setNextNodeId(Long nextNodeId) {
		this.nextNodeId = nextNodeId;
	}

	@Column(name = "authority_key")
	public String getAuthorityKey() {
		return authorityKey;
	}

	public void setAuthorityKey(String authorityKey) {
		this.authorityKey = authorityKey;
	}

}
