package com.litc.model.cms;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.litc.model.BaseId;

/**
 * 留言板
 */
@Entity
@Table(name = "cms_faq")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Faq extends BaseId {

	private String title;// 标题
	private String content;// 留言内容
	private Date pubdate;// 发表时间
	private String loginname;// 会员发表人，会员登录名
	private String anonymous;// 匿名发表人姓名
	private String email;// 匿名邮箱
	private String phone;// 匿名电话
	private String ip;// IP地址
	private String auditStatus;// 留言板状态
	private String answerContent;//回复内容
	private Date answerTime;//回复时间
	private String answerUser;//回复用户
    @Column(name="audit_status")
	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
    @Column(name="answer_content")
	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
    @Column(name="answer_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Date answerTime) {
		this.answerTime = answerTime;
	}
    @Column(name="answer_user")
	public String getAnswerUser() {
		return answerUser;
	}

	public void setAnswerUser(String answerUser) {
		this.answerUser = answerUser;
	}
	

}
