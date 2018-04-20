package com.litc.model.cms;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.format.annotation.DateTimeFormat;

import com.litc.model.BaseId;

/**
 * CMS稿件表
 */
@Entity
@Table(name = "cms_manuscript")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Manuscript extends BaseId {

	private String title;// 标题
	private String subject;// 副标题
	private Integer newsType;// 稿件类型
	private String author;// 作者
	private String editor;// 责任编辑
	private String content;// 稿件内容
	private Date pubTime;// 发布时间
	private Double displayorder;// 排序码
	private Integer publishstate;// 发布状态
	private Long nodeID;// 栏目ID
	private String url;// 链接
	private Integer hitcount;// 点击量
	private String piclinks;// 缩略图
	private String posterpic;// 海报图
	private String audit_subscriber;// 审核人
	private Date audit_time;// 审核时间
	private Integer audit_status;// 审核状态
    private Date operate_time;// 操作时间
    private String newType;//新闻类型

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}
    
	public String getNewType() {
		return newType;
	}

	public void setNewType(String newType) {
		this.newType = newType;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}

	public Integer getNewsType() {
		return this.newsType;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getEditor() {
		return this.editor;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPubTime() {
		return this.pubTime;
	}

	public void setDisplayorder(Double displayorder) {
		this.displayorder = displayorder;
	}

	public Double getDisplayorder() {
		return this.displayorder;
	}

	public void setPublishstate(Integer publishstate) {
		this.publishstate = publishstate;
	}

	public Integer getPublishstate() {
		return this.publishstate;
	}

	public void setNodeID(Long nodeID) {
		this.nodeID = nodeID;
	}

	public Long getNodeID() {
		return this.nodeID;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setHitcount(Integer hitcount) {
		this.hitcount = hitcount;
	}

	public Integer getHitcount() {
		return this.hitcount;
	}

	public void setPiclinks(String piclinks) {
		this.piclinks = piclinks;
	}

	public String getPiclinks() {
		return this.piclinks;
	}

	public void setPosterpic(String posterpic) {
		this.posterpic = posterpic;
	}

	public String getPosterpic() {
		return this.posterpic;
	}

	public void setAudit_subscriber(String audit_subscriber) {
		this.audit_subscriber = audit_subscriber;
	}

	public String getAudit_subscriber() {
		return this.audit_subscriber;
	}

	public void setAudit_time(Date audit_time) {
		this.audit_time = audit_time;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAudit_time() {
		return this.audit_time;
	}

	public void setAudit_status(Integer audit_status) {
		this.audit_status = audit_status;
	}

	public Integer getAudit_status() {
		return this.audit_status;
	}
	
}
