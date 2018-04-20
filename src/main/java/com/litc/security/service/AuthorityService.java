package com.litc.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import com.litc.security.common.page.PageParam;
import com.litc.security.model.Authority;
import com.litc.security.model.User;

public interface AuthorityService {
	
	/**
	 * 添加权限
	 * @param authority
	 */
	public void addAuthority(Authority authority);

	/**
	 * 获取权限
	 * @param id
	 * @return
	 */
	public Authority getAuthority(Long id);

	/**
	 *  修改权限
	 * @param user
	 * @return
	 */
	public Authority updateAuthority(Authority authority);
	
	/**
	 * 权限是否使用
	 * @param id
	 * @return
	 */
	public int isAuthorityUsed(Long id);
	
	/**
	 * 权限是否使用
	 * @param id
	 * @return
	 */
	public int isAuthorityUsed(Long[] id);
	

	/**
	 * 删除权限,根据编号删除
	 * @param id
	 */
	public void deleteAuthority(Long id);

	/**
	 * 批量删除权限
	 * @param ids
	 * @return
	 */
	public int deleteAuthoritys(Long[] ids);
	/**
	 * 
	 * @param loginName
	 * @return
	 */
	public Authority getAuthority(String idStr);

	/**
	 * 查询所有权限
	 * @return
	 */
	public List<Authority> getAuthoritys();
	
	/**
	 * 按状态查询所有权限
	 * @return
	 */
	public List<Authority> getAuthoritys(int status);
	
	/**
	 * 按类型所有权限
	 * @return
	 */
	public List<Authority> getAuthoritysByType(int type);
	
	/**
	 * 分页带条件查询权限
	 * @param pageNo
	 * @param pageSize
	 * @param driection
	 * @param orderType
	 * @param args
	 * @return
	 */
	public Page<Authority> getAuthoritiesByPages(int pageNo,int pageSize,Direction driection,String orderType,String keyWord);
	
	
	/**
	 * 是否存在权限值 返回true表示已存在
	 * @param authorityKey
	 * @return
	 */
	public boolean isAuthorityKeyExist(String authorityKey);
	
	/**
	 * 是否存在权限值 返回true表示已存在
	 * @param id
	 * @param authorityKey
	 * @return
	 */
	public boolean isAuthorityKeyExist(Long id,String authorityKey);
	/**
	 * 是否存在权限名 返回true表示已存在
	 * @param authorityKey
	 * @return
	 */
	public boolean isAuthorityNameExist(String authorityName);
	/**
	 * 是否存在权限名 返回true表示已存在
	 * @param id
	 * @param authorityKey
	 * @return
	 */
	public boolean isAuthorityNameExist(Long id, String authorityName);
}
