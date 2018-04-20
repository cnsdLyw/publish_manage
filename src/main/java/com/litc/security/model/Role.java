package com.litc.security.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.litc.security.repository.IComponent;

/**
 * 系统角色表，每个平台机构有自己的角色，角色信息不共用，权限是共用的。
 * @author liyw
 *
 */
@Entity
@Table(name = IComponent.SYS_ROLE)
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends BaseId {

	private String roleName;
	
	private String remark;
	
	private List<Authority> authorityList = new ArrayList<Authority>();
	
	/**
	 * 区分角色所属平台类别，1 出版；2图书馆 ；3发行 ；4 加工；所以角色在中心都可以看到
	 */
	private String organizationFlag;
	
	private Date lastModifyTime;
	
	private int status;
	
	@Column(nullable = false, unique = true)
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@ManyToMany
	@JoinTable(name = IComponent.SYS_ROLE_AUTHORITY, joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "authorityId") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	/*@Transient
	public String getAuthNames() {
		return ReflectionUtils.convertElementPropertyToString(authorityList, "displayName", ", ");
	}

	@Transient
	@SuppressWarnings("unchecked")
	public List<Long> getAuthIds() {
		return ReflectionUtils.convertElementPropertyToList(authorityList, "id");
	}*/

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "organization_flag", length = 2,columnDefinition = " default '0'")
	public String getOrganizationFlag() {
		return organizationFlag;
	}

	public void setOrganizationFlag(String organizationFlag) {
		this.organizationFlag = organizationFlag;
	}

	@Column(name = "last_modify_time")
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
