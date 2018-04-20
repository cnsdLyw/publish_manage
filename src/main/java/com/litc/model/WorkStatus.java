package com.litc.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "work_status")
public class WorkStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2655261617380260183L;

	@Id
	private String workId;

	/**
	 * 序列化对象
	 */
	private byte[] obj;

	/**
	 * 优先级
	 */
	private int priority;

	/**
	 * 任务状态
	 */
	private String curStatus;

	/**
	 * 任务类型
	 */
	private String type;

	/**
	 * 任务描述
	 */
	private String description;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getObj() {
		return obj;
	}

	public void setObj(byte[] obj) {
		this.obj = obj;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
