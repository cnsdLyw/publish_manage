package com.litc.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 发布平台常见问题
 * 
 * @author cuiyc
 * 
 */
@Entity
@Table(name = "jc_common_question")
public class CommonQuestion extends BaseId {
	
	/**
	 * 问题
	 */
	private String question;
	/**
	 * 解答
	 */
	private String content;
	/**
	 * 用户
	 */
	private String user;
	/**
	 * 创建时间
	 */
	private Date time;
	@Column(name = "question", length = 255)
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "create_user", length = 20)
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	
	
}
