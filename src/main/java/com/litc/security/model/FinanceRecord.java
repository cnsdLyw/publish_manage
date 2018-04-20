package com.litc.security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * 财务流水Model
 * 
 */
@Entity
@Table(name = "sly_finance_record")
@SuppressWarnings("unused")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FinanceRecord extends BaseId {
	private Double amount;// 金额
	private String amountType;// 类别：支出/收入
	private String amountUser;// 经手人
	private Date amountTime;// 时间
	private Integer status;// 状态
	private String remark;// 明细说明
	private String auditor;// 审核人员
	private String auditOpinion;// 审核意见
	private String auditTime;// 审核时间
	private String operator;// 操作人
	private String attachment;//相关附件
	private String attachmentName;// 相关附件名称
	private Date lastModifyTime;// 修改时间

	@Column(name = "amount")
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "amount_type", length=4)
	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	@Column(name = "amount_user", length=16)
	public String getAmountUser() {
		return amountUser;
	}

	public void setAmountUser(String amountUser) {
		this.amountUser = amountUser;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "amount_time")
	public Date getAmountTime() {
		return amountTime;
	}

	public void setAmountTime(Date amountTime) {
		this.amountTime = amountTime;
	}

	@Column(name = "amount_status", length=2)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "remark", length=300)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "amount_auditor", length=16)
	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	
	@Column(name = "amount_auditor_opinion", length=200)
	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	@Column(name = "amount_auditor_time")
	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "amount_operator", length=16)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "attachment_path", length=255)
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	@Column(name = "attachment_name", length=255)	
	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	@Column(name = "last_modify_time")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
