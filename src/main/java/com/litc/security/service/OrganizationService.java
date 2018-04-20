package com.litc.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.litc.security.model.Organization;

public interface OrganizationService {
	
	/**
	 * 添加机构
	 * @param organization
	 */
	public void addOrganization(Organization organization);

	/**
	 * 获取机构
	 * @param id
	 * @return
	 */
	public Organization getOrganization(String orgCode);
	
	/**
	 * 获取机构
	 * @param org
	 * @return
	 */
	public Organization getOrganization(Organization org);
	
	/**
	 * 获取机构
	 * @param orgName
	 * @return
	 */
	public Organization getOrgan(String orgName);

	/**
	 *  修改机构
	 * @param user
	 * @return
	 */
	public Organization updateOrganization(Organization organization);
	
	/**
	 * 机构是否使用
	 * @param id
	 * @return
	 */
	public int isOrganizationUsed(String orgCode);
	
	/**
	 * 机构是否使用
	 * @param id
	 * @return
	 */
	public int isOrganizationUsed(String[] orgCodes);
	

	/**
	 * 删除机构,根据编号删除
	 * @param id
	 */
	public void deleteOrganization(String orgCode);

	/**
	 * 批量删除机构
	 * @param ids
	 * @return
	 */
	public int deleteOrganizations(String[] orgCodes);

	/**
	 * 根据类别查询所有机构
	 * @param types
	 * @return
	 */
	public List<Organization> getOrganizationsByOrgType(String[] types);
	
	/**
	 * 根据类别查询机构
	 * @param typeId
	 * @return
	 */
	public List<Organization> findByOrgType(String typeId);
	
	/**
	 * 查询所有机构
	 * @return
	 */
	public List<Organization> getOrganizations();
	
	/**
	 * 分页带条件查询机构
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<Organization> getOrganizationsByPages(int pageNo,int pageSize,Direction driection,String orderType,Long orgType,String secondOrgName,String keyWord);
	
	
	/**
	 * 根据机构代码集合获取机构代码名称集合
	 *  @param orgCode
	 *  @return
	 */
	List<String> findOrganizationNames(String[] orgCodes);
	
	/**
	 * 获取机构
	 * @param orgCode
	 * @return
	 */
	public Organization getOrganizationByCode(String orgCode);
	
	/**
	 * 是否存在机构代码 是否存在用户名 返回true表示已存在
	 * @param loginName
	 * @return
	 */
	public boolean isOrgCodeExist(String orgCode);
	/**
	 * 查询所有的父节点
	 * @return
	 */
	public List<Organization> getFirstOrganization();
    /**
     * 根据条件查询父节点
     * @param firstName
     * @return
     */
	public List<Organization> getFirstOrganization(String firstName);
	
	/**
	 * 根据父节点查询子节点
	 * @param firstName
	 * @return
	 */
	public List<Organization> getOrganizationByFirstName(String firstName);

	public List<Organization> getOrganizationBySecondName(String secondName);

	public String getOrgName(String upperCode);
	
	/**
	 * 更新机构服务状态
	 * @param orgID
	 * @return
	 */
	public Organization updateOrgStatus(String orgID,String status);
}
