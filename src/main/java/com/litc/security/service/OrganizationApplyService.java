package com.litc.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import com.litc.security.model.OrganizationApply;

public interface OrganizationApplyService {
	
	/**
	 * 添加机构
	 * @param organizationApply
	 */
	public void addOrganizationApply(OrganizationApply organizationApply);

	/**
	 * 获取机构
	 * @param id
	 * @return
	 */
	public OrganizationApply getOrganizationApply(Long id);

	/**
	 *  修改机构
	 * @param user
	 * @return
	 */
	public OrganizationApply updateOrganizationApply(OrganizationApply organizationApply);
	

	/**
	 * 删除机构,根据编号删除
	 * @param id
	 */
	public void deleteOrganizationApply(Long id);

	/**
	 * 批量删除机构
	 * @param ids
	 * @return
	 */
	public int deleteOrganizationApplys(Long[] ids);

	/**
	 * 根据类别查询所有机构
	 * @param types
	 * @return
	 */
	public List<OrganizationApply> getOrganizationApplysByOrgType(Long[] types);
	
	/**
	 * 根据类别查询机构
	 * @param typeId
	 * @return
	 */
	public List<OrganizationApply> findByOrgType(Long typeId);
	
	/**
	 * 查询所有机构
	 * @return
	 */
	public List<OrganizationApply> getOrganizationApplys();
	
	/**
	 * 分页带条件查询机构
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<OrganizationApply> getOrganizationApplysByPages(int pageNo,int pageSize,Direction driection,String orderType,Long orgType,Integer orgStatus,String keyWord,String secondOrgName);
	
	/**
	 * 是否存在机构代码 是否存在用户名 返回true表示已存在
	 * @param loginName
	 * @return
	 */
	public boolean isOrgCodeExist(String orgCode);
	
	/**
	 * 是否存在用户名 是否存在用户名 返回true表示已存在
	 * @param loginName
	 * @return
	 */
	public boolean isLoginNameExist(String loginName);
	
	/**
	 * 是否存在邮箱 返回true表示已存在
	 * @param email
	 * @return
	 */
	public boolean isEmailExist(String email);

}
